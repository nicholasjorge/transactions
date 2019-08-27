package com.george.transaction.transactionservice.messaging;

import com.george.transaction.transactionservice.dto.TransactionDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface TransactionWriter {

    @Gateway(requestChannel = "output")
    void write(TransactionDto transaction);

}