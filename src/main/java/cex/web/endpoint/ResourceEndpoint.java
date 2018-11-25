package cex.web.endpoint;

import cex.service.SubscriptionService;
import cex.service.SubscriptionService.SubscriptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@Component
@ServerEndpoint(value = "/socket/resource", configurator = EndpointConfigurator.class)
public class ResourceEndpoint {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ObjectMapper mapper;

    @OnOpen
    public void onConnect(Session session) {
        log.info("receive new connection");
        subscriptionService.subscribe(session);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("close connection");
        subscriptionService.unsubscribe(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            log.info("Receive message {}", message);
            subscriptionService.handle(session, mapper.readValue(message, SubscriptionMessage.class));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage(), error);
    }
}
