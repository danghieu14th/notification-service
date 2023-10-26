package com.example.shared.utils;

public class EnumUtils {
    public static <T extends Enum<?>> T enumFromString(final String value, final T[] values) {
        if (value == null) {
            return null;
        }

        for (T v : values) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }

        return null;
    }
}
