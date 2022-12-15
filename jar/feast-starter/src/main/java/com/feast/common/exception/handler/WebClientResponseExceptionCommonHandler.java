package com.feast.common.exception.handler;

import com.feast.common.result.Result;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Byron
 * @date 2022/12/15 4:03 下午
 */
@Component
public class WebClientResponseExceptionCommonHandler extends AbstractExceptionCommonHandler{
    @Override
    public Mono<ServerResponse> handlerException(ServerWebExchange exchange, Exception e) {
        super.errorLog(exchange, e);
        HttpStatusCode statusCode = ((WebClientResponseException) e).getStatusCode();
        return ServerResponse.status(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Result.serverFail(statusCode.value(), e.getMessage()));
    }

    @Override
    public boolean support(Class<? extends Exception> targetClass) {
        return WebClientResponseException.class.isAssignableFrom(targetClass);
    }
}
