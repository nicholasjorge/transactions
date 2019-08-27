package com.george.transaction.transactionservice.feign;

import com.george.transaction.transactionservice.dto.TransactionDto;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

public interface PersistenceServiceProxy {

    @GetMapping("/persistence-service/transactions")
    public Resources<TransactionDto> getTransactions();

}