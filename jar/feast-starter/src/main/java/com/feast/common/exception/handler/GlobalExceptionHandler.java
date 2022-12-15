package com.feast.common.exception.handler;

import com.feast.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Byron
 * @date 2022/12/14 11:32 上午
 */
@Slf4j
@ControllerAdvice
public record GlobalExceptionHandler(List<ExceptionCommonHandler> exceptionHandlers) {

    @ExceptionHandler({Exception.class})
    public Mono<ServerResponse> defaultErrorHandler(ServerWebExchange exchange, Exception e) {
        ExceptionCommonHandler exceptionCommonHandler = exceptionHandlers.stream()
                .filter(exceptionHandler -> exceptionHandler.support(e.getClass()))
                .findFirst()
                .orElse(null);
        if (Objects.nonNull(exceptionCommonHandler)) {
            return exceptionCommonHandler.handlerException(exchange, e);
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Result.serverFail(HttpStatus.INTERNAL_SERVER_ERROR.value(), Optional.ofNullable(e.getMessage()).orElse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
    }

}
