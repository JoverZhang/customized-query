package org.customizedquery.core.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 分页包装
 *
 * @author Jover Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    private List<T> records = Collections.emptyList();

    /**
     * 总页数
     */
    private Long total;

    /**
     * 当前分页总页数
     */
    private Long pages;

}
