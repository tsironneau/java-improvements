package com.tsironneau.java15;

public sealed interface Racer permits Mario, Bowser, Toad {
    int speed();
    int acceleration();
    RacerCategory category();
}
