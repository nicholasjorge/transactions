package com.george.transaction.transactionservice.feign;

import com.george.transaction.transactionservice.dto.TransactionDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PersistenceServiceFeignProxyFallback implements PersistenceServiceProxy {

    @Override
    public Resources<TransactionDto> getTransactions() {
        return new Resources<TransactionDto>(new ArrayList<>(), (Iterable<Link>) null);
    }
}
