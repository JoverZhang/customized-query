package org.customizedquery.core.support;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jover Zhang
 */
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class Range<T> {

    private Compare compare;

    private T val;

    /**
     * 当前仅用于 {@link #between(Object, Object)}
     */
    private T val2;

    public Range(Compare compare, T val) {
        this.compare = compare;
        this.val = val;
    }

    public Range(Compare compare, T val, T val2) {
        this.compare = compare;
        this.val = val;
        this.val2 = val2;
    }

    public static <T> Range<T> eq(T val) {
        return new Range<>(Compare.EQ, val);
    }

    public static <T> Range<T> ne(T val) {
        return new Range<>(Compare.NE, val);
    }

    public static <T> Range<T> gt(T val) {
        return new Range<>(Compare.GT, val);
    }

    public static <T> Range<T> ge(T val) {
        return new Range<>(Compare.GE, val);
    }

    public static <T> Range<T> lt(T val) {
        return new Range<>(Compare.LT, val);
    }

    public static <T> Range<T> le(T val) {
        return new Range<>(Compare.LE, val);
    }

    public static <T> Range<T> between(T val1, T val2) {
        return new Range<>(Compare.BETWEEN, val1, val2);
    }

    public static <T> Range<T> likeRight(T val) {
        return new Range<>(Compare.LIKE_RIGHT, val);
    }

    enum Compare {
        /**
         * 等于
         */
        EQ("eq"),
        /**
         * 不等于
         */
        NE("ne"),
        /**
         * 大于
         */
        GT("gt"),
        /**
         * 大于等于
         */
        GE("ge"),
        /**
         * 小于
         */
        LT("lt"),
        /**
         * 小于等于
         */
        LE("le"),
        /**
         * 区间
         */
        BETWEEN("between"),
        /**
         * 右模糊匹配
         */
        LIKE_RIGHT("likeRight");

        @Getter private final String alias;

        Compare(String alias) {
            this.alias = alias;
        }
    }

}
