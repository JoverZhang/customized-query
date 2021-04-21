package org.customizedquery.core.utils;

/**
 * @author Jover Zhang
 */
public class StringUtils {

    public static String firstToLowerCase(String str) {
        byte[] bytes = str.getBytes();
        if (!Character.isUpperCase(bytes[0])) {
            throw new IllegalArgumentException("The first char [" + bytes[0] + "] is not uppercase");
        }
        bytes[0] = (byte) Character.toLowerCase(bytes[0]);
        return new String(bytes);
    }

    public static String firstToUpperCase(String str) {
        byte[] bytes = str.getBytes();
        if (!Character.isLowerCase(bytes[0])) {
            throw new IllegalArgumentException("The first char [" + bytes[0] + "] is not lowercase");
        }
        bytes[0] = (byte) Character.toUpperCase(bytes[0]);
        return new String(bytes);
    }

}
