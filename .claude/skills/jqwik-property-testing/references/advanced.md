# Advanced jqwik Reference

## @Property Parameters

```java
@Property(tries = 500, shrinking = ShrinkingMode.FULL, afterFailure = AfterFailureMode.RANDOM_SEED)
void my_property(@ForAll int n) { ... }
```

| Parameter | Default | Description |
|---|---|---|
| `tries` | 1000 | Number of generated value sets to try |
| `seed` | random | Fix the seed to reproduce a specific run |
| `shrinking` | `BOUNDED` | `OFF`, `BOUNDED` (10s timeout), `FULL` |
| `afterFailure` | `PREVIOUS_SEED` | `PREVIOUS_SEED`, `RANDOM_SEED`, `SAMPLE_ONLY`, `SAMPLE_FIRST` |
| `generation` | `AUTO` | `RANDOMIZED`, `EXHAUSTIVE` (enumerates all values if finite) |
| `edgeCases` | `MIXIN` | `MIXIN`, `FIRST`, `NONE` |
| `maxDiscardRatio` | 5 | Max ratio of discarded/total tries before failure |

## Edge Cases

jqwik automatically generates boundary values alongside random ones:
- Integers: `0`, `1`, `-1`, `Integer.MIN_VALUE`, `Integer.MAX_VALUE`
- Strings: `""`, single-char strings
- Collections: empty, single-element

**Injection modes (`edgeCases` attribute):**
- `MIXIN` (default): edge cases are interleaved with random samples throughout the run
- `FIRST`: all edge cases are tried before random samples
- `NONE`: only random values, no edge cases

Failure reports show `edge-cases#tried` to indicate how many boundary values were tested.

## Shrinking

When a property fails, jqwik automatically searches for the smallest input that still causes the failure.

**How it works:**
1. Property fails on some generated value `v`
2. jqwik tries simpler values (smaller integers, shorter strings, fewer elements)
3. Reports the minimal falsifying example alongside the original

**Shrinking modes:**
- `BOUNDED` (default): stops after 10 seconds
- `FULL`: exhaustive search (may be slow)
- `OFF`: no shrinking, reports the original failing value

**Implication for test design:** Don't pre-simplify inputs to make shrinking easier — jqwik handles it. Write properties that express the real invariant on the full domain.

## Lifecycle Annotations

jqwik has its own lifecycle, separate from JUnit 5. Use jqwik annotations in property test classes:

| jqwik | Equivalent JUnit | Runs |
|---|---|---|
| `@BeforeTry` | `@BeforeEach` | Before each generated value set |
| `@AfterTry` | `@AfterEach` | After each generated value set |
| `@BeforeProperty` | — | Once before the property method |
| `@AfterProperty` | — | Once after the property method |
| `@BeforeContainer` | `@BeforeAll` | Once before all properties in the class |
| `@AfterContainer` | `@AfterAll` | Once after all properties in the class |

**In this project:** Use `@BeforeTry` to reset mutable state between tries, not `@BeforeEach`.

## Assume.that() — Preconditions

```java
@Property
void division_is_safe(@ForAll int dividend, @ForAll int divisor) {
    Assume.that(divisor != 0);
    assertEquals(dividend, (dividend / divisor) * divisor + (dividend % divisor));
}
```

- Runs that fail the assumption are **discarded** (not counted as failures or tries)
- If too many runs are discarded, jqwik fails with `TooManyFilterMissesException`
- Default `maxDiscardRatio`: 5 (5× more discards than checks triggers failure)
- Prefer `Assume.that()` over `filter()` on the arbitrary — it surfaces the discard ratio

## Arbitraries.forType() Details

```java
Arbitrary<Mario> marios = Arbitraries.forType(Mario.class);
```

- Scans public constructors of `Mario` and generates arguments recursively
- Works well for Records (single canonical constructor)
- Does **not** apply domain constraints — if `Mario(int speed, int acceleration)` accepts any int, negative values will be generated
- Use a `@Provide` method with explicit constraints when domain validity matters:

```java
@Provide
Arbitrary<Mario> validMarios() {
    return Combinators.combine(
        Arbitraries.integers().greaterOrEqual(1),
        Arbitraries.integers().greaterOrEqual(1)
    ).as(Mario::new);
}
```

Use `Arbitraries.forType()` only when all constructor parameter types have suitable defaults and no domain constraints are required.

## Combining jqwik with JUnit 5

jqwik integrates with JUnit 5 — both `@Test` and `@Property` can coexist in the same class. The JUnit Platform runs both.

In this project:
- `@Test` methods use `Assertions` from `org.junit.jupiter.api`
- `@Property` methods can also use `Assertions` (jqwik wraps them correctly)
- Do not mix jqwik lifecycle (`@BeforeTry`) with JUnit lifecycle (`@BeforeEach`) in the same class unless you understand the interaction

## Reporting and Footnotes

Add contextual information to failure reports:

```java
@Property
void my_property(@ForAll String s, Reporter reporter) {
    reporter.publishValue("input length", String.valueOf(s.length()));
    // ...
}
```

Or use `Footnotes` for per-try annotations visible in shrinking reports.
