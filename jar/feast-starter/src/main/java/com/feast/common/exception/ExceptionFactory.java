package com.feast.common.exception;

import com.feast.common.result.Result;

/**
 * @author Byron
 * @date 2022/12/15 10:58 上午
 */
public class ExceptionFactory {
    private ExceptionFactory(){}

    public static RuntimeException create(Class<? extends AbstractSupplierException> target, String message) {
        return create(target, null, message, null);
    }

    public static RuntimeException create(Class<? extends AbstractSupplierException> target, Integer code, String message) {
        return create(target, code, message, null);
    }

    public static RuntimeException create(Class<? extends AbstractSupplierException> target, Integer code, String message, Throwable cause) {
        if (BizException.class.isAssignableFrom(target)) {
            return new BizException(() -> Result.serverFail(code, message), cause);
        } else if (ClientException.class.isAssignableFrom(target)) {
            return new ClientException(() -> Result.clientFail(code, message), cause);
        } else if (ParameterException.class.isAssignableFrom(target)) {
            return new ParameterException(() -> Result.fail(CommonHttpStatus.PARAMETER_FAILED.getValue(), CommonHttpStatus.Series.PARAMETER_ERROR.name(), message), cause);
        } else if (DataAccessException.class.isAssignableFrom(target)) {
            return new DataAccessException(() -> Result.fail(CommonHttpStatus.DATA_ACCESS_FAILED.getValue(), CommonHttpStatus.Series.DATA_ACCESS_ERROR.name(), message), cause);
        } else if (MQException.class.isAssignableFrom(target)) {
            return new MQException(() -> Result.fail(CommonHttpStatus.MQ_FAILED.getValue(), CommonHttpStatus.Series.MQ_ERROR.name(), message), cause);
        } else if (CacheException.class.isAssignableFrom(target)) {
            return new CacheException(() -> Result.fail(CommonHttpStatus.CACHE_FAILED.getValue(), CommonHttpStatus.Series.CACHE_ERROR.name(), message), cause);
        }
        return new RuntimeException(message, cause);
    }
}
