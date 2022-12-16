package com.feast.common.exception.handler;

import com.feast.common.exception.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Byron
 * @date 2022/12/15 2:21 下午
 */
@Component
public class ClientExceptionCommonHandler extends AbstractExceptionCommonHandler {

    @Override
    public Mono<ServerResponse> handlerException(ServerWebExchange exchange, Exception e) {
        super.errorLog(exchange, e);
        return super.monoServerResponse(HttpStatus.BAD_REQUEST.value(), e);
    }

    @Override
    public boolean support(Class<? extends Exception> targetClass) {
        return ClientException.class.isAssignableFrom(targetClass);
    }
}
