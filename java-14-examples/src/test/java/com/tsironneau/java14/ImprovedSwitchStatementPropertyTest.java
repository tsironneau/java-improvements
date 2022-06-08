package com.tsironneau.java14;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;

class ImprovedSwitchStatementPropertyTest {

    @Property
    void switch_expression(@ForAll DisasterLevel disasterLevel) {

        int numberOfPunches =
                switch (disasterLevel) {
                    case TIGER -> 1;
                    case DEMON -> 1;
                    case DRAGON -> 1;
                    default -> {
                        System.out.println("Always only one punch !");
                        yield 1;
                    }
                };
        Assertions.assertEquals(1, numberOfPunches);
    }
}
