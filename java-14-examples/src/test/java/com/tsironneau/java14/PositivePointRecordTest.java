package com.tsironneau.java14;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Assertions;

public class PositivePointRecordTest {

    @Property
    void check_x_coordinates_cannot_be_negative(@ForAll() @IntRange(min = Integer.MIN_VALUE, max = -1) int x, @ForAll() int y) {

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new PositivePoint(x, y)
        );
    }

    @Property
    void check_y_coordinates_cannot_be_negative(@ForAll() int x, @ForAll() @IntRange(min = Integer.MIN_VALUE, max = -1) int y) {

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new PositivePoint(x, y)
        );
    }

}
