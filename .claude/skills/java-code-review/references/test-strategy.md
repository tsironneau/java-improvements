# Test Strategy: Property-Based First

## Decision Hierarchy

### When `@Property` + `@ForAll` (jqwik) is mandatory

Use property-based tests whenever:
- The behavior can be expressed as a **mathematical or logical invariant** (symmetry, idempotency, round-trip, commutativity).
- The code processes a **range of values** rather than specific fixed inputs.
- The statement "for all X satisfying condition Y, the result should satisfy Z" applies.
- Examples: serialization/deserialization round-trips, algebraic properties of records, string transformation invariants, ordering properties.

### When `@Test` (JUnit 5) is acceptable

Use unit tests only when:
- The test is inherently **example-specific** (e.g., a single well-known edge case like empty string, null, or a semantically meaningful boundary value).
- The behavior is **non-deterministic** or context-dependent in ways jqwik cannot model.
- The scenario involves **side effects** (I/O, exceptions) that are hard to generate arbitrarily.

Flag any `@Test` method that could be expressed as a `@Property` and propose a rewrite.

## jqwik Best Practices

- **Always start with default ranges.** Write the property with plain `@ForAll` and no constraints first. Only add constraints (`@Positive`, `@IntRange`, `@NotEmpty`, etc.) if the test fails with the default range *and* there is a clear technical reason (e.g., the API under test rejects negative values, or the domain semantics exclude certain inputs). Never restrict a range out of convenience or to make a failing test pass — that defeats the purpose of property testing.
- Use `@ForAll` with constraints only when justified: `@Positive`, `@NotEmpty`, `@StringLength`, `@IntRange`, custom `@Provide` arbitraries.
- Property descriptions should clearly state the invariant being verified.
- Use `Assume.that(...)` for preconditions rather than if-guards inside the property body.
- Rely on shrinking — avoid artificially constraining inputs too tightly (defeats the purpose of property testing).
- Use `@BeforeTry` (not `@BeforeEach`) for per-try setup in property test classes.
- For sealed types or custom domains, use a `@Provide` method with `Arbitraries.oneOf(...)`.

## Naming Convention (must be enforced)

- `snake_case` method names in all test classes.
- `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` must be present on **every** test class, including property test classes.
