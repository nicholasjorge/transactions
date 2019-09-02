package com.george.transaction.persistenceservice.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class Report implements Serializable {

    private String iban;
    private int transactionsCount;
    private Map<TransactionType,Map<String,BigDecimal>> details;

}