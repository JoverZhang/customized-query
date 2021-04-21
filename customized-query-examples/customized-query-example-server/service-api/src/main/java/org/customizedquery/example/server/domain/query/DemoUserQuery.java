package org.customizedquery.example.server.domain.query;

import lombok.*;
import org.customizedquery.core.support.AbstractRecoverableQuery;
import org.customizedquery.core.support.Range;

import java.util.Date;

/**
 * 查询实体定义样例
 *
 * <p>定义一个与数据库实体相对应的查询实体 (以 Query 结尾). <br>
 * 继承 {@link AbstractRecoverableQuery}, 且使用 lombok 的 @Builder 注解 {@link Builder}. <br>
 * 并包装 @Builder 自动生成的静态方法 builder(), 包装后方法实例: {@link #queryBuilder()}
 * <p>
 * 最后根据下方实例定义查询实体
 *
 * @author Jover Zhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemoUserQuery extends AbstractRecoverableQuery {

    /**
     * 定义即允许等值查询, 又允许区间查询的字段
     */
    private Range<Long> id;

    /**
     * 定义允许等值查询的字段
     */
    private String name;

    /**
     * 定义即允许等值查询, 又允许区间查询的字段
     */
    private Range<Date> birthday;

    /**
     * 定义允许等值查询的字段
     */
    private Boolean orderById;

    /**
     * 定义允许排序的字段
     */
    private Boolean orderByName;

    /**
     * 定义允许排序的字段
     */
    private Boolean orderByBirthday;

    /**
     * ***重点***
     *
     * <p>使用 lombok 的 @Builder 注解会为类自动生成类似下方的静态方法:
     * <pre>{@code
     *     public static DemoUserQueryBuilder builder() {
     *         return new DemoUserQueryBuilder();
     *     }
     * }</pre>
     *
     * <p>所以可以自定义以下方法, 对 lombok 自动生成的静态方法 builder() 加一层动态代理.
     *
     * <pre>{@code
     *     public static DemoUserQueryBuilder queryBuilder() {
     *         return new builderProxy(builder());
     *     }
     * }</pre>
     *
     * <p>PS:
     */
    public static DemoUserQueryBuilder queryBuilder() {
        return builderProxy(builder());
    }

}
