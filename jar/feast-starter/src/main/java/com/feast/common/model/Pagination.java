package com.feast.common.model;

import lombok.Data;

import java.util.Collection;

/**
 * @author Byron
 * @date 2022/12/13 3:56 下午
 */
@Data
public class Pagination<E> {
    private Integer pageIndex;
    private Integer pageSize;
    private Long total;
    private Collection<E> dataSet;
}
