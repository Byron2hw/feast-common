package com.feast.common.exception;

import com.feast.common.result.AbstractResult;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/15 11:40 上午
 */
public class ClientException extends AbstractSupplierException {
    public ClientException(Supplier<? extends AbstractResult> resultSupplier) {
        super(resultSupplier);
    }

    public ClientException(Supplier<? extends AbstractResult> resultSupplier, Throwable ex) {
        super(resultSupplier, ex);
    }
}
