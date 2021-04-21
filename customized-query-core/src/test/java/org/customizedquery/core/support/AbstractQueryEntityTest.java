package org.customizedquery.core.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.customizedquery.core.support.Range.between;
import static org.customizedquery.core.support.Range.ge;

public class AbstractQueryEntityTest extends Assertions {

    public static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testConvert() throws JsonProcessingException {
        TestQuery testQuery = AbstractRecoverableQuery.builderProxy(new TestQuery.TestQueryBuilder())
                .id(between(1L, 2L))
                .name("little_a")
                .balance(BigDecimal.ONE)
                .birthday(ge(new Date(0)))
                .orderByBirthday(false)
                .orderByName(true)
                .orderById(true)
                .build();

        {
            System.err.println(mapper.writeValueAsString(testQuery));
            JsonNode node = mapper.valueToTree(testQuery);

            Map<String, Object> expectedCondition = new LinkedHashMap<String, Object>() {{
                put("id_between", new Object[]{1L, 2L});
                put("name", "little_a");
                put("balance", BigDecimal.ONE);
                put("birthday_ge", new Date(0));
            }};
            Map<String, Boolean> expectOrderBy = new LinkedHashMap<String, Boolean>() {{
                put("birthday", false);
                put("name", true);
                put("id", true);
            }};

            // All member variables must be null
            assertTrue(node.get("id").isNull());
            assertTrue(node.get("name").isNull());
            assertTrue(node.get("balance").isNull());
            assertTrue(node.get("birthday").isNull());
            assertTrue(node.get("orderByBirthday").isNull());
            assertTrue(node.get("orderByName").isNull());
            assertTrue(node.get("orderById").isNull());

            // condition
            assertEquals(expectedCondition.size(), node.get("condition").size());
            assertEquals(mapper.writeValueAsString(expectedCondition), node.get("condition").toString());

            // orderBy
            assertEquals(expectOrderBy.size(), node.get("orderBy").size());
            assertEquals(mapper.writeValueAsString(expectOrderBy), node.get("orderBy").toString());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class TestQuery extends AbstractRecoverableQuery {

        private Range<Long> id;

        private String name;

        private BigDecimal balance;

        private Range<Date> birthday;

        private Boolean orderById;

        private Boolean orderByName;

        private Boolean orderByBirthday;

    }

}
