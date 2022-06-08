package com.tsironneau.java14;

import java.util.Optional;

@SuppressWarnings("PatternVariableCanBeUsed")
public class PatternMatchingInstanceOf {

    Optional<String> beforeJava_14(Object obj) {

        if (obj instanceof String) {
            final String s = (String) obj;
            return Optional.of(s.toLowerCase());
        }
        return Optional.empty();
    }

    Optional<String> withJava_14(Object obj) {

        if (obj instanceof String s) {
            return Optional.of(s.toLowerCase());
        }
        return Optional.empty();
    }

    public final String s = "string field value";

    String scopeOfThePatternVariable(Object obj){

        if (obj instanceof String s) {
            return s; // s = pattern variable
        }else{
            return s; // s = field
        }
    }

}

