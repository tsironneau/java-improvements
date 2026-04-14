package com.tsironneau.java15;

public final class ToadWithMushroom extends Toad {
    private final int baseSpeed;

    public ToadWithMushroom(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    @Override
    public int speed() {
        return baseSpeed + 2;
    }

    @Override
    public int acceleration() {
        return 5;
    }
}
