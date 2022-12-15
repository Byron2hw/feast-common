package com.feast.common.exception;

import com.feast.common.result.AbstractResult;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/15 4:37 下午
 */
public abstract class AbstractSupplierException extends WebClientException {
    private final transient Supplier<? extends AbstractResult> resultSupplier;

    protected AbstractSupplierException(Supplier<? extends AbstractResult> resultSupplier) {
        super(resultSupplier.get().getMsg());
        this.resultSupplier = resultSupplier;
    }

    protected AbstractSupplierException(Supplier<? extends AbstractResult> resultSupplier, Throwable ex) {
        super(resultSupplier.get().getMsg(), ex);
        this.resultSupplier = resultSupplier;
    }

    public Supplier<? extends AbstractResult> getResultSupplier() {
        return this.resultSupplier;
    }
}
