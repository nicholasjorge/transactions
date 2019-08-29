package com.george.transaction.persistenceservice.controller;

import com.george.transaction.persistenceservice.model.Report;
import com.george.transaction.persistenceservice.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persistence")
public class PersistenceServiceController {

    private PersistenceService persistenceService;

    @Autowired
    public PersistenceServiceController(final PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @GetMapping(value = "/report", produces = "application/json")
    public ResponseEntity getReport() {
        Report report = persistenceService.getReport();
        return new ResponseEntity(report, HttpStatus.OK);
    }

}
