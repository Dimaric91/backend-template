package cex.amqp.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMessage implements RabbitMessages {

    private Long transferId;
    private String message;
}
