package com.george.transaction.transactionservice.controller;

import com.george.transaction.transactionservice.dto.TransactionDto;
import com.george.transaction.transactionservice.messaging.TransactionWriter;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @ApiOperation(value = "Creates a transaction", notes = "Publishes a transaction in the RabbitMQ broker to be persisted", consumes = MediaType.APPLICATION_JSON_VALUE, response = Void.class)
    @HystrixCommand(fallbackMethod = "fallback")
    @PostMapping
    public void write(@ApiParam(value = "Transaction object to be persisted", required = true) @RequestBody @Valid final TransactionDto transaction) {
        this.writer.write(transaction);
    }

    public void fallback(TransactionDto transactionDto) {
    }

}
