package com.george.transaction.persistenceservice.service;

import com.george.transaction.persistenceservice.model.Report;
import com.george.transaction.persistenceservice.model.Transaction;
import com.george.transaction.persistenceservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private TransactionRepository transactionRepository;

    @Autowired
    public PersistenceServiceImpl(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Report getReport() {
        List<Transaction> transactions = transactionRepository.findAll();
        Map collectMany = collectMany(transactions, Transaction::getCnp, Transaction::getTransactionType, Transaction::getIban, Transaction::getName);
        //TODO implement report
        return null;
    }

    private static <T> Map collectMany(Collection<T> data, Function<T,?>... groupers) {
        //		Collections.reverse(Arrays.asList(groupers));
        Iterator<Function<T,?>> iter = Arrays.asList(groupers).iterator();
        Collector collector = Collectors.groupingBy(iter.next());
        while (iter.hasNext()) {
            collector = Collectors.groupingBy(iter.next(), collector);
        }
        return (Map) data.stream().collect(collector);
    }
}
