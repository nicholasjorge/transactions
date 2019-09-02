package com.george.transaction.persistenceservice.controller;

import com.george.transaction.persistenceservice.model.Report;
import com.george.transaction.persistenceservice.service.PersistenceService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/persistence")
public class PersistenceServiceController {

    private PersistenceService persistenceService;

    @Autowired
    public PersistenceServiceController(final PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @ApiOperation(value = "Generate Report for Transactions", notes = "Groups transaction information grouped by IBAN and transaction type, counting the transactions and grouping the clients(by CNP) and summing the amount", response = ResponseEntity.class)
    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReport() {
        List<Report> report = persistenceService.getReport();
        return new ResponseEntity(report, HttpStatus.OK);
    }

    public ResponseEntity fallback() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
