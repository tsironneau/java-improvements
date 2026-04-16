# Project Reference — java-improvements

> **Purpose**: This document gives sub-agents all the context they need about the project
> WITHOUT re-exploring the codebase. Read this FIRST, then only read specific files if needed.

---

## 1. Overview

Multi-module Maven project (`io.github.tsironneau:java-improvements`) where each module
demonstrates language features introduced in a specific Java version.
Each module picks a **fun theme** (anime, gaming) to make examples engaging and pedagogical.

**Root**: `/home/tsironneau/code/java-improvements`
**Compiler**: Java 25 source/target (set in root pom.xml, no `--enable-preview` needed)
**Testing stack**: JUnit 5 (Jupiter) + jqwik (property-based testing) from java-14 onwards

---

## 2. Module Inventory

| Module | Theme | Features Demonstrated | Pattern |
|--------|-------|----------------------|---------|
| `java-11-examples` | — | String methods (repeat, isBlank, strip, lines) | test-only |
| `java-12-examples` | — | Switch expressions, String indent/transform, Collectors.teeing | test-only |
| `java-13-examples` | — | Switch yield, Text blocks | test-only |
| `java-14-examples` | One Punch Man | Records, Pattern Matching instanceof, TextBlocks, Switch expressions | main+test |
| `java-15-examples` | Mario Kart | Sealed classes/interfaces, Records implementing sealed, String methods (formatted, stripIndent, translateEscapes) | main+test |
| `java-16-examples` | Pokémon | Stream.toList(), Stream.mapMulti(), Local Records, Day Period formatting | main+test |

**Important**: java-11/12/13 are test-only (no `src/main/java`). From java-14 onwards, follow
the main+test split pattern.

---

## 3. Directory Layout (java-14+ pattern)

```
java-{N}-examples/
├── pom.xml
├── src/
│   ├── main/java/com/tsironneau/java{N}/
│   │   ├── FeatureClass.java
│   │   └── ...
│   └── test/java/com/tsironneau/java{N}/
│       ├── FeatureClassPropertyTest.java   ← jqwik @Property tests
│       ├── FeatureClassTest.java           ← JUnit @Test methods
│       └── ...
```

**Package**: `com.tsironneau.java{N}` (NOT `io.github.tsironneau`)

---

## 4. Module pom.xml Template

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <artifactId>java-improvements</artifactId>
        <groupId>io.github.tsironneau</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>java-{N}-examples</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.jqwik</groupId>
            <artifactId>jqwik</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</project>
```

No version pins — all managed in root `<dependencyManagement>`.
Root pom.xml `<modules>` block must include the new module.

---

## 5. Testing Conventions

### File Naming
- `FeaturePropertyTest.java` — jqwik `@Property` tests (invariants)
- `FeatureTest.java` — JUnit `@Test` methods (specific examples, edge cases)

### Test Class Structure
```java
package com.tsironneau.java{N};

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FeatureClassTest {
    // snake_case method names — MANDATORY
}
```

### Property-First Approach
1. **@Property** FIRST for universal invariants (for all X, Y holds)
2. **@Test** SECOND for specific examples and edge cases

### When to use @Property vs @Test

| Use @Property | Use @Test |
|---------------|-----------|
| Universal invariant ("for all inputs...") | Specific example with concrete values |
| Round-trip / idempotency | Exception/error path |
| Equivalence between two implementations | Edge case at exact boundary |
| Ordering / comparison property | Non-deterministic or locale-dependent |
| Algebraic property (associativity, etc.) | Side-effect verification |

### jqwik Patterns (Quick Reference)

```java
// Basic property with auto-generated values
@Property
void property_name(@ForAll List<String> input) { ... }

// Custom arbitrary provider
@Provide
Arbitrary<MyType> myTypeArbitrary() {
    return Arbitraries.integers().between(0, 100).map(MyType::new);
}

@Property
void uses_custom(@ForAll("myTypeArbitrary") MyType value) { ... }

// LocalTime arbitrary (common pattern)
@Provide
Arbitrary<LocalTime> anyLocalTime() {
    return Arbitraries.integers().between(0, 86399).map(LocalTime::ofSecondOfDay);
}

// Constraint annotations
@ForAll @Positive int n           // > 0
@ForAll @IntRange(min=1, max=10)  // range
@ForAll @NotEmpty List<String>    // non-empty

// Preconditions (discard invalid inputs)
Assume.that(input > 0);

// Lifecycle (NOT @BeforeEach)
@BeforeTry void setup() { ... }
```

### Default Range Rule
**Always start with default `@ForAll` ranges.** Only add constraints (`@Positive`, `@NotEmpty`,
`@IntRange`) when there is a **technical reason** (e.g., division by zero, array index).
Never constrain for convenience.

---

## 6. Production Code Conventions

- **Utility classes**: `private` constructor, `final` class, `static` methods
- **Records**: for immutable data carriers; compact constructors for validation
- **Sealed interfaces/classes**: for closed hierarchies with exhaustive pattern matching
- **Javadoc**: on classes and public methods; include thematic references (fun theme)
- **No redundant comments** inside method bodies — code should be self-documenting
- **Naming**: intention-revealing, no abbreviations, predicates as `isX`/`hasX`
- **No hardcoded strings for domain values**: extract repeated string literals into enums or
  constants. If a set of related strings represents domain concepts (e.g., character names,
  categories, statuses), create an enum with a `displayName()` method. This provides type safety,
  prevents typos, and centralizes definitions. Example: `Pokemon.PIKACHU` over `"Pikachu"`.
- **Static imports for enum constants**: when a class uses enum constants heavily, use
  `import static` to import them (e.g., `import static com.tsironneau.java16.Pokemon.*;`).
  This allows writing `ESPEON` instead of `Pokemon.ESPEON`, improving readability when the
  context makes the type obvious.
- **Methods**: SRP, short (<20 lines), 0-2 parameters preferred
- **No side effects** in pure methods

---

## 7. Build Commands

```bash
# Build and test single module
mvn test -pl java-{N}-examples

# Run single test class
mvn test -pl java-{N}-examples -Dtest=FeatureClassTest

# Build all modules
mvn test

# Build without tests
mvn package -DskipTests

# Build single module without tests
mvn package -DskipTests -pl java-{N}-examples
```

---

## 8. Commit Conventions

```
feat(java{N}): [what was added]

Co-Authored-By: Claude Opus 4.6 (1M context) <noreply@anthropic.com>
```

---

## 9. Existing Themes (avoid reuse)

| Module | Theme | Key Characters/References |
|--------|-------|--------------------------|
| java-14 | One Punch Man | DisasterLevel enum (WOLF, TIGER, DEMON, DRAGON, GOD) |
| java-15 | Mario Kart | Racer sealed interface (Mario, Bowser, Toad), RacerCategory |
| java-16 | Pokémon | Pokédex, evolution chains, Eevee/Espeon/Umbreon, trainer party |

When creating a new module, pick a different fun theme (anime, gaming, movies, etc.).

---

## 10. Known Issues

- `java-14-examples`: `RecordWithAnnotationsTest.throw_exception_when_null_parameter_given_to_constructor`
  fails (pre-existing, unrelated to new modules). Use `-pl` to test specific modules.
