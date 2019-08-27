package com.george.transaction.transactionservice.controller;

import com.george.transaction.transactionservice.dto.TransactionDto;
import com.george.transaction.transactionservice.feign.PersistenceServiceFeignProxy;
import com.george.transaction.transactionservice.messaging.TransactionWriter;
import com.george.transaction.transactionservice.service.TransactionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
public class TransactionServiceController {

    public static final String PERSISTENCE_SERVICE_TRANSACTIONS_URL = "http://persistence-service/transactions/";

    private PersistenceServiceFeignProxy persistenceServiceFeignProxy;
    private TransactionService transactionService;
    private TransactionWriter writer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public TransactionServiceController(final PersistenceServiceFeignProxy persistenceServiceFeignProxy, final TransactionService transactionService,
                                        final TransactionWriter writer) {
        this.persistenceServiceFeignProxy = persistenceServiceFeignProxy;
        this.transactionService = transactionService;
        this.writer = writer;
    }

    @GetMapping
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<Resources<TransactionDto>> getTransactions() {
        log.info("Calling persistence-service using restTemplate");
        return restTemplate.exchange(PERSISTENCE_SERVICE_TRANSACTIONS_URL, HttpMethod.GET, null, new ParameterizedTypeReference<Resources<TransactionDto>>() {
        });
    }

    private ResponseEntity<Resources<TransactionDto>> fallback() {
        log.warn("Entered the fallbackMethod in the transaction-service");
        return new ResponseEntity<Resources<TransactionDto>>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public void write(@RequestBody @Valid TransactionDto transaction) {
        this.writer.write(transaction);
    }

    @GetMapping("/report")
    @HystrixCommand(fallbackMethod = "fallbackReport")
    public ResponseEntity<Map> getReport() {
        log.info("Entered the report generation endpoint in transaction-service");
        ResponseEntity<Resources<TransactionDto>> transactions = restTemplate
                .exchange(PERSISTENCE_SERVICE_TRANSACTIONS_URL, HttpMethod.GET, null, new ParameterizedTypeReference<Resources<TransactionDto>>() {
                });

        Map report = transactionService.getReport(transactions.getBody().getContent());
        ResponseEntity<Map> responseEntity = new ResponseEntity<Map>(report, HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<Map> fallbackReport() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    @PostMapping
    //    @HystrixCommand(fallbackMethod = "fallbackCreate")
    //    public ResponseEntity<Resources<TransactionDto>> createTransaction(@RequestBody TransactionDto transaction) {
    //        log.info("calling persistence service, create transaction, with data: {}", transaction);
    //        return restTemplate
    //                .exchange("http://persistence-service/transactions/", HttpMethod.POST, new HttpEntity<>(transaction), new ParameterizedTypeReference<Resources<TransactionDto>>() {
    //                });
    //    }
    //
    //    private ResponseEntity<Resources<TransactionDto>> fallbackCreate(TransactionDto transaction) {
    //        log.warn("Entered the fallbackMethod in the transaction-service");
    //        return new ResponseEntity<Resources<TransactionDto>>(HttpStatus.NO_CONTENT);
    //    }

    @GetMapping("/feign")
    public ResponseEntity getTransactionsFeign() {
        log.info("Calling the persistence-service using feign client");
        return new ResponseEntity(persistenceServiceFeignProxy.getTransactions(), HttpStatus.OK);
    }

}
