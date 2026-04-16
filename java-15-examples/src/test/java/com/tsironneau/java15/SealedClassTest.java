package com.tsironneau.java15;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sealed classes and pattern matching with Mario Kart racers")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SealedClassTest {

    @SuppressWarnings({"DataFlowIssue", "unused"})
    @Test
    void switch_over_sealed_hierarchy_is_exhaustive() {
        Racer racer = new Mario(4, 4);
        String kind = switch (racer) {
            case Mario m -> "mario";
            case Bowser b -> "bowser";
            case Toad t -> "toad";
            // no default needed - compiler knows all permits
        };
        assertEquals("mario", kind);
    }

    @SuppressWarnings("ConstantValue")
    @Test
    void non_sealed_toad_can_be_extended() {
        Racer toadRacer = new ToadWithMushroom(3);

        assertTrue(toadRacer instanceof ToadWithMushroom);
        assertTrue(toadRacer instanceof Toad);
        assertTrue(toadRacer instanceof Racer);
    }

    @Test
    void toad_with_mushroom_has_boosted_speed() {
        ToadWithMushroom toad = new ToadWithMushroom(3);
        assertEquals(5, toad.speed()); // 3 + 2 boost
        assertEquals(RacerCategory.LIGHT, toad.category());
    }

    @Test
    void toad_with_mushroom_acceleration_is_five() {
        assertEquals(5, new ToadWithMushroom(1).acceleration());
    }

    @Test
    void mario_record_equals_hashcode_tostring() {
        Mario m1 = new Mario(4, 4);
        Mario m2 = new Mario(4, 4);
        assertAll(
                () -> assertEquals(m1, m2),
                () -> assertEquals(m1.hashCode(), m2.hashCode()),
                () -> assertTrue(m1.toString().contains("4")),
                () -> assertEquals(4, m1.acceleration())
        );
    }

    @Test
    void bowser_record_weight_accessor_equals_hashcode_tostring() {
        Bowser b1 = new Bowser(3, 5);
        Bowser b2 = new Bowser(3, 5);
        assertAll(
                () -> assertEquals(5, b1.weight()),
                () -> assertEquals(3, b1.speed()),
                () -> assertEquals(b1, b2),
                () -> assertEquals(b1.hashCode(), b2.hashCode()),
                () -> assertTrue(b1.toString().contains("5"))
        );
    }
}
