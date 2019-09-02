package com.george.transaction.persistenceservice.service;

import com.george.transaction.persistenceservice.model.Report;
import com.george.transaction.persistenceservice.model.Transaction;
import com.george.transaction.persistenceservice.model.TransactionType;
import com.george.transaction.persistenceservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersistenceServiceImpl implements PersistenceService {

    private TransactionRepository transactionRepository;

    @Autowired
    public PersistenceServiceImpl(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Report> getReport() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<Report> reports = processTransactions(transactions);
        return reports;
    }

    private List<Report> processTransactions(final List<Transaction> transactions) {
        log.info("Processing {} transactions.", transactions.size());
        Map<String,List<Transaction>> groupedByIBAN = transactions.stream().collect(Collectors.groupingBy(Transaction::getIban));
        List<Report> reports = new LinkedList<>();
        for (Map.Entry<String,List<Transaction>> entry : groupedByIBAN.entrySet()) {
            Report report = new Report();
            report.setIban(entry.getKey());
            List<Transaction> transactionsByIBAN = entry.getValue();
            report.setTransactionsCount(transactionsByIBAN.size());
            Map<TransactionType,Map<String,BigDecimal>> details = transactionsByIBAN.stream().collect(Collectors.groupingBy(Transaction::getTransactionType, byCNPAndSum()));
            report.setDetails(details);
            reports.add(report);
        }
        log.info("Generated {} records.", reports.size());
        return reports;
    }

    private Collector<Transaction,?,Map<String,BigDecimal>> byCNPAndSum() {
        return Collectors.groupingBy(Transaction::getCnp, Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add));
    }

}
