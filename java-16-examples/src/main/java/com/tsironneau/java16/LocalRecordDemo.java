package com.tsironneau.java16;

import java.util.Comparator;
import java.util.List;

/**
 * Demonstrates local records — records declared inside method bodies — finalized in Java 16.
 *
 * <p>Local records are scoped to their enclosing method and cannot be accessed outside.
 * They are implicitly static, provide automatic {@code equals}, {@code hashCode},
 * and {@code toString}, and are ideal for intermediate data transformations.</p>
 *
 * <p>Theme: Pokémon encounters — pairing a Pokémon with its level during a safari walk.</p>
 */
public final class LocalRecordDemo {

    private LocalRecordDemo() {
    }

    /**
     * Creates two encounter objects with the same Pokémon and level and checks structural equality.
     * The local record is only visible inside this method.
     *
     * @return {@code true} if two encounters with the same data are equal
     */
    public static boolean checkStructuralEquality(Pokemon pokemon, int level) {
        record PokemonEncounter(Pokemon pokemon, int level) {}

        var encounter1 = new PokemonEncounter(pokemon, level);
        var encounter2 = new PokemonEncounter(pokemon, level);
        return encounter1.equals(encounter2);
    }

    /**
     * Creates an encounter and returns its {@code toString()} representation.
     * Demonstrates that local records auto-generate a meaningful toString.
     */
    public static String encounterToString(Pokemon pokemon, int level) {
        record PokemonEncounter(Pokemon pokemon, int level) {}

        return new PokemonEncounter(pokemon, level).toString();
    }

    /**
     * Creates two encounters and returns whether they share the same hash code.
     */
    public static boolean checkHashCodeConsistency(Pokemon pokemon, int level) {
        record PokemonEncounter(Pokemon pokemon, int level) {}

        var encounter1 = new PokemonEncounter(pokemon, level);
        var encounter2 = new PokemonEncounter(pokemon, level);
        return encounter1.hashCode() == encounter2.hashCode();
    }

    /**
     * Sorts a list of Pokémon encounters by level using a local record as an intermediate
     * data carrier, then returns the sorted Pokémon.
     *
     * <p>This demonstrates local records as grouping/sorting helpers inside a method.</p>
     */
    public static List<Pokemon> sortEncountersByLevel(List<Pokemon> pokemon, List<Integer> levels) {
        record PokemonEncounter(Pokemon pokemon, int level) {}

        if (pokemon.size() != levels.size()) {
            throw new IllegalArgumentException("pokemon and levels must have the same size");
        }

        List<PokemonEncounter> encounters = new java.util.ArrayList<>();
        for (int i = 0; i < pokemon.size(); i++) {
            encounters.add(new PokemonEncounter(pokemon.get(i), levels.get(i)));
        }

        return encounters.stream()
                .sorted(Comparator.comparingInt(PokemonEncounter::level))
                .map(PokemonEncounter::pokemon)
                .toList();
    }
}
