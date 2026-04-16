package com.tsironneau.java16;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.tsironneau.java16.Pokemon.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalRecordDemoTest {

    @Property
    void local_record_equals_is_structural(@ForAll Pokemon pokemon, @ForAll int level) {
        assertTrue(LocalRecordDemo.checkStructuralEquality(pokemon, level));
    }

    @Property
    void hashCode_is_consistent_with_equals(@ForAll Pokemon pokemon, @ForAll int level) {
        assertTrue(LocalRecordDemo.checkHashCodeConsistency(pokemon, level));
    }

    @Property
    void toString_contains_pokemon_and_level(@ForAll Pokemon pokemon, @ForAll int level) {
        String result = LocalRecordDemo.encounterToString(pokemon, level);
        assertTrue(result.contains(pokemon.name()), "toString should contain the pokemon name");
        assertTrue(result.contains(String.valueOf(level)), "toString should contain the level");
    }

    @Test
    void encounters_are_sorted_by_level() {
        List<Pokemon> pokemon = List.of(PIDGEY, CATERPIE, RATTATA);
        List<Integer> levels = List.of(12, 3, 7);

        List<Pokemon> sorted = LocalRecordDemo.sortEncountersByLevel(pokemon, levels);
        assertEquals(List.of(CATERPIE, RATTATA, PIDGEY), sorted);
    }

    @Test
    void sorting_empty_encounters_returns_empty_list() {
        List<Pokemon> sorted = LocalRecordDemo.sortEncountersByLevel(List.of(), List.of());
        assertTrue(sorted.isEmpty());
    }

    @Test
    void sorting_single_encounter_returns_that_pokemon() {
        List<Pokemon> sorted = LocalRecordDemo.sortEncountersByLevel(
                List.of(PIKACHU), List.of(25));
        assertEquals(List.of(PIKACHU), sorted);
    }
}
