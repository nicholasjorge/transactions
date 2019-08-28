package com.george.transaction.transactionservice.service;

import com.george.transaction.transactionservice.dto.TransactionDto;

import java.util.Collection;
import java.util.Map;

public interface TransactionService {

    Map getReport(final Collection<TransactionDto> transactions);

}
