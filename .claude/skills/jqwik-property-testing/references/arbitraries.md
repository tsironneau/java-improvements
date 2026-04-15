# Arbitraries Reference

## Built-in Auto-generated Types

jqwik generates these types with no configuration using plain `@ForAll`:

| Category | Types |
|---|---|
| Integer | `byte`, `short`, `int`, `long`, `BigInteger` |
| Floating | `float`, `double`, `BigDecimal` |
| Text | `String`, `char`, `Character` |
| Boolean | `boolean`, `Boolean` |
| Collections | `List<T>`, `Set<T>`, `Stream<T>`, `Map<K,V>`, arrays |
| Other | `Optional<T>`, enums |

Default ranges:
- Integers: `Integer.MIN_VALUE` to `Integer.MAX_VALUE`
- Strings/collections: length 0–255

## Constraint Annotations

Apply directly on `@ForAll` parameters. Add only when the default range is technically incompatible with the API under test.

### Numeric

| Annotation | Effect |
|---|---|
| `@Positive` | value > 0 |
| `@Negative` | value < 0 |
| `@IntRange(min, max)` | integer bounds (inclusive) |
| `@LongRange(min, max)` | long bounds |
| `@DoubleRange(min, max)` | double bounds |
| `@FloatRange(min, max)` | float bounds |
| `@BigRange(min, max)` | BigDecimal bounds |
| `@Scale(n)` | decimal places for floating types |

### Strings

| Annotation | Effect |
|---|---|
| `@NotEmpty` | length >= 1 |
| `@StringLength(min, max)` | length bounds |
| `@AlphaChars` | alphabetic characters only |
| `@NumericChars` | numeric characters only |
| `@LowerChars` / `@UpperChars` | case constraint |
| `@Chars("abc")` | restrict to given characters |
| `@CharRange(from, to)` | character range |

### Collections

| Annotation | Effect |
|---|---|
| `@Size(min, max)` | collection size bounds |
| `@NotEmpty` | at least one element |
| `@UniqueElements` | all elements distinct |

### Nullability

| Annotation | Effect |
|---|---|
| `@WithNull` | includes `null` values in generation |
| `@NotNull` | excludes `null` (usually the default) |

## Building Custom Arbitraries

### Fluent API on Arbitraries

```java
Arbitraries.integers().greaterOrEqual(1)
Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(20)
Arbitraries.doubles().between(0.0, 1.0)
Arbitraries.longs().lessThan(0)
```

### map — transform generated values

```java
Arbitrary<Circle> circles =
    Arbitraries.doubles().greaterOrEqual(0.01).map(Circle::new);
```

### filter — restrict by predicate

```java
Arbitrary<Integer> evens =
    Arbitraries.integers().filter(n -> n % 2 == 0);
```

Prefer `Assume.that()` in the property body over `filter()` when possible — `filter()` discards silently without the discard ratio safeguard.

### flatMap — dependent generation

```java
Arbitrary<List<Integer>> listsWithMatchingSize =
    Arbitraries.integers().between(1, 10).flatMap(size ->
        Arbitraries.integers().list().ofSize(size)
    );
```

### Combinators.combine — merge multiple arbitraries

```java
Arbitrary<Rectangle> rectangles =
    Combinators.combine(
        Arbitraries.doubles().greaterOrEqual(0.01),
        Arbitraries.doubles().greaterOrEqual(0.01)
    ).as(Rectangle::new);
```

### Arbitraries.oneOf — uniform mix of multiple arbitraries

```java
Arbitrary<Shape> shapes = Arbitraries.oneOf(circles, rectangles, triangles);
```

Each subtype is equally likely. When mixing concrete types for a sealed hierarchy, annotate the `@Provide` method with `@SuppressWarnings("unchecked")`.

### Arbitraries.frequency — weighted mix

```java
Arbitrary<Shape> shapes = Arbitraries.frequency(
    Tuples.tuple(3, circles),   // 3x more likely
    Tuples.tuple(1, rectangles)
);
```

### Arbitraries.forType — auto-generate from constructors

```java
Arbitrary<Mario> marios = Arbitraries.forType(Mario.class);
```

Generates instances using the public constructors of `T`, recursively generating constructor arguments. Works well for Records. Does not handle types with no public constructor or types requiring domain constraints on constructor arguments — use a `@Provide` method with `Combinators.combine()` in that case.

### Arbitraries.defaultFor — programmatic default

```java
Arbitrary<Integer> ints = Arbitraries.defaultFor(Integer.class);
```

Useful inside `@Provide` methods when you need the standard arbitrary for a type without hardcoding it.
