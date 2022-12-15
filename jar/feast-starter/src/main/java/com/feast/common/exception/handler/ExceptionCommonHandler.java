package com.feast.common.exception.handler;

import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Byron
 * @date 2022/12/15 2:06 下午
 */
public interface ExceptionCommonHandler {

    /**
     * 处理异常
     * @param exchange ServerWebExchange
     * @param e Exception
     * @return ResponseEntity
     */
    Mono<ServerResponse> handlerException(ServerWebExchange exchange, Exception e);

    /**
     * 是否支持
     * @param targetClass targetClass
     * @return boolean
     */
    boolean support(Class<? extends Exception> targetClass);
}
