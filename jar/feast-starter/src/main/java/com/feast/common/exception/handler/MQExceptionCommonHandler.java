package com.feast.common.exception.handler;

import com.feast.common.exception.AbstractSupplierException;
import com.feast.common.exception.MQException;
import com.feast.common.result.AbstractResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/15 4:55 下午
 */
@Component
public class MQExceptionCommonHandler extends AbstractExceptionCommonHandler{
    @Override
    public Mono<ServerResponse> handlerException(ServerWebExchange exchange, Exception e) {
        super.warnLog(exchange, e);
        Supplier<? extends AbstractResult> resultSupplier = ((AbstractSupplierException)e).getResultSupplier();
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(resultSupplier.get());
    }

    @Override
    public boolean support(Class<? extends Exception> targetClass) {
        return MQException.class.isAssignableFrom(targetClass);
    }
}
