package com.tsironneau.java14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RecordWithAnnotationsTest {

    @Test
    void throw_exception_when_null_parameter_given_to_constructor() {
        //noinspection ConstantConditions
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new RecordWithAnnotations<>(null)
        );
    }
}