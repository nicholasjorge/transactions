package com.george.transaction.persistenceservice.model;

public enum TransactionType {
	
	IBAN_TO_IBAN,
	IBAN_TO_WALLET,
	WALLET_TO_IBAN,
	WALLET_TO_WALLET;
	
	private TransactionType() {
	}

}
