package com.feast.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

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
}
