---
name: jqwik-property-testing
description: This skill should be used when writing, reviewing, or improving property-based tests with jqwik. Apply it when the user asks to "write a property test", "add @Property", "use jqwik", "test with arbitrary values", "write a property for", or when a @Test method could be expressed as a universal invariant.
version: 0.1.0
---

# Property-Based Testing with jqwik

This skill covers how to write effective property-based tests in the `java-improvements` project using jqwik. It builds on the official jqwik user guide and the project-specific conventions established in this codebase.

## When to Write a @Property Instead of a @Test

Use `@Property` whenever the behavior can be expressed as a universal statement:
- "For all X, the result satisfies Y" — invariants (always positive, never null, monotonic)
- "For all X, f(g(X)) == X" — round-trips (serialize/deserialize, encode/decode)
- "For all X, f(X) == g(X)" — equivalence between two implementations
- "For all X satisfying P, the result satisfies Q" — conditional invariants

Use `@Test` only when the scenario is inherently example-specific: a specific edge case, a side effect, or a behavior that cannot be generalized.

**Flag any `@Test` that asserts a universal statement and propose a `@Property` rewrite.**

## Anatomy of a Property Test

```java
@Property
void formatted_is_equivalent_to_String_format(
        @ForAll String name,
        @ForAll int age) {
    String template = "%s is %d years old";
    assertEquals(String.format(template, name, age), template.formatted(name, age));
}
```

Key elements:
- `@Property` — marks the method as a property test (run ~1000 times by default)
- `@ForAll` — marks parameters to be generated randomly by jqwik
- The method body expresses the invariant via assertions

## The Default Range Rule

**Always start with default ranges. Add constraints only when the default range causes the property to fail for a technically justified reason.**

| Situation | What to do |
|---|---|
| Property fails with negative ints and the API doesn't support them | Add `@Positive` |
| Property fails with empty strings and the API requires non-empty input | Add `@NotEmpty` |
| Property passes with all ints | Use plain `@ForAll int` — no constraint |
| You're restricting a range to avoid thinking about edge cases | Do not restrict — fix the property or the API |

Never restrict a range to make a failing test pass. A failure with default ranges is information: it reveals a real edge case or a poorly defined property.

## Generating Values

### Default (no configuration needed)
jqwik auto-generates `int`, `long`, `double`, `String`, `boolean`, `char`, `List`, `Set`, `Optional`, enums, and more. Use plain `@ForAll` when the default range is appropriate.

### Constraint annotations
Apply on `@ForAll` parameters to narrow the domain:
```java
@ForAll @Positive int n           // n > 0
@ForAll @Negative int n           // n < 0
@ForAll @NotEmpty String s        // s.length() >= 1
@ForAll @IntRange(min=1, max=9) int w  // only when technically required
```

See `references/arbitraries.md` for the full list.

### Custom providers with @Provide
Use a `@Provide` method when generating domain objects or combining multiple arbitraries:

```java
@Property
void area_is_always_positive(@ForAll("circles") Circle c) {
    assertTrue(c.area() > 0);
}

@Provide
Arbitrary<Circle> circles() {
    return Arbitraries.doubles().greaterOrEqual(0.01).map(Circle::new);
}
```

The method name must match the string in `@ForAll("name")`.

### Generating sealed types
Use `Arbitraries.oneOf()` to combine generators for each permitted subtype:

```java
@SuppressWarnings("unchecked")
@Provide
Arbitrary<Racer> racers() {
    return Arbitraries.oneOf(
            Arbitraries.forType(Mario.class),
            Arbitraries.forType(Bowser.class),
            Arbitraries.forType(ToadWithMushroom.class)
    );
}
```

`Arbitraries.forType(T.class)` auto-generates instances of `T` using its public constructors. Works well for Records. Use the `@SuppressWarnings("unchecked")` on the provider method when combining via `oneOf`.

### Preconditions with Assume.that()
When the domain is a subset of the generated values, use `Assume.that()` rather than if-guards or tight range constraints:

```java
@Property
void division_result_is_positive(@ForAll int a, @ForAll int b) {
    Assume.that(a > 0 && b > 0);
    assertTrue(a / b >= 0);
}
```

Runs that fail the assumption are discarded (not counted as failures). If too many runs are discarded (default ratio: 5×), jqwik reports a warning.

## Naming and Conventions (enforced in this project)

- `snake_case` method names for all property methods
- `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` on **every** test class, including property test classes
- Property method names should state the invariant: `heavier_bowser_has_lower_or_equal_acceleration`, not `test_bowser`
- Lifecycle: use `@BeforeTry` / `@AfterTry` (jqwik-specific), not `@BeforeEach` / `@AfterEach`

## Additional Resources

- **`references/arbitraries.md`** — Full list of built-in arbitraries, constraint annotations, and combinators (map, filter, flatMap, combine)
- **`references/advanced.md`** — Shrinking, edge cases, generation modes, @Property parameters, forType details
