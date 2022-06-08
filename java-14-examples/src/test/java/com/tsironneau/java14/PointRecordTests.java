package com.tsironneau.java14;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;

public class PointRecordTests {

    @Property
    void check_record_generated_getters(@ForAll() int x, @ForAll() int y) {

        final Point recordPoint = new Point(x, y);
        System.out.println("Pretty print of the record : " + recordPoint);
        Assertions.assertAll(
                () -> Assertions.assertEquals(x, recordPoint.x()),
                () -> Assertions.assertEquals(y, recordPoint.y())
        );
    }

    @Property
    void check_record_equals(@ForAll() int x, @ForAll() int y){

        final Point recordPoint = new Point(x, y);
        final Point recordPoint2 = new Point(x, y);
        System.out.println("Pretty print of the record : " + recordPoint);
        Assertions.assertEquals(recordPoint, recordPoint2);
    }

    @Property
    void check_record_hashCode(@ForAll() int x, @ForAll() int y){

        final Point recordPoint = new Point(x, y);
        final Point recordPoint2 = new Point(x, y);
        System.out.println("Pretty print of the record : " + recordPoint);
        Assertions.assertEquals(recordPoint.hashCode(), recordPoint2.hashCode());
    }

}
