package cex.service;

import cex.amqp.messages.TransferMessage;
import cex.amqp.service.MessageProducer;
import cex.api.request.TransferRequest;
import cex.api.response.TransferResponse;
import cex.entity.Transfer;
import cex.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final MessageProducer messageProducer;
    private final SubscriptionService subscriptionService;

    @Autowired
    public TransferService(TransferRepository transferRepository, MessageProducer messageProducer, SubscriptionService subscriptionService) {
        this.transferRepository = transferRepository;
        this.messageProducer = messageProducer;
        this.subscriptionService = subscriptionService;
    }

    @Transactional
    public TransferResponse performTransfer(TransferRequest request) {
        Transfer transfer = new Transfer();

        transfer.setAccountFrom(request.getAccountFrom());
        transfer.setAccountTo(request.getAccountTo());
        transfer.setAmount(request.getAmount());
        transfer = transferRepository.save(transfer);

        messageProducer.sendMessage(new TransferMessage(transfer.getId(), "Transfer was store"), "transfer.store");
        subscriptionService.broadcast(new SubscriptionService.ResourceMessage(
                "transfer.create",
                SubscriptionService.ResourceMessage.Type.CREATE,
                "Transfer was created"));
        return new TransferResponse(transfer.getId());
    }
}
