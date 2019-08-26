package com.george.transaction.transactionservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.george.transaction.transactionservice.controller.PersistenceServiceProxy;
import com.george.transaction.transactionservice.dto.TransactionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private RestTemplate restTemplate;

//	private PersistenceServiceProxy persistenceServiceProxy;

//	@Autowired
//	public TransactionServiceImpl(PersistenceServiceProxy persistenceServiceProxy) {
//		this.persistenceServiceProxy = persistenceServiceProxy;
//	}

	public Collection<TransactionDto> getTransactions() {
//		log.info("Calling the persistence-service through feign in the transaction-service");
//		Collection<TransactionDto> content = persistenceServiceProxy.getTransactions().getContent();
		return null;
	}

	@Override
	public Collection<TransactionDto> getReport(final String name) {
		return null;
	}

	@Override
	public TransactionDto createTransaction(final TransactionDto transaction) {
		return null;
	}

}
