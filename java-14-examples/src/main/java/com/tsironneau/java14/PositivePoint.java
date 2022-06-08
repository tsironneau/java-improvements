package com.tsironneau.java14;

public record PositivePoint(int x, int y) {

    public PositivePoint {

        if (x < 0) {
            throw new IllegalArgumentException("x should be >= 0, x = " + x);
        }
        if (y < 0) {
            throw new IllegalArgumentException("y should be >= 0, y = " + y);
        }
    }
}
