package com.tsironneau.java17;

import static com.tsironneau.java17.MoblinRank.ELITE;

/**
 * Demonstrates Java 17 Pattern Matching for switch (JEP 406, preview): type patterns with
 * guards ({@code when} conditions) for fine-grained matching on sealed hierarchies.
 *
 * <p>Switch expressions over a sealed type are <em>exhaustive</em> — the compiler verifies
 * all permitted subtypes are covered, eliminating the need for a {@code default} branch.
 * Guarded patterns refine a type pattern with a boolean condition in the same {@code case} arm,
 * enabling compact multi-level dispatch without nested {@code if}-statements.
 *
 * <p>Example patterns shown:
 * <ul>
 *   <li>Simple type pattern:  {@code case Octorok o}</li>
 *   <li>Guarded pattern:      {@code case Moblin m when m.rank() == MoblinRank.ELITE}</li>
 *   <li>Fall-through guard:   {@code case Stalfos s when s.canReassemble()}</li>
 * </ul>
 *
 * @see EnemySealed the sealed hierarchy of Hyrule enemies
 */
@SuppressWarnings("unused")
public final class EnemyPatternMatch {

    private EnemyPatternMatch() {}

    /**
     * Returns a combat strategy for fighting the given enemy, chosen via type and guarded patterns.
     *
     * <p>Demonstrates JEP 406 guarded patterns: {@code case Moblin m when m.rank() == MoblinRank.ELITE}
     * narrows the type first, then applies a {@code when} guard without a nested {@code if}.
     *
     * @param enemy the enemy encountered by Link
     * @return recommended combat approach
     */
    public static String requiredStrategy(EnemySealed enemy) {
        return switch (enemy) {
            case Moblin m when m.rank() == ELITE -> "Use the Master Sword — only elite steel breaks an elite Moblin";
            case Moblin m                        -> "Block and counter — basic spear patterns are readable";
            case Octorok o when o.shotSpeed() == OctorokShotSpeed.FAST  -> "Reflect with shield — fast rocks can be sent back";
            case Octorok o                       -> "Step aside and slash — slow shots leave wide openings";
            case Stalfos s when s.canReassemble() -> "Bomb the bones after defeat or it will rise again";
            case Stalfos s                       -> "Strike fast — this Stalfos cannot reassemble";
        };
    }

    /**
     * Returns {@code true} when the enemy is an elite-tier threat demanding full readiness.
     *
     * <p>Demonstrates a boolean-returning switch expression over the sealed {@link EnemySealed}
     * interface: each {@code case} uses a type pattern, and guarded variants single out
     * dangerous sub-cases without an additional method call at the call site.
     *
     * @param enemy the enemy to evaluate
     * @return {@code true} if the enemy is an elite Moblin or a reassembling Stalfos
     */
    public static boolean isEliteThreat(EnemySealed enemy) {
        return switch (enemy) {
            case Moblin m when m.rank() == ELITE -> true;
            case Moblin m                        -> false;
            case Octorok o                       -> false;
            case Stalfos s when s.canReassemble() -> true;
            case Stalfos s                       -> false;
        };
    }
}
