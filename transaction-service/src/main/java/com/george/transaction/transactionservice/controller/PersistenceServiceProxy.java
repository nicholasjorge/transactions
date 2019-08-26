package com.george.transaction.transactionservice.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

import com.george.transaction.transactionservice.dto.TransactionDto;

@FeignClient(name = "persistence-service")
//@FeignClient(name = "api-gateway")
//@RibbonClient(name = "persistence-service")
public interface PersistenceServiceProxy {

	@GetMapping("/transactions")
	public Resources<TransactionDto> getTransactions();

}