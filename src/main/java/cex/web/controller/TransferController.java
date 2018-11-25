package cex.web.controller;

import cex.service.TransferService;
import cex.api.request.TransferRequest;
import cex.api.response.TransferResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("Простой контроллер")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer")
    @ApiOperation("Осуществить перевод")
    public TransferResponse create(@RequestBody TransferRequest request) {
        return transferService.performTransfer(request);
    }

}
