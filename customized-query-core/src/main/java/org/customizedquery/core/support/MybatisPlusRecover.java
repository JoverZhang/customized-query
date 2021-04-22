package org.customizedquery.core.support;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import org.customizedquery.core.utils.Asserts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Jover Zhang
 */
public class MybatisPlusRecover {

    @SneakyThrows
    public static <S extends AbstractRecoverableQuery, T> QueryWrapper<T> toWrapper(S recoverable, Class<T> targetClass) {
        Asserts.notNull(recoverable);
        Asserts.notNull(targetClass);
        return doToWrapper(recoverable, targetClass);
    }

    @SuppressWarnings("unchecked")
    private static <S extends AbstractRecoverableQuery, T> QueryWrapper<T> doToWrapper(S recoverable,
                                                                                       Class<T> targetClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        QueryWrapper<T> wrapper = new QueryWrapper<>();

        // Compose "where"
        Method getConditionMethod = AbstractRecoverableQuery.class.getDeclaredMethod("getCondition");
        Map<String, Object> conditionMap = (Map<String, Object>) getConditionMethod.invoke(recoverable);
        if (conditionMap != null) {
            for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
                String fieldName;
                String conditionName = entry.getKey();
                Object value = entry.getValue();

                // Is range
                if (conditionName.contains("_")) {
                    int conditionNameLen = conditionName.length();
                    // eq
                    if (conditionName.endsWith("_" + Range.Compare.EQ.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 3);
                        wrapper.eq(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                    // ne
                    else if (conditionName.endsWith("_" + Range.Compare.NE.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 3);
                        wrapper.ne(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                    // gt
                    else if (conditionName.endsWith("_" + Range.Compare.GT.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 3);
                        wrapper.gt(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                    // ge
                    else if (conditionName.endsWith("_" + Range.Compare.GE.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 3);
                        wrapper.ge(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                    // lt
                    else if (conditionName.endsWith("_" + Range.Compare.LT.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 3);
                        wrapper.lt(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                    // le
                    else if (conditionName.endsWith("_" + Range.Compare.LE.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 3);
                        wrapper.le(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                    // between
                    else if (conditionName.endsWith("_" + Range.Compare.BETWEEN.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 8);
                        List<?> values;
                        if (!(value instanceof List) || (values = (List<?>) value).size() < 2) {
                            throw new IllegalArgumentException();
                        }
                        wrapper.between(fieldNameConvert(fieldName, targetClass), values.get(0), values.get(1));
                    }
                    // like right
                    else if (conditionName.endsWith("_" + Range.Compare.LIKE_RIGHT.getAlias())) {
                        fieldName = conditionName.substring(0, conditionNameLen - 10);
                        wrapper.likeRight(value != null, fieldNameConvert(fieldName, targetClass), value);
                    }
                }
                // Normal condition
                else {
                    fieldName = conditionName;
                    wrapper.eq(value != null, fieldNameConvert(fieldName, targetClass), value);
                }
            }
        }

        // Compose "order by"
        Method getOrderByMethod = AbstractRecoverableQuery.class.getDeclaredMethod("getOrderBy");
        Map<String, Boolean> orderByMap = (Map<String, Boolean>) getOrderByMethod.invoke(recoverable);
        if (orderByMap != null) {
            for (Map.Entry<String, Boolean> entry : orderByMap.entrySet()) {
                String fieldName = fieldNameConvert(entry.getKey(), targetClass);
                Boolean isAscending = entry.getValue();
                if (isAscending == null || isAscending) {
                    wrapper.orderByAsc(fieldName);
                } else {
                    wrapper.orderByDesc(fieldName);
                }
            }
        }

        return wrapper;
    }

    private static String fieldNameConvert(String fieldName, Class<?> targetClass) {
        if (!StringUtils.isCamel(fieldName)) {
            throw new IllegalArgumentException("The field name " + fieldName + " is not camel");
        }
        try {
            TableField tableField = targetClass.getDeclaredField(fieldName).getAnnotation(TableField.class);
            if (tableField != null) {
                return tableField.value();
            }
            return StringUtils.camelToUnderline(fieldName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Not found field name " + fieldName, e);
        }
    }

}
