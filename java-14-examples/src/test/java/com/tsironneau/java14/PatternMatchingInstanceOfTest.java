package com.tsironneau.java14;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeTry;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PatternMatchingInstanceOfTest {

    private PatternMatchingInstanceOf testedObject;

    @BeforeTry
    @BeforeEach
    void setup(){
        testedObject = new PatternMatchingInstanceOf();
    }

    @Property
    void before_and_after_impl_returns_the_same_string(@ForAll() String aString){
        final Optional<String> beforeJava_14 = testedObject.beforeJava_14(aString);
        final Optional<String> withJava_14 = testedObject.withJava_14(aString);

        Assertions.assertAll(
                () -> assertTrue(beforeJava_14.isPresent()),
                () -> assertTrue(withJava_14.isPresent())
        );

        assertEquals(withJava_14.get(), beforeJava_14.get());
    }

    @Property
    void returns_empty_when_object_is_not_a_string(@ForAll Integer notAString) {
        Assertions.assertAll(
                () -> assertEquals(Optional.empty(), testedObject.beforeJava_14(notAString)),
                () -> assertEquals(Optional.empty(), testedObject.withJava_14(notAString))
        );
    }

    @Test
    void scope_returns_pattern_variable_when_object_is_a_string() {
        assertEquals("one punch", testedObject.scopeOfThePatternVariable("one punch"));
    }

    @Test
    void scope_returns_field_when_object_is_not_a_string() {
        assertEquals(testedObject.s, testedObject.scopeOfThePatternVariable(42));
    }

}