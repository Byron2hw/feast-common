package com.feast.common.util;

import com.feast.common.exception.AbstractSupplierException;
import com.feast.common.exception.BizException;
import com.feast.common.exception.CacheException;
import com.feast.common.exception.ExceptionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author Byron
 * @date 2022/12/15 10:01 上午
 */
public class AssertUtils {
    private AssertUtils(){}

    public static void assertNotNull(final Object target, Integer code, String message) {
        if (Objects.isNull(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertTrue(final boolean target, Integer code, String message) {
        if (!target) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertFalse(final boolean target, Integer code, String message) {
        if (target) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertEquals(final Object target, final Object anotherTarget, Integer code, String message) {
        if (!Objects.equals(target, anotherTarget)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertNotEquals(final Object target, final Object anotherTarget, Integer code, String message) {
        if (Objects.equals(target, anotherTarget)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertBlank(final CharSequence target, Integer code, String message) {
        if (StringUtils.isNotBlank(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertNotBlank(final CharSequence target, Integer code, String message) {
        if (StringUtils.isBlank(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertEmpty(final CharSequence target, Integer code, String message) {
        if (StringUtils.isNotEmpty(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertNotEmpty(final CharSequence target, Integer code, String message) {
        if (StringUtils.isEmpty(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertEmpty(final Collection<?> target, Integer code, String message) {
        if (!CollectionUtils.isEmpty(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertNotEmpty(final Collection<?> target, Integer code, String message) {
        if (CollectionUtils.isEmpty(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertEmpty(final Map<?, ?> target, Integer code, String message) {
        if (!CollectionUtils.isEmpty(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertNotEmpty(final Map<?, ?> target, Integer code, String message) {
        if (CollectionUtils.isEmpty(target)) {
            throw ExceptionFactory.create(BizException.class, code, message);
        }
    }

    public static void assertCacheNull(final Object target, String message) {
        if (Objects.nonNull(target)) {
            throw ExceptionFactory.create(CacheException.class, message);
        }
    }

    public static void assertCacheNotNull(final Object target, String message) {
        if (Objects.isNull(target)) {
            throw ExceptionFactory.create(CacheException.class, message);
        }
    }

    public static void assertSupplierTure(BooleanSupplier supplier, Class<? extends AbstractSupplierException> targetClass, Integer code, String message) {
        if (!supplier.getAsBoolean()) {
            throw ExceptionFactory.create(targetClass, code, message);
        }
    }

    public static void assertSupplierFalse(BooleanSupplier supplier, Class<? extends AbstractSupplierException> targetClass, Integer code, String message) {
        if (supplier.getAsBoolean()) {
            throw ExceptionFactory.create(targetClass, code, message);
        }
    }
}
