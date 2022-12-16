package com.feast.common.exception.handler;

import com.feast.common.exception.AbstractSupplierException;
import com.feast.common.result.AbstractResult;
import com.feast.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/15 2:09 下午
 */
@Slf4j
public abstract class AbstractExceptionCommonHandler implements ExceptionCommonHandler {
    protected void warnLog(ServerWebExchange exchange, Exception e) {
        ServerHttpRequest request = exchange.getRequest();
        log.warn("【exception handler】method: {}, uri: {}, msg: {}", request.getMethod(), request.getURI(), e.getMessage(), e);
    }

    protected void errorLog(ServerWebExchange exchange, Exception e) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("【exception handler】method: {}, uri: {}, msg: {}", request.getMethod(), request.getURI(), e.getMessage(), e);
    }

    protected Mono<ServerResponse> monoServerResponse(int status, Exception e) {
        Supplier<? extends AbstractResult> resultSupplier = ((AbstractSupplierException)e).getResultSupplier();
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(resultSupplier.get());
    }

    protected Mono<ServerResponse> monoServerResponse(int status, Result<?> result) {
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(result);
    }
}
