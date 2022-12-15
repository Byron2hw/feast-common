package com.feast.common.exception;

import com.feast.common.result.AbstractResult;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/15 4:51 下午
 */
public class MQException extends AbstractSupplierException{
    protected MQException(Supplier<? extends AbstractResult> resultSupplier) {
        super(resultSupplier);
    }

    protected MQException(Supplier<? extends AbstractResult> resultSupplier, Throwable ex) {
        super(resultSupplier, ex);
    }
}
