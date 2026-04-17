package com.tsironneau.java17;

import net.jqwik.api.*;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import static com.tsironneau.java17.MoblinRank.*;
import static com.tsironneau.java17.OctorokShotSpeed.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnemyPatternMatchPropertyTest {

    @Property
    void every_enemy_has_a_required_strategy(@ForAll("anyEnemy") EnemySealed enemy) {
        String strategy = EnemyPatternMatch.requiredStrategy(enemy);
        assertNotNull(strategy);
        assertFalse(strategy.isBlank());
    }

    @Property
    void elite_threat_always_matches_high_threat_level(@ForAll("anyEnemy") EnemySealed enemy) {
        Assume.that(EnemyPatternMatch.isEliteThreat(enemy));
        assertEquals(ThreatLevel.HIGH, enemy.threat());
    }

    @Property
    void non_elite_enemies_never_have_elite_strategy(@ForAll("anyEnemy") EnemySealed enemy) {
        Assume.that(!EnemyPatternMatch.isEliteThreat(enemy));
        String strategy = EnemyPatternMatch.requiredStrategy(enemy);
        assertFalse(strategy.contains("Master Sword") || strategy.contains("Bomb the bones"));
    }

    @Property
    void elite_moblin_is_always_elite(@ForAll("anyMoblin") Moblin moblin) {
        Assume.that(moblin.rank() == ELITE);
        assertTrue(EnemyPatternMatch.isEliteThreat(moblin));
    }

    @Property
    void non_elite_moblin_is_never_elite(@ForAll("anyMoblin") Moblin moblin) {
        Assume.that(moblin.rank() != ELITE);
        assertFalse(EnemyPatternMatch.isEliteThreat(moblin));
    }

    @Provide
    Arbitrary<EnemySealed> anyEnemy() {
        return Arbitraries.oneOf(anyMoblin(), anyOctorok(), anyStalfos());
    }

    @Property
    void octorok_is_never_elite(@ForAll("anyOctorok") Octorok octorok) {
        assertFalse(EnemyPatternMatch.isEliteThreat(octorok));
    }

    @Property
    void reassembling_stalfos_is_always_elite(@ForAll("anyStalfos") Stalfos stalfos) {
        Assume.that(stalfos.canReassemble());
        assertTrue(EnemyPatternMatch.isEliteThreat(stalfos));
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
        Arbitrary<OctorokShotSpeed> shotSpeed = Arbitraries.of(SLOW, MODERATE, FAST);
        return Combinators.combine(name, health, shotSpeed).as(Octorok::new);
    }

    @Provide
    Arbitrary<Stalfos> anyStalfos() {
        Arbitrary<String> name = Arbitraries.strings().alpha().ofMinLength(1);
        Arbitrary<Integer> health = Arbitraries.integers().greaterOrEqual(1);
        Arbitrary<Boolean> canReassemble = Arbitraries.of(true, false);
        return Combinators.combine(name, health, canReassemble).as(Stalfos::new);
    }
}
