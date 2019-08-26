package com.george.transaction.persistenceservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface TransactionChannels {

	@Input
    MessageChannel input();
	
}
