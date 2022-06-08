package com.tsironneau.java14;

import org.jetbrains.annotations.NotNull;

public record RecordWithAnnotations<T>(@NotNull T nonNullParameter) {

}
