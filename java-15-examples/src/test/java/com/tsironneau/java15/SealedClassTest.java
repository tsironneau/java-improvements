package com.tsironneau.java15;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sealed classes and pattern matching with Mario Kart racers")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SealedClassTest {

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

    @Test
    void mario_exposes_record_components() {
        Racer mario = new Mario(4, 4);

        if (mario instanceof Mario m) {
            assertEquals(4, m.speed());
            assertEquals(4, m.acceleration());
        } else {
            fail("Mario should be an instance of Mario record");
        }
    }

    @Test
    void bowser_exposes_record_components() {
        Racer bowser = new Bowser(5, 5);

        if (bowser instanceof Bowser b) {
            assertEquals(5, b.speed());
            assertEquals(5, b.weight());
        } else {
            fail("Bowser should be an instance of Bowser record");
        }
    }

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
    void bowser_acceleration_decreases_with_weight() {
        Bowser bowser1 = new Bowser(5, 1);
        Bowser bowser2 = new Bowser(5, 5);
        Bowser bowser3 = new Bowser(5, 6);

        assertEquals(5, bowser1.acceleration()); // max(1, 6-1) = 5
        assertEquals(1, bowser2.acceleration()); // max(1, 6-5) = 1
        assertEquals(1, bowser3.acceleration()); // max(1, 6-6) = 1
    }
}
