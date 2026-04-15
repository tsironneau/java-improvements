package com.tsironneau.java16;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalRecordDemoTest {

    @Property
    void local_record_equals_is_structural(@ForAll String name, @ForAll int level) {
        assertTrue(LocalRecordDemo.checkStructuralEquality(name, level));
    }

    @Property
    void hashCode_is_consistent_with_equals(@ForAll String name, @ForAll int level) {
        assertTrue(LocalRecordDemo.checkHashCodeConsistency(name, level));
    }

    @Property
    void toString_contains_name_and_level(@ForAll String name, @ForAll int level) {
        String result = LocalRecordDemo.encounterToString(name, level);
        assertTrue(result.contains(name), "toString should contain the name");
        assertTrue(result.contains(String.valueOf(level)), "toString should contain the level");
    }

    @Test
    void encounters_are_sorted_by_level() {
        List<String> names = List.of("Pidgey", "Caterpie", "Rattata");
        List<Integer> levels = List.of(12, 3, 7);

        List<String> sorted = LocalRecordDemo.sortEncountersByLevel(names, levels);
        assertEquals(List.of("Caterpie", "Rattata", "Pidgey"), sorted);
    }

    @Test
    void sorting_empty_encounters_returns_empty_list() {
        List<String> sorted = LocalRecordDemo.sortEncountersByLevel(List.of(), List.of());
        assertTrue(sorted.isEmpty());
    }

    @Test
    void sorting_single_encounter_returns_that_pokemon() {
        List<String> sorted = LocalRecordDemo.sortEncountersByLevel(List.of("Pikachu"), List.of(25));
        assertEquals(List.of("Pikachu"), sorted);
    }
}
