package com.tsironneau.java15;

public record Bowser(int speed, int weight) implements Racer {
    private static final int MAX_STAT = 6;

    @Override
    public int acceleration() {
        return Math.max(1, MAX_STAT - weight);
    }

    @Override
    public RacerCategory category() {
        return RacerCategory.HEAVY;
    }
}
