package com.feast.common.exception;

import com.feast.common.result.AbstractResult;

import java.util.function.Supplier;

/**
 * @author Byron
 * @date 2022/12/14 3:49 下午
 */
public class BizException extends AbstractSupplierException {

    protected BizException(Supplier<? extends AbstractResult> resultSupplier) {
        super(resultSupplier);
    }

    protected BizException(Supplier<? extends AbstractResult> resultSupplier, Throwable ex) {
        super(resultSupplier, ex);
    }
}
