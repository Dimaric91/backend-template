package cex.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Резальтат перевода")
public class TransferResponse {

    @ApiModelProperty("Идентификатор перевода")
    private Long transferId;

}
