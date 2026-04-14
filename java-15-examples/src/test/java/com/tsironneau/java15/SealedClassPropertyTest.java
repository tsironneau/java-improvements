package com.tsironneau.java15;

import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;
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
                        Arbitraries.integers().greaterOrEqual(1),
                        Arbitraries.integers().greaterOrEqual(1)
                ).as(Mario::new);

        Arbitrary<Bowser> bowsers =
                Combinators.combine(
                        Arbitraries.integers().greaterOrEqual(1),
                        Arbitraries.integers()
                ).as(Bowser::new);

        Arbitrary<ToadWithMushroom> toads =
                Arbitraries.integers().map(ToadWithMushroom::new);

        return Arbitraries.oneOf(marios, bowsers, toads);
    }

    @Property
    void mario_speed_matches_constructor_argument(@ForAll @Positive int speed, @ForAll @Positive int acceleration) {
        assertTrue(new Mario(speed, acceleration).speed() > 0);
    }

    @Property
    void bowser_acceleration_is_always_at_least_one(@ForAll int weight) {
        assertTrue(new Bowser(1, weight).acceleration() >= 1);
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
    void heavier_bowser_has_lower_or_equal_acceleration(@ForAll @Positive int weight) {
        Bowser lighter = new Bowser(5, weight);
        Bowser heavier = new Bowser(5, weight + 1);
        assertTrue(lighter.acceleration() >= heavier.acceleration());
    }
}
