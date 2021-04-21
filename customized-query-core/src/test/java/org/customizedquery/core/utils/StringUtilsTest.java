package org.customizedquery.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest extends Assertions {

    @Test
    public void testFirstToLowerCase() {
        assertEquals("a", StringUtils.firstToLowerCase("A"));
        assertEquals("littleA", StringUtils.firstToLowerCase("LittleA"));

        assertThrows(IllegalArgumentException.class, () -> StringUtils.firstToLowerCase("littleA"));
        assertThrows(IllegalArgumentException.class, () -> StringUtils.firstToLowerCase("\0LittleA"));
    }

    @Test
    public void testFirstToUpperCase() {
        assertEquals("A", StringUtils.firstToUpperCase("a"));
        assertEquals("LittleA", StringUtils.firstToUpperCase("littleA"));

        assertThrows(IllegalArgumentException.class, () -> StringUtils.firstToUpperCase("LittleA"));
        assertThrows(IllegalArgumentException.class, () -> StringUtils.firstToUpperCase("\0littleA"));
    }

}
