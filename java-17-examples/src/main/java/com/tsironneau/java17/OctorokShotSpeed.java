package com.tsironneau.java17;

/**
 * Shot speed classification for Octoroks: categorizes the danger based on projectile velocity.
 * Replaces magic integer thresholds (2, 5) with type-safe enum values.
 */
public enum OctorokShotSpeed {
    SLOW, MODERATE, FAST
}
