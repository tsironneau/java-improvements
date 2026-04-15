package com.tsironneau.java16;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StreamMapMultiTest {

    @Test
    void mapMulti_expands_eevee_evolution_chain() {
        List<String> result = StreamMapMulti.expandEvolutions(List.of("Eevee"));
        assertEquals(List.of("Vaporeon", "Jolteon", "Flareon", "Espeon", "Umbreon"), result);
    }

    @Test
    void mapMulti_expands_charmander_evolution_chain() {
        List<String> result = StreamMapMulti.expandEvolutions(List.of("Charmander"));
        assertEquals(List.of("Charmander", "Charmeleon", "Charizard"), result);
    }

    @Test
    void unevolvable_pokemon_are_dropped() {
        List<String> result = StreamMapMulti.expandEvolutions(List.of("Ditto"));
        assertTrue(result.isEmpty());
    }

    @Test
    void mixed_pokemon_expand_known_and_drop_unknown() {
        List<String> result = StreamMapMulti.expandEvolutions(List.of("Eevee", "Ditto", "Squirtle"));
        assertEquals(
                List.of("Vaporeon", "Jolteon", "Flareon", "Espeon", "Umbreon",
                        "Squirtle", "Wartortle", "Blastoise"),
                result
        );
    }

    @Test
    void filter_and_count_combines_filter_and_map_in_one_step() {
        List<Integer> counts = StreamMapMulti.filterAndCountEvolutions(
                List.of("Eevee", "Ditto", "Charmander")
        );
        assertEquals(List.of(5, 3), counts);
    }
}
