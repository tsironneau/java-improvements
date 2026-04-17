package com.tsironneau.java17;

import net.jqwik.api.*;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import static com.tsironneau.java17.MoblinRank.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnemySealedPropertyTest {

    @Provide
    Arbitrary<EnemySealed> enemies() {
        return Arbitraries.oneOf(anyMoblin(), anyOctorok(), anyStalfos());
    }

    @Provide
    Arbitrary<Moblin> anyMoblin() {
        Arbitrary<String> name = Arbitraries.strings().alpha().ofMinLength(1);
        Arbitrary<Integer> health = Arbitraries.integers().greaterOrEqual(1);
        Arbitrary<MoblinRank> rank = Arbitraries.of(GRUNT, VETERAN, ELITE);
        return Combinators.combine(name, health, rank).as(Moblin::new);
    }

    @Provide
    Arbitrary<Octorok> anyOctorok() {
        Arbitrary<String> name = Arbitraries.strings().alpha().ofMinLength(1);
        Arbitrary<Integer> health = Arbitraries.integers().greaterOrEqual(1);
        Arbitrary<OctorokShotSpeed> shotSpeed = Arbitraries.of(OctorokShotSpeed.SLOW, OctorokShotSpeed.MODERATE, OctorokShotSpeed.FAST);
        return Combinators.combine(name, health, shotSpeed).as(Octorok::new);
    }

    @Provide
    Arbitrary<Stalfos> anyStalfos() {
        Arbitrary<String> name = Arbitraries.strings().alpha().ofMinLength(1);
        Arbitrary<Integer> health = Arbitraries.integers().greaterOrEqual(1);
        Arbitrary<Boolean> canReassemble = Arbitraries.of(true, false);
        return Combinators.combine(name, health, canReassemble).as(Stalfos::new);
    }

    @Property
    void every_enemy_has_positive_health(@ForAll("enemies") EnemySealed enemy) {
        assertTrue(enemy.health() > 0);
    }

    @Property
    void every_enemy_has_non_blank_name(@ForAll("enemies") EnemySealed enemy) {
        assertFalse(enemy.name().isBlank());
    }

    @Property
    void every_enemy_has_a_threat_level(@ForAll("enemies") EnemySealed enemy) {
        assertNotNull(enemy.threat());
    }

    @Property
    void switch_over_sealed_hierarchy_covers_all_types(@ForAll("enemies") EnemySealed enemy) {
        ThreatLevel fromSwitch = switch (enemy) {
            case Moblin m -> m.threat();
            case Octorok o -> o.threat();
            case Stalfos s -> s.threat();
        };
        assertEquals(enemy.threat(), fromSwitch);
    }

    @Property
    void moblin_elite_rank_is_always_high_threat(@ForAll @NotBlank String name, @ForAll @Positive int health) {
        assertEquals(ThreatLevel.HIGH, new Moblin(name, health, ELITE).threat());
    }

    @Property
    void stalfos_that_can_reassemble_is_always_high_threat(@ForAll @NotBlank String name, @ForAll @Positive int health) {
        assertEquals(ThreatLevel.HIGH, new Stalfos(name, health, true).threat());
    }
}
