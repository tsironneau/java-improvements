package com.tsironneau.java15;

public non-sealed abstract class Toad implements Racer {
    @Override
    public RacerCategory category() {
        return RacerCategory.LIGHT;
    }
}
