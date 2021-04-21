package org.customizedquery.core.utils;

import org.springframework.util.Assert;

/**
 * @author Jover Zhang
 */
public class Asserts {

    public static void notNull(Object object) {
        Assert.notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void isTrue(boolean expression) {
        Assert.isTrue(expression, "[Assertion failed] - this expression must be true");
    }

}
