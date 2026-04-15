package com.tsironneau.java16;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Demonstrates the difference between {@code Stream.toList()} (Java 16)
 * and {@code Collectors.toList()}.
 *
 * <p>{@code Stream.toList()} returns an unmodifiable list (like an official Pokédex),
 * while {@code Collectors.toList()} returns a mutable list (like a trainer's party).</p>
 */
public final class StreamToList {

    private StreamToList() {
    }

    /**
     * Returns an unmodifiable list using {@code Stream.toList()} — the official Pokédex.
     * The returned list cannot be modified: no add, remove, or set.
     */
    public static <T> List<T> toUnmodifiableList(List<T> source) {
        return source.stream().toList();
    }

    /**
     * Returns a mutable list using {@code Collectors.toList()} — a trainer's party.
     * The returned list can be freely modified.
     */
    public static <T> List<T> toMutableList(List<T> source) {
        return source.stream().collect(Collectors.toList());
    }
}
