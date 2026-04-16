package com.tsironneau.java16;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static com.tsironneau.java16.Pokemon.*;

/**
 * Demonstrates the Day Period support added in Java 16 via {@code DateTimeFormatter}.
 *
 * <p>The {@code "B"} pattern symbol formats a time into natural-language day periods
 * (morning, afternoon, evening, night) instead of the binary AM/PM.</p>
 *
 * <p>Theme: Eevee evolves differently depending on the time of day —
 * Espeon in the morning/afternoon, Umbreon in the evening/at night.</p>
 */
public final class DayPeriodFormatter {

    private static final DateTimeFormatter DAY_PERIOD_SHORT =
            DateTimeFormatter.ofPattern("B", Locale.ENGLISH);

    private static final DateTimeFormatter DAY_PERIOD_FULL =
            DateTimeFormatter.ofPattern("BBBB", Locale.ENGLISH);

    private static final DateTimeFormatter AM_PM =
            DateTimeFormatter.ofPattern("a", Locale.ENGLISH);

    private static final Map<String, Pokemon> PERIOD_TO_EEVEELUTION = Map.of(
            "in the morning", ESPEON,
            "in the afternoon", ESPEON,
            "noon", ESPEON,
            "in the evening", UMBREON,
            "at night", UMBREON,
            "midnight", UMBREON
    );

    private DayPeriodFormatter() {
    }

    /**
     * Formats the time using the abbreviated day period pattern {@code "B"}.
     */
    public static String formatDayPeriod(LocalTime time) {
        return DAY_PERIOD_SHORT.format(time);
    }

    /**
     * Formats the time using the full day period pattern {@code "BBBB"}.
     */
    public static String formatFullDayPeriod(LocalTime time) {
        return DAY_PERIOD_FULL.format(time);
    }

    /**
     * Formats the time using the classic AM/PM pattern {@code "a"}.
     */
    public static String formatAmPm(LocalTime time) {
        return AM_PM.format(time);
    }

    /**
     * Formats the time using a day period pattern for a specific locale.
     */
    public static String formatDayPeriodForLocale(LocalTime time, Locale locale) {
        return DateTimeFormatter.ofPattern("BBBB", locale).format(time);
    }

    /**
     * Determines which Eeveelution Eevee evolves into based on the time of day.
     * Morning/afternoon → Espeon (sunlight), evening/night → Umbreon (moonlight).
     */
    public static Pokemon eeveeEvolution(LocalTime time) {
        String period = DAY_PERIOD_FULL.format(time);
        return PERIOD_TO_EEVEELUTION.getOrDefault(period, ESPEON);
    }
}
