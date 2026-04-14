package com.tsironneau.java15;

public record Bowser(int speed, int weight) implements Racer {
    @Override
    public int acceleration() {
        return Math.max(1, 6 - weight);
    }

    @Override
    public String category() {
        return "heavy";
    }
}
