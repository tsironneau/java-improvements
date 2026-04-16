package com.tsironneau.java16;

/**
 * Pokémon referenced across the java-16 module examples.
 *
 * <p>Centralizes all Pokémon names as enum constants to avoid hardcoded strings
 * and provide type safety throughout the module.</p>
 */
public enum Pokemon {

    // Kanto starters and evolutions
    BULBASAUR("Bulbasaur"), IVYSAUR("Ivysaur"), VENUSAUR("Venusaur"),
    CHARMANDER("Charmander"), CHARMELEON("Charmeleon"), CHARIZARD("Charizard"),
    SQUIRTLE("Squirtle"), WARTORTLE("Wartortle"), BLASTOISE("Blastoise"),

    // Eevee family
    EEVEE("Eevee"),
    VAPOREON("Vaporeon"), JOLTEON("Jolteon"), FLAREON("Flareon"),
    ESPEON("Espeon"), UMBREON("Umbreon"),

    // Common encounters
    PIKACHU("Pikachu"), PIDGEY("Pidgey"), RATTATA("Rattata"),
    CATERPIE("Caterpie"), WEEDLE("Weedle"),

    // Others
    DITTO("Ditto"), MISSING_NO("MissingNo");

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String displayName;

    Pokemon(String displayName) {
        this.displayName = displayName;
    }

}
