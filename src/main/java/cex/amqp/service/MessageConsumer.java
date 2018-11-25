package cex.amqp.service;

import cex.amqp.messages.TransferMessage;
import cex.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receive(TransferMessage message) {
        log.info("Receive message '{}'", message);
    }
}
