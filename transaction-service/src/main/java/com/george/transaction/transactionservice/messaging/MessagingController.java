package com.george.transaction.transactionservice.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.george.transaction.transactionservice.controller.PersistenceServiceProxy;
import com.george.transaction.transactionservice.dto.TransactionDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/transactions")
public class MessagingController {

	private final TransactionWriter writer;
	private final PersistenceServiceProxy reader;

	@Autowired
	public MessagingController(TransactionWriter writer, PersistenceServiceProxy reader) {
		this.writer = writer;
		this.reader = reader;
	}

	public Collection<String> fallback() {
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/names")
	public Collection<String> names() {
		return this.reader.getTransactions().getContent().stream().map(TransactionDto::getName)
				.collect(Collectors.toList());
	}

	@PostMapping
	public void write(@RequestBody TransactionDto transaction) {
		this.writer.write(transaction);
	}

}
