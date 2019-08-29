package com.george.transaction.transactionservice.controller;

import com.george.transaction.transactionservice.dto.TransactionDto;
import com.george.transaction.transactionservice.messaging.TransactionWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
public class TransactionServiceController {

    private TransactionWriter writer;

    @Autowired
    public TransactionServiceController(final TransactionWriter writer) {
        this.writer = writer;
    }

    @PostMapping
    public void write(@RequestBody @Valid TransactionDto transaction) {
        this.writer.write(transaction);
    }

}
