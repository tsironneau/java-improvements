package com.tsironneau.java16;

import java.util.List;
import java.util.Map;

/**
 * Demonstrates {@code Stream.mapMulti()} introduced in Java 16.
 *
 * <p>{@code mapMulti} is a one-to-many flat-mapping alternative to {@code flatMap}.
 * Elements are emitted imperatively via a consumer — emitting zero times filters,
 * emitting once maps, emitting N times expands.</p>
 *
 * <p>Theme: Pokémon evolution chains. A base Pokémon can expand into its full evolution line.</p>
 */
public final class StreamMapMulti {

    private static final Map<String, List<String>> EVOLUTION_CHAINS = Map.of(
            "Eevee", List.of("Vaporeon", "Jolteon", "Flareon", "Espeon", "Umbreon"),
            "Charmander", List.of("Charmander", "Charmeleon", "Charizard"),
            "Bulbasaur", List.of("Bulbasaur", "Ivysaur", "Venusaur"),
            "Squirtle", List.of("Squirtle", "Wartortle", "Blastoise")
    );

    private StreamMapMulti() {
    }

    /**
     * Uses {@code mapMulti} to emit each element once after transforming to uppercase.
     * Equivalent to {@code stream().map(String::toUpperCase)}.
     */
    public static List<String> mapToUpperCase(List<String> source) {
        return source.stream()
                .<String>mapMulti((name, consumer) -> consumer.accept(name.toUpperCase()))
                .toList();
    }

    /**
     * Uses {@code mapMulti} that never calls the consumer — all elements are dropped.
     * Equivalent to {@code stream().filter(x -> false)}.
     */
    public static List<String> filterAll(List<String> source) {
        return source.stream()
                .<String>mapMulti((name, consumer) -> { /* emit nothing */ })
                .toList();
    }

    /**
     * Expands each Pokémon into its full evolution chain using {@code mapMulti}.
     * Unknown Pokémon (not in the evolution map) are dropped.
     */
    public static List<String> expandEvolutions(List<String> pokemon) {
        return pokemon.stream()
                .<String>mapMulti((name, consumer) -> {
                    List<String> evolutions = EVOLUTION_CHAINS.get(name);
                    if (evolutions != null) {
                        evolutions.forEach(consumer);
                    }
                })
                .toList();
    }

    /**
     * Combines filter and map in a single {@code mapMulti} step:
     * keeps only Pokémon that have evolutions and emits their evolution count.
     */
    public static List<Integer> filterAndCountEvolutions(List<String> pokemon) {
        return pokemon.stream()
                .<Integer>mapMulti((name, consumer) -> {
                    List<String> evolutions = EVOLUTION_CHAINS.get(name);
                    if (evolutions != null) {
                        consumer.accept(evolutions.size());
                    }
                })
                .toList();
    }

    static int evolutionChainSize(String pokemon) {
        List<String> chain = EVOLUTION_CHAINS.get(pokemon);
        return chain == null ? 0 : chain.size();
    }
}
