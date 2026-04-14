package com.tsironneau.java15;

public final class ToadWithMushroom extends Toad {
    private static final int MUSHROOM_BOOST = 2;
    private final int baseSpeed;

    public ToadWithMushroom(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    @Override
    public int speed() {
        return baseSpeed + MUSHROOM_BOOST;
    }

    @Override
    public int acceleration() {
        return 5;
    }
}
