package com.george.transaction.transactionservice.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.george.transaction.transactionservice.dto.TransactionDto;

@MessagingGateway
interface TransactionWriter {

	@Gateway(requestChannel = "output")
	void write(TransactionDto transaction);

}