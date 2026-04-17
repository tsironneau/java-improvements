package com.tsironneau.java17;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.tsironneau.java17.MoblinRank.*;
import static com.tsironneau.java17.OctorokShotSpeed.FAST;
import static com.tsironneau.java17.OctorokShotSpeed.SLOW;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnemyPatternMatchTest {

    // --- requiredStrategy: type patterns ---

    @Test
    void red_octorok_with_slow_shot_gets_step_aside_strategy() {
        Octorok redOctorok = new Octorok("Red Octorok", 2, SLOW);
        String strategy = EnemyPatternMatch.requiredStrategy(redOctorok);
        assertTrue(strategy.contains("Step aside"));
    }

    @Test
    void rock_octorok_with_fast_shot_gets_reflect_strategy() {
        Octorok rockOctorok = new Octorok("Rock Octorok", 4, FAST);
        String strategy = EnemyPatternMatch.requiredStrategy(rockOctorok);
        assertTrue(strategy.contains("Reflect"));
    }

    @Test
    void blue_moblin_grunt_rank_gets_block_and_counter_strategy() {
        Moblin blueMoblin = new Moblin("Blue Moblin", 5, GRUNT);
        String strategy = EnemyPatternMatch.requiredStrategy(blueMoblin);
        assertTrue(strategy.contains("Block and counter"));
    }

    @Test
    void elite_moblin_gets_master_sword_strategy() {
        Moblin eliteMoblin = new Moblin("Gold Moblin", 12, ELITE);
        String strategy = EnemyPatternMatch.requiredStrategy(eliteMoblin);
        assertTrue(strategy.contains("Master Sword"));
    }

    @Test
    void stalfos_knight_that_can_reassemble_gets_bomb_strategy() {
        Stalfos stalfosKnight = new Stalfos("Stalfos Knight", 8, true);
        String strategy = EnemyPatternMatch.requiredStrategy(stalfosKnight);
        assertTrue(strategy.contains("Bomb"));
    }

    @Test
    void broken_stalfos_that_cannot_reassemble_gets_strike_fast_strategy() {
        Stalfos weakStalfos = new Stalfos("Stalfos", 4, false);
        String strategy = EnemyPatternMatch.requiredStrategy(weakStalfos);
        assertTrue(strategy.contains("Strike fast"));
    }

    @Test
    void elite_moblin_is_elite_threat() {
        assertTrue(EnemyPatternMatch.isEliteThreat(new Moblin("Gold Moblin", 12, ELITE)));
    }

    @Test
    void veteran_moblin_is_not_elite_threat() {
        assertFalse(EnemyPatternMatch.isEliteThreat(new Moblin("Red Moblin", 7, VETERAN)));
    }

    @Test
    void grunt_moblin_is_not_elite_threat() {
        assertFalse(EnemyPatternMatch.isEliteThreat(new Moblin("Blue Moblin", 4, GRUNT)));
    }

    @Test
    void any_octorok_is_not_elite() {
        assertFalse(EnemyPatternMatch.isEliteThreat(new Octorok("Red Octorok", 2, SLOW)));
    }

    @Test
    void reassembling_stalfos_knight_is_elite() {
        assertTrue(EnemyPatternMatch.isEliteThreat(new Stalfos("Stalfos Knight", 10, true)));
    }

    @Test
    void non_reassembling_stalfos_is_not_elite() {
        assertFalse(EnemyPatternMatch.isEliteThreat(new Stalfos("Stalfos", 4, false)));
    }

    @Test
    void switch_covers_all_enemy_subtypes_without_default() {
        EnemySealed[] enemies = {
            new Moblin("Blue Moblin", 4, GRUNT),
            new Octorok("Red Octorok", 2, SLOW),
            new Stalfos("Stalfos Knight", 8, true)
        };
        for (EnemySealed enemy : enemies) {
            assertDoesNotThrow(() -> EnemyPatternMatch.requiredStrategy(enemy));
        }
    }
}
