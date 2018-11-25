package cex.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel("Запрос на перевод")
public class TransferRequest {

    @NotNull
    @ApiModelProperty("счет пользователя")
    private String accountFrom;

    @NotNull
    @ApiModelProperty("счет назначения")
    private String accountTo;

    @NotNull
    @ApiModelProperty("Сумма")
    private BigDecimal amount;
}
