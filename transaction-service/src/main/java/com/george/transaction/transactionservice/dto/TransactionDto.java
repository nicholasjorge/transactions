package com.george.transaction.transactionservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TransactionDto implements Serializable{
	
	private static final long serialVersionUID = -916709137486141281L;
	private String transactionType;
	private String name;
	private String cnp;
	private String iban;
	private String description;
	private BigDecimal amount;

}
