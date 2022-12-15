package com.feast.common.exception.handler;

import com.feast.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Byron
 * @date 2022/12/15 4:00 下午
 */
@Component
public class WebClientRequestExceptionCommonHandler extends AbstractExceptionCommonHandler{
    @Override
    public Mono<ServerResponse> handlerException(ServerWebExchange exchange, Exception e) {
        super.errorLog(exchange, e);
        return ServerResponse.status(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Result.fail(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.series().name(), Optional.ofNullable(e.getMessage()).orElse(HttpStatus.BAD_REQUEST.getReasonPhrase())));
    }

    @Override
    public boolean support(Class<? extends Exception> targetClass) {
        return WebClientRequestException.class.isAssignableFrom(targetClass);
    }
}
