package cex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Map<Session, Subscription> subscriptions = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void subscribe(Session session) {
        executor.submit(() -> {
            subscriptions.put(session, new Subscription(session));
        });
    }

    @Override
    public void unsubscribe(Session session) {
        executor.submit(() -> {
            subscriptions.remove(session);
        });
    }

    @Override
    public void broadcast(ResourceMessage message) {

        executor.submit(() -> {
            for (Subscription subscription : subscriptions.values()) {
                subscription.send(message);
            }
        });
    }

    @Override
    public void handle(Session session, SubscriptionMessage message) {
        executor.submit(() -> {
            Subscription subscription = subscriptions.get(session);
            if (subscription != null) {
                subscription.handle(message);
            }
        });
    }

    @RequiredArgsConstructor
    private class Subscription {

        private final Session session;
        private final Map<String, Pattern> patterns = new HashMap<>();

        @Synchronized
        public void handle(SubscriptionMessage message) {
            switch (message.type) {
                case SUBSCRIBE:
                    patterns.put(message.pattern, Pattern.compile(message.pattern));
                    break;
                case UNSUBSCRIBE:
                    patterns.remove(message.pattern);
                    break;
            }
        }

        @Synchronized
        public void send(ResourceMessage message) {
            for (Pattern p : patterns.values()) {
                if (p.matcher(message.alias).matches()) {
                    try {
                        session.getBasicRemote().sendText(mapper.writeValueAsString(message.object));
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }

                    break;
                }
            }
        }
    }
}
