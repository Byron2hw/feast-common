package com.feast.common.exception;

import com.feast.common.result.AbstractResult;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/15 4:54 下午
 */
public class CacheException extends AbstractSupplierException{
    protected CacheException(Supplier<? extends AbstractResult> resultSupplier) {
        super(resultSupplier);
    }

    protected CacheException(Supplier<? extends AbstractResult> resultSupplier, Throwable ex) {
        super(resultSupplier, ex);
    }
}
