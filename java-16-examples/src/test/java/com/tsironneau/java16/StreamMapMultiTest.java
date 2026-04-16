package com.tsironneau.java16;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.tsironneau.java16.Pokemon.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StreamMapMultiTest {

    @Test
    void mapMulti_expands_eevee_evolution_chain() {
        List<Pokemon> result = StreamMapMulti.expandEvolutions(List.of(EEVEE));
        assertEquals(List.of(VAPOREON, JOLTEON, FLAREON, ESPEON, UMBREON), result);
    }

    @Test
    void mapMulti_expands_charmander_evolution_chain() {
        List<Pokemon> result = StreamMapMulti.expandEvolutions(List.of(CHARMANDER));
        assertEquals(List.of(CHARMANDER, CHARMELEON, CHARIZARD), result);
    }

    @Test
    void unevolvable_pokemon_are_dropped() {
        List<Pokemon> result = StreamMapMulti.expandEvolutions(List.of(DITTO));
        assertTrue(result.isEmpty());
    }

    @Test
    void mixed_pokemon_expand_known_and_drop_unknown() {
        List<Pokemon> result = StreamMapMulti.expandEvolutions(
                List.of(EEVEE, DITTO, SQUIRTLE));
        assertEquals(
                List.of(VAPOREON, JOLTEON, FLAREON, ESPEON, UMBREON,
                        SQUIRTLE, WARTORTLE, BLASTOISE),
                result
        );
    }

    @Test
    void filter_and_count_combines_filter_and_map_in_one_step() {
        List<Integer> counts = StreamMapMulti.filterAndCountEvolutions(
                List.of(EEVEE, DITTO, CHARMANDER)
        );
        assertEquals(List.of(5, 3), counts);
    }
}
