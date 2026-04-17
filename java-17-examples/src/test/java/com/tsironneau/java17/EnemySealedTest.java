package com.tsironneau.java17;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.tsironneau.java17.MoblinRank.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnemySealedTest {

    @Test
    void elite_moblin_rank_elite_is_high_threat() {
        Moblin moblin = new Moblin("Elite Moblin", 120, ELITE);
        assertAll(
                () -> assertEquals("Elite Moblin", moblin.name()),
                () -> assertEquals(120, moblin.health()),
                () -> assertEquals(ELITE, moblin.rank()),
                () -> assertEquals(ThreatLevel.HIGH, moblin.threat())
        );
    }

    @Test
    void blue_octorok_with_fast_shots_is_high_threat() {
        Octorok octorok = new Octorok("Blue Octorok", 30, OctorokShotSpeed.FAST);
        assertEquals(ThreatLevel.HIGH, octorok.threat());
    }

    @Test
    void blue_octorok_with_slow_shots_is_low_threat() {
        Octorok octorok = new Octorok("Blue Octorok", 30, OctorokShotSpeed.SLOW);
        assertEquals(ThreatLevel.LOW, octorok.threat());
    }

    @Test
    void reassembling_stalfos_is_high_threat() {
        Stalfos stalfos = new Stalfos("Stalfos Knight", 80, true);
        assertEquals(ThreatLevel.HIGH, stalfos.threat());
    }

    @Test
    void stalfos_without_reassembly_is_moderate_threat() {
        Stalfos stalfos = new Stalfos("Stalfos", 50, false);
        assertEquals(ThreatLevel.MODERATE, stalfos.threat());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void switch_over_sealed_enemy_needs_no_default_branch() {
        EnemySealed enemy = new Moblin("Red Moblin", 60, GRUNT);
        String description = switch (enemy) {
            case Moblin m -> "moblin rank " + m.rank();
            case Octorok o -> "octorok speed " + o.shotSpeed();
            case Stalfos s -> "stalfos reassemble=" + s.canReassemble();
        };
        assertEquals("moblin rank GRUNT", description);
    }

    @Test
    void moblin_record_equality_and_tostring() {
        Moblin m1 = new Moblin("Moblin", 50, VETERAN);
        Moblin m2 = new Moblin("Moblin", 50, VETERAN);
        assertAll(
                () -> assertEquals(m1, m2),
                () -> assertEquals(m1.hashCode(), m2.hashCode()),
                () -> assertTrue(m1.toString().contains("Moblin"))
        );
    }

    @Test
    void non_positive_health_throws_exception() {
        assertThrows(IllegalArgumentException.class, () -> new Moblin("Moblin", 0, GRUNT));
        assertThrows(IllegalArgumentException.class, () -> new Octorok("Octorok", -5, OctorokShotSpeed.SLOW));
        assertThrows(IllegalArgumentException.class, () -> new Stalfos("Stalfos", 0, false));
    }

    @Test
    void blank_name_throws_exception() {
        assertThrows(IllegalArgumentException.class, () -> new Moblin("", 50, GRUNT));
        assertThrows(IllegalArgumentException.class, () -> new Octorok("  ", 30, OctorokShotSpeed.MODERATE));
    }

    @Test
    void moblin_null_rank_throws_exception() {
        assertThrows(IllegalArgumentException.class, () -> new Moblin("Moblin", 50, null));
    }
}
