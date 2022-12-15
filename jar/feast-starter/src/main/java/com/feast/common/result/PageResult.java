package com.feast.common.result;

import com.feast.common.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collection;

/**
 * @author Byron
 * @date 2022/12/13 3:46 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<E> extends AbstractResult {
    private Integer pageIndex;
    private Integer pageSize;
    private Long total;
    private Collection<E> dataSet;

    public static <E> PageResult<E> success(Pagination<E> page) {
        PageResult<E> pageResult = new PageResult<>();
        pageResult.setSuccess(true);
        pageResult.setCode(HttpStatus.OK.value());
        pageResult.setSeries(HttpStatus.OK.series().name());
        pageResult.setMsg(HttpStatus.OK.getReasonPhrase());
        pageResult.setPageIndex(page.getPageIndex());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setDataSet(page.getDataSet());
        return pageResult;
    }

    public static <E> PageResult<E> fail(Integer code, String series, String msg) {
        PageResult<E> pageResult = new PageResult<>();
        pageResult.setSuccess(false);
        pageResult.setCode(code);
        pageResult.setSeries(series);
        pageResult.setMsg(msg);
        return pageResult;
    }

    public static <E> PageResult<E> fail(Integer code, String series, String msg, Pagination<E> page) {
        PageResult<E> pageResult = new PageResult<>();
        pageResult.setSuccess(false);
        pageResult.setCode(code);
        pageResult.setSeries(series);
        pageResult.setMsg(msg);
        pageResult.setPageIndex(page.getPageIndex());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setDataSet(page.getDataSet());
        return pageResult;
    }

    public static <E> PageResult<E> clientFail(Integer code, String msg) {
        return fail(code, HttpStatus.Series.CLIENT_ERROR.name(), msg);
    }

    public static <E> PageResult<E> clientFail(Integer code, String msg, Pagination<E> page) {
        return fail(code, HttpStatus.Series.CLIENT_ERROR.name(), msg, page);
    }

    public static <E> PageResult<E> serverFail(Integer code, String msg) {
        return fail(code, HttpStatus.Series.SERVER_ERROR.name(), msg);
    }

    public static <E> PageResult<E> serverFail(Integer code, String msg, Pagination<E> page) {
        return fail(code, HttpStatus.Series.SERVER_ERROR.name(), msg, page);
    }

}
