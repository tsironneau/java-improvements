package com.tsironneau.java16;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StreamToListTest {

    @Test
    void empty_stream_toList_returns_empty_unmodifiable_list() {
        List<String> result = StreamToList.toUnmodifiableList(List.of());
        assertTrue(result.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> result.add("Bulbasaur"));
    }

    @Test
    void toList_preserves_null_elements() {
        List<String> input = Arrays.asList("Pikachu", null, "Eevee");
        List<String> result = StreamToList.toUnmodifiableList(input);
        assertEquals(3, result.size());
        assertNull(result.get(1));
    }

    @Test
    void toList_result_cannot_be_mutated_via_remove() {
        List<String> pokedex = StreamToList.toUnmodifiableList(List.of("Bulbasaur", "Charmander", "Squirtle"));
        assertThrows(UnsupportedOperationException.class, () -> pokedex.remove(0));
    }

    @Test
    void collectors_toList_result_is_mutable() {
        List<String> party = StreamToList.toMutableList(List.of("Bulbasaur", "Charmander"));
        party.add("Squirtle");
        assertEquals(List.of("Bulbasaur", "Charmander", "Squirtle"), party);
    }

    @Test
    void both_methods_preserve_encounter_order() {
        List<String> pokemon = List.of("Pidgey", "Rattata", "Caterpie", "Weedle");
        assertEquals(pokemon, StreamToList.toUnmodifiableList(pokemon));
        assertEquals(pokemon, StreamToList.toMutableList(pokemon));
    }
}
