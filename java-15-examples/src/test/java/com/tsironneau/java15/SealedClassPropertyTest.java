package com.tsironneau.java15;

import net.jqwik.api.Arbitrary;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Combinators;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SealedClassPropertyTest {

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
    void switch_over_racer_returns_correct_category(@ForAll("racers") Racer racer) {
        RacerCategory category = switch (racer) {
            case Mario m -> RacerCategory.BALANCED;
            case Bowser b -> RacerCategory.HEAVY;
            case Toad t -> RacerCategory.LIGHT;
        };
        assertNotNull(category);
    }

    @Property
    void bowser_acceleration_is_always_positive(@ForAll("heavyBowsers") Bowser bowser) {
        assertTrue(bowser.acceleration() > 0);
    }

    @Provide
    Arbitrary<Bowser> heavyBowsers() {
        return Combinators.combine(
            Arbitraries.integers().between(3, 10),
            Arbitraries.integers().between(2, 10)
        ).as(Bowser::new);
    }
}
