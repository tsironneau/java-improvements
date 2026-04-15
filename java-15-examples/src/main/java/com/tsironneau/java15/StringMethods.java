package com.tsironneau.java15;

public class StringMethods {
    public static String greet(String template, Object... args) {
        return template.formatted(args);
    }

    public static String normalizeIndent(String raw) {
        return raw.stripIndent();
    }

    public static String processEscapes(String raw) {
        return raw.translateEscapes();
    }
}
