package com.tsironneau.java14;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GenericRecordTest {

    @Property
    void accessors_return_the_values_passed_at_construction(@ForAll String first, @ForAll Integer second) {
        var record = new GenericRecord<>(first, second);
        assertAll(
                () -> assertEquals(first, record.first()),
                () -> assertEquals(second, record.second())
        );
    }

    @Property
    void two_records_with_same_values_are_equal(@ForAll String first, @ForAll Integer second) {
        assertEquals(new GenericRecord<>(first, second), new GenericRecord<>(first, second));
    }

    @Property
    void two_records_with_same_values_have_the_same_hashcode(@ForAll String first, @ForAll Integer second) {
        assertEquals(
                new GenericRecord<>(first, second).hashCode(),
                new GenericRecord<>(first, second).hashCode()
        );
    }

    @Test
    void toString_contains_both_components() {
        var repr = new GenericRecord<>("Saitama", 1).toString();
        assertAll(
                () -> assertTrue(repr.contains("Saitama")),
                () -> assertTrue(repr.contains("1"))
        );
    }
}
