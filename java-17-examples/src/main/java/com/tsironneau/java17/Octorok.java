package com.tsironneau.java17;

/**
 * An Octorok enemy record: ranged attacker whose threat depends on projectile velocity.
 * Demonstrates record syntax and compact constructor validation for the sealed {@link EnemySealed} interface.
 *
 * <p>Threat escalates with shot speed: SLOW (LOW), MODERATE (MODERATE), FAST (HIGH).
 */
public record Octorok(String name, int health, OctorokShotSpeed shotSpeed) implements EnemySealed {

    public Octorok {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (health <= 0) throw new IllegalArgumentException("health must be positive");
        if (shotSpeed == null) throw new IllegalArgumentException("shotSpeed must not be null");
    }

    @Override
    public ThreatLevel threat() {
        return switch (shotSpeed) {
            case FAST -> ThreatLevel.HIGH;
            case MODERATE -> ThreatLevel.MODERATE;
            case SLOW -> ThreatLevel.LOW;
        };
    }
}
