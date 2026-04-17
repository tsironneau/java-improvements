package com.tsironneau.java17;

/**
 * A Stalfos enemy record: skeletal warrior whose threat depends on reassembly capability.
 * Demonstrates record syntax and compact constructor validation for the sealed {@link EnemySealed} interface.
 *
 * <p>Threat escalates with reassembly: non-reassembling (MODERATE), reassembling (HIGH).
 */
public record Stalfos(String name, int health, boolean canReassemble) implements EnemySealed {

    public Stalfos {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (health <= 0) throw new IllegalArgumentException("health must be positive");
    }

    @Override
    public ThreatLevel threat() {
        return canReassemble ? ThreatLevel.HIGH : ThreatLevel.MODERATE;
    }
}
