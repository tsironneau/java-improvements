package com.tsironneau.java17;

/**
 * A Moblin enemy record: melee-focused warrior with rank determining threat level.
 * Demonstrates record syntax and compact constructor validation for the sealed {@link EnemySealed} interface.
 *
 * <p>Threat escalates with rank: GRUNT (LOW), VETERAN (MODERATE), ELITE (HIGH).
 */
public record Moblin(String name, int health, MoblinRank rank) implements EnemySealed {

    public Moblin {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (health <= 0) throw new IllegalArgumentException("health must be positive");
        if (rank == null) throw new IllegalArgumentException("rank must not be null");
    }

    @Override
    public ThreatLevel threat() {
        return switch (rank) {
            case ELITE -> ThreatLevel.HIGH;
            case VETERAN -> ThreatLevel.MODERATE;
            case GRUNT -> ThreatLevel.LOW;
        };
    }
}
