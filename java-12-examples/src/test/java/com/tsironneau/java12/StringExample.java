package com.tsironneau.java12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Some tests to experience Java 12 String Methods")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StringExample {

    @Test
    void indent_add_leading_spaces_and_line_break() {

        final String toIndent = "toIndent";
        final String indented = toIndent.indent(3);
        Assertions.assertEquals("   toIndent\n", indented);
    }

    @Test
    void indent_negative_argument_removes_leading_spaces(){
        final String toIndent = "    toIndent";
        final String indented = toIndent.indent(-3);
        Assertions.assertEquals(" toIndent\n", indented);
    }

    @Test
    void transform_returns_a_modified_string_with_lambda(){
        final String toTransform = "to transform";
        final String transformed = toTransform
                .transform(s -> s.replace("to", "is"))
                .transform(s -> s.concat("ed"));

        Assertions.assertEquals("is transformed", transformed);
    }

}
