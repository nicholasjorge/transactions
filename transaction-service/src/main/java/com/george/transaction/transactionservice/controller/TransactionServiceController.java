package com.george.transaction.transactionservice.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.george.transaction.transactionservice.dto.TransactionDto;
import com.george.transaction.transactionservice.service.TransactionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
public class TransactionServiceController {

	private PersistenceServiceProxy persistenceServiceProxy;
//	private TransactionService transactionService;

	@Autowired
	public TransactionServiceController(PersistenceServiceProxy persistenceServiceProxy) {
		this.persistenceServiceProxy = persistenceServiceProxy;
	}

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	@HystrixCommand(fallbackMethod = "fallback")
	public ResponseEntity<Resources<TransactionDto>> getTransactionsByName() {
		log.info("Calling persistence-service using restTemplate");
		
		return restTemplate.exchange(
				"http://persistence-service/transactions/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Resources<TransactionDto>>() {
				});

	}
	
	@PostMapping
	@HystrixCommand(fallbackMethod = "fallbackCreate")
	public ResponseEntity<Resources<TransactionDto>> createTransaction(@RequestBody TransactionDto transaction){
		log.info("calling persistence service, create transaction, with data: {}",transaction);
		return restTemplate.exchange("http://persistence-service/transactions/", HttpMethod.POST, new HttpEntity<>(transaction), new ParameterizedTypeReference<Resources<TransactionDto>>() {
		});
	}
	
//	@GetMapping("/transactions")
//	@HystrixCommand(fallbackMethod = "fallback")
//	public ResponseEntity<Resources<TransactionDto>> getTransactions() {
//		log.info("Calling the persistence-service usign Feign Client Proxy");
//		Resources<TransactionDto> transactions = persistenceServiceProxy.getTransactions();
//		return new ResponseEntity<Resources<TransactionDto>>(transactions, HttpStatus.OK);
//	}

	private ResponseEntity<Resources<TransactionDto>> fallback() {
		log.warn("Entered the fallbackMethod in the transaction-service");
		return new ResponseEntity<Resources<TransactionDto>>(HttpStatus.NO_CONTENT);
	}
	
	private ResponseEntity<Resources<TransactionDto>> fallbackCreate(TransactionDto transaction) {
		log.warn("Entered the fallbackMethod in the transaction-service");
		return new ResponseEntity<Resources<TransactionDto>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/report")
	public ResponseEntity<?> getReport(){
		
		ResponseEntity<Resources<TransactionDto>> transactions = restTemplate.exchange(
				"http://persistence-service/transactions", HttpMethod.GET, null,
				new ParameterizedTypeReference<Resources<TransactionDto>>() {
				});
		
		Map<String, Map<String, List<TransactionDto>>> transactionsByIBAN = transactions.getBody().getContent()
				.stream()
				.collect(Collectors.groupingBy(TransactionDto::getName, Collectors.groupingBy(TransactionDto::getIban)));
		
		Map<String, Map<String, Long>> ibanAndNrOfTr = transactions.getBody().getContent()
				.stream()
				.collect(Collectors.groupingBy(TransactionDto::getName, ibanAndCount()));
		
		Map<String, Map<String, List<TransactionDto>>> transactionTypesAndCnp = transactions.getBody().getContent().stream()
				.collect(Collectors.groupingBy(TransactionDto::getTransactionType, Collectors.groupingBy(TransactionDto::getCnp)));
		
		Map<String, Map<String, Map<String, List<TransactionDto>>>> xxx = transactions.getBody().getContent().stream()
				.collect(Collectors.groupingBy(TransactionDto::getName, transactionsByIbanAndType()));
		
		Map<Object, List<TransactionDto>> groupedData = 
			    transactions.getBody().getContent().stream().collect(Collectors.groupingBy(p -> Arrays.asList(p.getName(),p.getIban(),p.getTransactionType(),p.getCnp())));
		
		Map collectMany = collectMany(transactions.getBody().getContent(), TransactionDto::getCnp, TransactionDto::getTransactionType, TransactionDto::getIban, TransactionDto::getName);
		
		ResponseEntity<?> responseEntity = new ResponseEntity<>(collectMany, HttpStatus.OK);
		return responseEntity;
	}
	
	private Collector<TransactionDto, ?, Map<String, Map<String, List<TransactionDto>>>> transactionsByIbanAndType(){
		return Collectors.groupingBy(TransactionDto::getIban, Collectors.groupingBy(TransactionDto::getTransactionType));
	}
	
	private Collector<TransactionDto, ?, Map<String, Long>> ibanAndCount(){
		return Collectors.groupingBy(TransactionDto::getIban, Collectors.counting());
	}
	
	public static <T> Map collectMany(Collection<T> data, Function<T, ?>... groupers) {
//		Collections.reverse(Arrays.asList(groupers));
	    Iterator<Function<T, ?>> iter = Arrays.asList(groupers).iterator();
	    Collector collector = Collectors.groupingBy(iter.next());
	    while (iter.hasNext()) {
	        collector = Collectors.groupingBy(iter.next(), collector);
	    }
	    return (Map) data.stream().collect(collector);
	}
	
	
	
}
