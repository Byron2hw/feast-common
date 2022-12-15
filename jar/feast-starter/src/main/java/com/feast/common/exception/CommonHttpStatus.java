package com.feast.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * @author Byron
 * @date 2022/12/15 4:59 下午
 */
@Getter
@AllArgsConstructor
public enum CommonHttpStatus implements HttpStatusCode {
    CACHE_FAILED(1000, Series.CACHE_ERROR, "Cache Failed"),
    DATA_ACCESS_FAILED(1001, Series.DATA_ACCESS_ERROR, "Data Access Failed"),
    MQ_FAILED(1002, Series.MQ_ERROR, "Message Queue Failed"),
    PARAMETER_FAILED(1003, Series.PARAMETER_ERROR, "Parameter Failed"),
    ;

    /**
     * Status value
     */
    private final int value;
    private final Series series;
    private final String reasonPhrase;

    public Series series() {
        return this.series;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public boolean is1xxInformational() {
        return false;
    }

    @Override
    public boolean is2xxSuccessful() {
        return false;
    }

    @Override
    public boolean is3xxRedirection() {
        return false;
    }

    @Override
    public boolean is4xxClientError() {
        return this.series() == Series.PARAMETER_ERROR;
    }

    @Override
    public boolean is5xxServerError() {
        return this.series() != Series.PARAMETER_ERROR;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Getter
    public static enum Series {
        CACHE_ERROR,
        DATA_ACCESS_ERROR,
        MQ_ERROR,
        PARAMETER_ERROR,
        ;
    }
}
