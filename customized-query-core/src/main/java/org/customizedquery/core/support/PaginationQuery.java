package org.customizedquery.core.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页查询包装器
 *
 * @author Jover Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationQuery<T> {

    @Min(1)
    @NotNull
    private Integer current;

    @Min(1)
    @NotNull
    private Integer size;

    private T query;

    public static <T> PaginationQuery<T> query(int current, int size) {
        return query(current, size, null);
    }

    public static <T> PaginationQuery<T> query(int current, int size, T query) {
        return new PaginationQuery<>(current, size, query);
    }

    @JsonIgnore
    public Integer getOffset() {
        return getSize() * (getCurrent() - 1);
    }

    @JsonIgnore
    public Integer getLimit() {
        return getSize();
    }

}
