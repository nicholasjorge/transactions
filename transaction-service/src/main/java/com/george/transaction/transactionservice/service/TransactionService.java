package com.george.transaction.transactionservice.service;

import java.util.Collection;

import com.george.transaction.transactionservice.dto.TransactionDto;

public interface TransactionService {

	Collection<TransactionDto> getTransactions();

	Collection<TransactionDto> getReport(final String name);

	TransactionDto createTransaction(final TransactionDto transaction);

}
