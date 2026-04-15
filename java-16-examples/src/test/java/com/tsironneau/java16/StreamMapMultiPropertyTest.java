package com.tsironneau.java16;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StreamMapMultiPropertyTest {

    @Property
    void emitting_once_is_equivalent_to_map(@ForAll List<String> names) {
        List<String> viaMapMulti = StreamMapMulti.mapToUpperCase(names);
        List<String> viaMap = names.stream().map(String::toUpperCase).toList();
        assertEquals(viaMap, viaMapMulti);
    }

    @Property
    void emitting_zero_is_equivalent_to_filter_false(@ForAll List<String> names) {
        List<String> result = StreamMapMulti.filterAll(names);
        assertTrue(result.isEmpty());
    }

    @Property
    void total_output_size_equals_sum_of_emissions(@ForAll List<String> pokemon) {
        List<String> expanded = StreamMapMulti.expandEvolutions(pokemon);
        int expectedSize = pokemon.stream()
                .mapToInt(StreamMapMulti::evolutionChainSize)
                .sum();
        assertEquals(expectedSize, expanded.size());
    }
}
