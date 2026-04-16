package com.tsironneau.java16;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DayPeriodFormatterTest {

    @Test
    void morning_time_formats_as_in_the_morning() {
        assertEquals("in the morning", DayPeriodFormatter.formatFullDayPeriod(LocalTime.of(9, 0)));
    }

    @Test
    void noon_formats_as_noon() {
        assertEquals("noon", DayPeriodFormatter.formatFullDayPeriod(LocalTime.of(12, 0)));
    }

    @Test
    void afternoon_time_formats_as_in_the_afternoon() {
        assertEquals("in the afternoon", DayPeriodFormatter.formatFullDayPeriod(LocalTime.of(15, 0)));
    }

    @Test
    void evening_time_formats_as_in_the_evening() {
        assertEquals("in the evening", DayPeriodFormatter.formatFullDayPeriod(LocalTime.of(19, 0)));
    }

    @Test
    void night_time_formats_as_at_night() {
        assertEquals("at night", DayPeriodFormatter.formatFullDayPeriod(LocalTime.of(23, 0)));
    }

    @Test
    void midnight_formats_as_midnight() {
        assertEquals("midnight", DayPeriodFormatter.formatFullDayPeriod(LocalTime.MIDNIGHT));
    }

    @Test
    void am_pm_pattern_gives_AM_for_morning() {
        assertEquals("AM", DayPeriodFormatter.formatAmPm(LocalTime.of(9, 0)));
    }

    @Test
    void am_pm_pattern_gives_PM_for_afternoon() {
        assertEquals("PM", DayPeriodFormatter.formatAmPm(LocalTime.of(15, 0)));
    }

    @Test
    void day_period_varies_by_locale() {
        LocalTime morning = LocalTime.of(9, 0);
        String english = DayPeriodFormatter.formatDayPeriodForLocale(morning, Locale.ENGLISH);
        String french = DayPeriodFormatter.formatDayPeriodForLocale(morning, Locale.FRENCH);
        assertEquals("du matin", french);
        assertEquals("in the morning", english);
    }

    @Test
    void eevee_evolves_to_espeon_in_morning() {
        assertEquals("Espeon", DayPeriodFormatter.eeveeEvolution(LocalTime.of(9, 0)));
    }

    @Test
    void eevee_evolves_to_espeon_at_noon() {
        assertEquals("Espeon", DayPeriodFormatter.eeveeEvolution(LocalTime.of(12, 0)));
    }

    @Test
    void eevee_evolves_to_umbreon_in_evening() {
        assertEquals("Umbreon", DayPeriodFormatter.eeveeEvolution(LocalTime.of(19, 0)));
    }

    @Test
    void eevee_evolves_to_umbreon_at_night() {
        assertEquals("Umbreon", DayPeriodFormatter.eeveeEvolution(LocalTime.of(23, 0)));
    }

    @Test
    void eevee_evolves_to_umbreon_at_midnight() {
        assertEquals("Umbreon", DayPeriodFormatter.eeveeEvolution(LocalTime.MIDNIGHT));
    }
}
