package com.george.transaction.persistenceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.george.transaction.persistenceservice.model.Transaction;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
