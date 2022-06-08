package com.tsironneau.java12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DisplayName("Some tests to experience Java 12 Collectors Methods")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CollectorsExample {

    @Test
    public void teeing__sort_number_list_with_even_before_odd() {
        //Create a Integer list from 0 to 9 with even number first and odd number after
        final List<Integer> collect = IntStream.range(0, 10).boxed().collect(
                Collectors.teeing(
                        //Filter the even numbers
                        Collectors.filtering(i -> i % 2 == 0, Collectors.toList()),
                        //Filter the odd numbers
                        Collectors.filtering(i -> i % 2 == 1, Collectors.toList()),
                        //Merge previous lists
                        (List<Integer> a, List<Integer> b) -> {
                            a.addAll(b);
                            return a;
                        }
                )
        );

        System.out.println(collect);
    }

}
