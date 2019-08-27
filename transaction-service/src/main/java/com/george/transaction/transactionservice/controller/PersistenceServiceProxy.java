package com.george.transaction.transactionservice.controller;

import com.george.transaction.transactionservice.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "persistence-service"/*, fallback = HystrixClientFallback.class*/)
//@FeignClient(name = "api-gateway")
//@RibbonClient(name = "persistence-service")
public interface PersistenceServiceProxy {

    @GetMapping("/transactions")
    public Resources<TransactionDto> getTransactions();

}

//static class HystrixClientFallback implements HystrixClient {
//    @Override
//    public Resources<TransactionDto> iFailSometimes() {
//        return new Resources<TransactionDto>();
//    }