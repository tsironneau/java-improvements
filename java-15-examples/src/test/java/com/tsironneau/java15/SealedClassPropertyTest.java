package com.tsironneau.java15;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SealedClassPropertyTest {

    @SuppressWarnings("unchecked")
    @Provide
    Arbitrary<Racer> racers() {
        Arbitrary<Mario> marios =
                Combinators.combine(
                        Arbitraries.integers().between(1, 10),
                        Arbitraries.integers().between(1, 10)
                ).as(Mario::new);

        Arbitrary<Bowser> bowsers =
                Combinators.combine(
                        Arbitraries.integers().between(1, 10),
                        Arbitraries.integers().between(1, 10)
                ).as(Bowser::new);

        Arbitrary<ToadWithMushroom> toads =
                Arbitraries.integers().between(1, 10).map(ToadWithMushroom::new);

        return Arbitraries.oneOf(marios, bowsers, toads);
    }

    @Property
    void speed_is_always_positive_for_any_racer(@ForAll("racers") Racer racer) {
        assertTrue(racer.speed() > 0);
    }

    @Property
    void acceleration_is_always_positive_for_any_racer(@ForAll("racers") Racer racer) {
        assertTrue(racer.acceleration() > 0);
    }

    @Property
    void switch_category_matches_polymorphic_category(@ForAll("racers") Racer racer) {
        RacerCategory fromSwitch = switch (racer) {
            case Mario m -> RacerCategory.BALANCED;
            case Bowser b -> RacerCategory.HEAVY;
            case Toad t -> RacerCategory.LIGHT;
        };
        assertEquals(racer.category(), fromSwitch);
    }

    @Property
    void heavier_bowser_has_lower_or_equal_acceleration(@ForAll @IntRange(min = 1, max = 9) int weight) {
        Bowser lighter = new Bowser(5, weight);
        Bowser heavier = new Bowser(5, weight + 1);
        assertTrue(lighter.acceleration() >= heavier.acceleration());
    }
}
