package com.george.transaction.transactionservice.service;

import com.george.transaction.transactionservice.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public Map getReport(final Collection<TransactionDto> transactions) {
        //        Map<Object,List<TransactionDto>> groupedData = transactions.stream()
        //                .collect(Collectors.groupingBy(p -> Arrays.asList(p.getName(), p.getIban(), p.getTransactionType(), p.getCnp())));
        Map collectMany = collectMany(transactions, TransactionDto::getCnp, TransactionDto::getTransactionType, TransactionDto::getIban, TransactionDto::getName);
        return collectMany;
    }

    //    private Collector<TransactionDto,?,Map<String,Map<String,List<TransactionDto>>>> byIbanAndType() {
    //        return Collectors.groupingBy(TransactionDto::getIban, Collectors.groupingBy(TransactionDto::getTransactionType));
    //    }
    //
    //    private Collector<TransactionDto,?,Map<String,Long>> byIbanAndCount() {
    //        return Collectors.groupingBy(TransactionDto::getIban, Collectors.counting());
    //    }

    private static <T> Map collectMany(Collection<T> data, Function<T,?>... groupers) {
        //		Collections.reverse(Arrays.asList(groupers));
        Iterator<Function<T,?>> iter = Arrays.asList(groupers).iterator();
        Collector collector = Collectors.groupingBy(iter.next());
        while (iter.hasNext()) {
            collector = Collectors.groupingBy(iter.next(), collector);
        }
        return (Map) data.stream().collect(collector);
    }

}
