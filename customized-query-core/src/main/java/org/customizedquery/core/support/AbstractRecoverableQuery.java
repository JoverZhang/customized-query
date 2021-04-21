package org.customizedquery.core.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.customizedquery.core.utils.StringUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jover Zhang
 */
@SuppressWarnings("unchecked")
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractRecoverableQuery {

    static final String ORDER_BY_PREFIX = "orderBy";

    private static final String BUILDER_METHOD_TAIL = "Builder";

    private static final String BUILD_METHOD = "build";

    @JsonProperty
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, Object> condition;

    @JsonProperty
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, Boolean> orderBy;

    /**
     * 断言对象是否为构建者对象
     *
     * @throws IllegalArgumentException 非法对象
     */
    private static void assertBuildable(Object builder) {
        // 仅允许名为 XxxBuilder 的对象
        if (!builder.getClass().getName().endsWith(BUILDER_METHOD_TAIL)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 代理 builder (lombok 的 @Builder).
     *
     * <p>记录调用过程. 在后续需要时, 可复现调用过程
     *
     * @param builder 构建者对象 (lombok 的 @Builder)
     * @return 代理后的 builder
     */
    protected static <T> T builderProxy(T builder) {
        assertBuildable(builder);

        Enhancer eh = new Enhancer();
        eh.setSuperclass(builder.getClass());
        eh.setCallback(new BuilderProxyInterceptor(builder));

        return (T) eh.create();
    }

    /**
     * 构建者代理拦截器
     */
    @RequiredArgsConstructor
    static class BuilderProxyInterceptor implements MethodInterceptor {

        private final Object obj;

        private Map<String, Object> conditionMap;

        private Map<String, Boolean> orderByMap;

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            String methodName = method.getName();

            // build(): 直接调用 obj 自身的 build(),
            // 并装载查询信息,
            // 且 return build() 的结果
            if (BUILD_METHOD.equals(methodName)) {
                AbstractRecoverableQuery query = (AbstractRecoverableQuery) method.invoke(obj, args);
                query.setCondition(conditionMap);
                query.setOrderBy(orderByMap);
                return query;
            }

            // orderByXxx(): 按顺序记录当前操作
            if (methodName.startsWith(ORDER_BY_PREFIX)) {
                addOrderBy(methodName, (Boolean) args[0]);
            }
            // 非 build, 非 orderByXxx(), 剩下都是 condition
            else {
                addCondition(methodName, args[0]);
            }

            return proxy;
        }

        private void addCondition(String methodName, Object condition) {
            if (conditionMap == null) {
                conditionMap = new LinkedHashMap<>();
            }

            String fieldName;
            Object val;

            /*
             * PS:
             *
             * Range<Long> id;
             * Range<String> name;
             *
             * queryBuilder()
             * .id(between(1L, 10L)) // fieldName = id_between, val = [1L, 10L]
             * .name(eq("abc"))      // fieldName = name_eq,    val = "abc"
             */
            if (condition instanceof Range) {
                Range<?> rangeCondition = (Range<?>) condition;
                fieldName = methodName + "_" + rangeCondition.getCompare().getAlias();

                // 仅当 between 时, 会出现两个参数
                if (rangeCondition.getCompare() == Range.Compare.BETWEEN) {
                    val = Arrays.asList(rangeCondition.getVal(), rangeCondition.getVal2());
                } else {
                    val = rangeCondition.getVal();
                }
            }

            /*
             * PS:
             *
             * Long id;
             * String name;
             *
             * queryBuilder()
             * .id(1L)      // fieldName = id,   val = 1L
             * .name("abc") // fieldName = name, val = "abc"
             */
            else {
                fieldName = methodName;
                val = condition;
            }

            conditionMap.put(fieldName, val);
        }

        private void addOrderBy(String methodName, boolean asc) {
            if (orderByMap == null) {
                orderByMap = new LinkedHashMap<>();
            }

            /*
             * PS:
             *
             * Boolean orderById;
             * Boolean orderByName;
             *
             * queryBuilder()
             * .orderById(true)     // fieldName = id,   asc = true
             * .orderByName(false)  // fieldName = name, asc = false
             */
            String fieldName = StringUtils.firstToLowerCase(methodName.substring(7));
            orderByMap.put(fieldName, asc);
        }

    }

}
