package com.george.transaction.persistenceservice.messaging;

import com.george.transaction.persistenceservice.model.Transaction;
import com.george.transaction.persistenceservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@Slf4j
@MessageEndpoint
public class TransactionProcessor {

    private TransactionRepository repo;

    @Autowired
    public TransactionProcessor(final TransactionRepository repo) {
        this.repo = repo;
    }

    @ServiceActivator(inputChannel = "input")
    public void onMessage(final Message<Transaction> msg) {
        log.info("Processing message with transaction payload : {}", msg.getPayload());
        this.repo.save(msg.getPayload());
    }

}
