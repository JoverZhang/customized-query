package org.customizedquery.core.support;

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
                .orderByBirthday(false)
                .orderById(true)
                .build();

        QueryWrapper<A> wrapper = MybatisPlusRecover.recover(aQuery, A.class);
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

        private Boolean orderById;

        private Boolean orderByBirthday;

        public static AQuery.AQueryBuilder queryBuilder() {
            return builderProxy(builder());
        }

    }

    public static class A {

        private Long id;

        private String name;

        private Date birthday;

    }

}
