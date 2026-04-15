package com.tsironneau.java16;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DayPeriodFormatterPropertyTest {

    @Provide
    Arbitrary<LocalTime> anyLocalTime() {
        return Arbitraries.integers().between(0, 86399)
                .map(LocalTime::ofSecondOfDay);
    }

    @Property
    void format_never_returns_null_or_blank(@ForAll("anyLocalTime") LocalTime time) {
        String result = DayPeriodFormatter.formatDayPeriod(time);
        assertNotNull(result);
        assertFalse(result.isBlank(), "Day period should not be blank for " + time);
    }

    @Property
    void B_pattern_does_not_produce_AM_PM(@ForAll("anyLocalTime") LocalTime time) {
        String result = DayPeriodFormatter.formatFullDayPeriod(time);
        assertFalse(result.equalsIgnoreCase("AM"), "Day period should not be AM");
        assertFalse(result.equalsIgnoreCase("PM"), "Day period should not be PM");
    }

    @Property
    void a_pattern_only_produces_AM_or_PM(@ForAll("anyLocalTime") LocalTime time) {
        String result = DayPeriodFormatter.formatAmPm(time);
        assertTrue(result.equals("AM") || result.equals("PM"),
                "AM/PM pattern should only produce AM or PM, got: " + result);
    }

    @Property
    void eevee_always_evolves_into_espeon_or_umbreon(@ForAll("anyLocalTime") LocalTime time) {
        String evolution = DayPeriodFormatter.eeveeEvolution(time);
        assertTrue(evolution.equals("Espeon") || evolution.equals("Umbreon"),
                "Eevee should evolve into Espeon or Umbreon, got: " + evolution);
    }
}
