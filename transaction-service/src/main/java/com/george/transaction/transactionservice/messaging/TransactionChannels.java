package com.george.transaction.transactionservice.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TransactionChannels {

	@Output
	MessageChannel output();

}
