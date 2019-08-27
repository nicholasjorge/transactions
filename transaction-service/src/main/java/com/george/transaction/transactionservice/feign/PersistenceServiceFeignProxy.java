package com.george.transaction.transactionservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "persistence-service", fallback = PersistenceServiceFeignProxyFallback.class)
//@FeignClient(name = "api-gateway")
//@RibbonClient(name = "persistence-service")
public interface PersistenceServiceFeignProxy extends PersistenceServiceProxy {

}
