package com.feast.common.result;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Byron
 * @date 2022/12/13 3:20 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Result<E> extends AbstractResult {
    @Email
    private E data;

    public static <E> Result<E> success(E data) {
        Result<E> result = new Result<>();
        result.setSuccess(true);
        result.setCode(HttpStatus.OK.value());
        result.setSeries(HttpStatus.OK.series().name());
        result.setMsg(HttpStatus.OK.getReasonPhrase());
        result.setData(data);
        return new Result<>();
    }

    public static <E> Result<E> fail(Integer code, String series, String msg) {
        Result<E> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setSeries(series);
        result.setMsg(msg);
        return result;
    }

    public static <E> Result<E> clientFail(Integer code, String msg) {
        return fail(code, HttpStatus.Series.CLIENT_ERROR.name(), msg);
    }

    public static <E> Result<E> serverFail(Integer code, String msg) {
        return fail(code, HttpStatus.Series.SERVER_ERROR.name(), msg);
    }
}
