package com.feast.common.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author Byron
 * @date 2022/12/13 3:16 下午
 */
@Data
@JsonSerialize
public abstract class AbstractResult {
    protected boolean success;
    protected Integer code;
    protected String msg;
    protected String series;
}
