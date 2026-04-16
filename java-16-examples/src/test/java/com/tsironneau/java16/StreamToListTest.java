package com.tsironneau.java16;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.tsironneau.java16.Pokemon.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StreamToListTest {

    @Test
    void empty_stream_toList_returns_empty_unmodifiable_list() {
        List<Pokemon> result = StreamToList.toUnmodifiableList(List.of());
        assertTrue(result.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> result.add(BULBASAUR));
    }

    @Test
    void toList_preserves_null_elements() {
        List<Pokemon> input = Arrays.asList(PIKACHU, null, EEVEE);
        List<Pokemon> result = StreamToList.toUnmodifiableList(input);
        assertEquals(3, result.size());
        assertNull(result.get(1));
    }

    @Test
    void toList_result_cannot_be_mutated_via_remove() {
        List<Pokemon> pokedex = StreamToList.toUnmodifiableList(
                List.of(BULBASAUR, CHARMANDER, SQUIRTLE));
        assertThrows(UnsupportedOperationException.class, () -> pokedex.remove(0));
    }

    @Test
    void collectors_toList_result_is_mutable() {
        List<Pokemon> party = StreamToList.toMutableList(List.of(BULBASAUR, CHARMANDER));
        party.add(SQUIRTLE);
        assertEquals(List.of(BULBASAUR, CHARMANDER, SQUIRTLE), party);
    }

    @Test
    void both_methods_preserve_encounter_order() {
        List<Pokemon> pokemon = List.of(PIDGEY, RATTATA, CATERPIE, WEEDLE);
        assertEquals(pokemon, StreamToList.toUnmodifiableList(pokemon));
        assertEquals(pokemon, StreamToList.toMutableList(pokemon));
    }
}
