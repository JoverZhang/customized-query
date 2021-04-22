package org.customizedquery.core.support;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.customizedquery.core.support.Range.*;

public class MybatisPlusRecoverTest {

    @Test
    public void testRecover() {
        AQuery aQuery = AQuery.queryBuilder()
                .name("123")
                .birthday(gt(new Date(0)))
                .birthday(lt(new Date(0)))
                .id(between(1L, 10L))
                .createTime(between(new Date(0), new Date(10000)))
                .modifiedTime(eq(new Date()))
                .orderByBirthday(false)
                .orderById(true)
                .orderByCreateTime(true)
                .orderByModifiedTime(false)
                .build();

        QueryWrapper<A> wrapper = MybatisPlusRecover.toWrapper(aQuery, A.class);
        System.out.println(wrapper.getTargetSql());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class AQuery extends AbstractRecoverableQuery {

        private Range<Long> id;

        private String name;

        private Range<Date> birthday;

        private Range<Date> createTime;

        private Range<Date> modifiedTime;

        private Boolean orderById;

        private Boolean orderByBirthday;

        private Boolean orderByCreateTime;

        private Boolean orderByModifiedTime;

        public static AQuery.AQueryBuilder queryBuilder() {
            return builderProxy(builder());
        }

    }

    public static class A {

        private Long id;

        private String name;

        private Date birthday;

        @TableField("`create_time`")
        private Range<Date> createTime;

        private Range<Date> modifiedTime;

    }

}
