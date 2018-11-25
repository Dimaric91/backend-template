package cex.amqp.service;

import cex.amqp.messages.RabbitMessages;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessage(RabbitMessages message, String routingKey) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    }
}
