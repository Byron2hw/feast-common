package com.feast.common.exception.handler;

import com.feast.common.exception.ParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Byron
 * @date 2022/12/15 4:55 下午
 */
@Component
public class ParameterExceptionCommonHandler extends AbstractExceptionCommonHandler{
    @Override
    public Mono<ServerResponse> handlerException(ServerWebExchange exchange, Exception e) {
        super.warnLog(exchange, e);
        return super.monoServerResponse(HttpStatus.BAD_REQUEST.value(), e);
    }

    @Override
    public boolean support(Class<? extends Exception> targetClass) {
        return ParameterException.class.isAssignableFrom(targetClass);
    }
}
