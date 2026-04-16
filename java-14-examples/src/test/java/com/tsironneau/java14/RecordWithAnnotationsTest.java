package com.tsironneau.java14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecordWithAnnotationsTest {

    @Test
    void annotation_should_be_present_on_record_components() {
        var components = RecordWithAnnotations.class.getRecordComponents();

        boolean annotationPresent = java.util.Arrays.stream(components)
                                                    .anyMatch(c -> c.isAnnotationPresent(MyTestAnnotation.class));

        assertTrue(annotationPresent,
                "Annotation should be present on record components");
    }

    @Test
    void check_propagation() throws NoSuchFieldException {
        var field = RecordWithAnnotations.class.getDeclaredField("annotatedField");
        assertTrue(field.isAnnotationPresent(MyTestAnnotation.class),
                "Annotation should propagate to private field");
    }

    @Test
    void accessor_returns_the_value_passed_at_construction() {
        var record = new RecordWithAnnotations<>("Saitama");
        assertEquals("Saitama", record.annotatedField());
    }
}