package com.george.transaction.transactionservice.dto;

public enum TransactionTypeDto {

    IBAN_TO_IBAN,
    IBAN_TO_WALLET,
    WALLET_TO_IBAN,
    WALLET_TO_WALLET;

    private TransactionTypeDto() {
    }

}