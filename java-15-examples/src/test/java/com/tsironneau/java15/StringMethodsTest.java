package com.tsironneau.java15;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 15 String methods: formatted, stripIndent, translateEscapes")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringMethodsTest {

    @Test
    void formatted_interpolates_string_argument() {
        String result = "Hello %s".formatted("World");
        assertEquals("Hello World", result);
    }

    @Property
    void formatted_is_equivalent_to_String_format(
            @ForAll String name,
            @ForAll int age) {
        String template = "%s is %d years old";
        assertEquals(String.format(template, name, age), template.formatted(name, age));
    }

    @Test
    void formatted_with_multiple_arguments() {
        // Using %s instead of %.2f to avoid locale-specific decimal separator issues
        String result = "%s has %d skills".formatted("Mario", 5);
        assertEquals("Mario has 5 skills", result);
    }

    @Test
    void stripIndent_removes_common_leading_whitespace() {
        String raw = """
                    hello
                    world
                    test\
                """;
        String expected = "hello\nworld\ntest";
        assertEquals(expected, raw.stripIndent());
    }

    @Test
    void stripIndent_preserves_relative_indentation() {
        String raw = """
                    line1
                      line2 with extra indent
                    line3\
                """;
        String result = raw.stripIndent();
        // Common indent (4 spaces) is removed, but relative indent (2 extra on line2) remains
        assertTrue(result.contains("  line2"));
    }

    @Test
    void translateEscapes_converts_backslash_n_to_newline() {
        String raw = "line1\\nline2";
        String result = raw.translateEscapes();
        assertEquals("line1\nline2", result);
    }

    @Test
    void translateEscapes_converts_backslash_t_to_tab() {
        String raw = "col1\\tcol2";
        String result = raw.translateEscapes();
        assertEquals("col1\tcol2", result);
    }

    @Test
    void translateEscapes_converts_double_backslash_to_single() {
        String raw = "\\\\";
        String result = raw.translateEscapes();
        assertEquals("\\", result);
    }

    @Test
    void translateEscapes_throws_for_invalid_escape_sequence() {
        String raw = "\\q";
        assertThrows(IllegalArgumentException.class, raw::translateEscapes);
    }

    @Property
    void StringMethods_greet_is_equivalent_to_formatted(@ForAll String name, @ForAll int age) {
        String template = "Hello %s, you are %d years old";
        assertEquals(template.formatted(name, age), StringMethods.greet(template, name, age));
    }

    @Property
    void StringMethods_normalizeIndent_is_equivalent_to_stripIndent(@ForAll String raw) {
        assertEquals(raw.stripIndent(), StringMethods.normalizeIndent(raw));
    }

    @Property
    void StringMethods_processEscapes_is_equivalent_to_translateEscapes(@ForAll String raw) {
        try {
            assertEquals(raw.translateEscapes(), StringMethods.processEscapes(raw));
        } catch (IllegalArgumentException e) {
            assertThrows(IllegalArgumentException.class, () -> StringMethods.processEscapes(raw));
        }
    }
}
