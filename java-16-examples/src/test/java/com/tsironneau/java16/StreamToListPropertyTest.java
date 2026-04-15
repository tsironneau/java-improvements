package com.tsironneau.java16;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StreamToListPropertyTest {

    @Property
    void toList_preserves_all_elements(@ForAll List<String> pokedex) {
        List<String> result = StreamToList.toUnmodifiableList(pokedex);
        assertEquals(pokedex, result);
    }

    @Property
    void toList_result_size_equals_stream_size(@ForAll List<Integer> numbers) {
        List<Integer> result = StreamToList.toUnmodifiableList(numbers);
        assertEquals(numbers.size(), result.size());
    }

    @Property
    void toList_result_is_unmodifiable(@ForAll List<String> pokedex) {
        List<String> result = StreamToList.toUnmodifiableList(pokedex);
        assertThrows(UnsupportedOperationException.class, () -> result.add("MissingNo"));
    }

    @Property
    void collectors_toList_result_is_always_mutable(@ForAll List<String> party) {
        List<String> result = StreamToList.toMutableList(party);
        assertDoesNotThrow(() -> result.add("Pikachu"));
        assertEquals(party.size() + 1, result.size());
    }
}
