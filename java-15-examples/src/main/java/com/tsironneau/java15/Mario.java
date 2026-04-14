package com.tsironneau.java15;

public record Mario(int speed, int acceleration) implements Racer {
    @Override
    public String category() {
        return "balanced";
    }
}
