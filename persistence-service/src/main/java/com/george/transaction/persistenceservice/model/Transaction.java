package com.george.transaction.persistenceservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Transaction implements Serializable {

	private static final long serialVersionUID = 6861305102131387511L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	private String iban;
	private String cnp;
	private String name;
	private String description;
	private BigDecimal amount;

}
