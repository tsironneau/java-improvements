package com.tsironneau.java14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("Some tests to experience Java 14 switch expression")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImprovedSwitchStatementTest {

    @ParameterizedTest
    @EnumSource(DisasterLevel.class)
    void switch_expression(DisasterLevel disasterLevel) {

        int numberOfPunches =
                switch (disasterLevel) {
                    case TIGER -> 1;
                    case DEMON -> 1;
                    case DRAGON -> 1;
                    default -> {
                        System.out.println("Always only one punch !");
                        yield 1;//yield is new in Java 13
                    }
                };
        Assertions.assertEquals(1, numberOfPunches);
    }
}
