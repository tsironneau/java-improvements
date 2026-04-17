package com.tsironneau.java17;

/**
 * Demonstrates Java 17 Sealed Classes (JEP 409, finalized): restricts the set of permitted
 * subclasses, enabling type-safe exhaustive pattern matching in switch expressions without
 * a default branch. The compiler verifies that all permitted subtypes are handled.
 *
 * <p>Permitted implementations: {@link Moblin}, {@link Octorok}, {@link Stalfos}.
 * Each provides its own {@link ThreatLevel} classification via compact constructor validation.
 */
public sealed interface EnemySealed permits Moblin, Octorok, Stalfos {

    String name();

    int health();

    ThreatLevel threat();
}
