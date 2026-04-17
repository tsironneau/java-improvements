---
name: java-code-review
description: This skill should be used when reviewing Java code written in the java-improvements project. Apply it to check Clean Code compliance, appropriate use of property-based testing vs unit testing, and the quality/fun factor of chosen examples.
version: 0.1.0
---

# Java Code Review Skill

This skill defines the review process for the `io.github.tsironneau:java-improvements` multi-module Maven project. It covers three pillars: Clean Code compliance, test strategy, and example quality.

## Project Context

- Multi-module Maven project; each module showcases features of a specific Java version.
- `java-11`, `java-12`, `java-13`: tests only in `src/test/java`.
- `java-14` and beyond: production classes in `src/main/java`, tests in `src/test/java`.
- Testing stack: JUnit 5 everywhere; jqwik (property-based testing) available from `java-14` onwards.
- Test naming: `snake_case` method names with `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` on every test class.
- Dependencies managed centrally in the root `pom.xml`.

## Review Process

### Step 1 — Identify scope

Review only recently added or modified files unless explicitly asked for a full review.

### Step 2 — Apply the three pillars

For detailed criteria on each pillar, consult the reference files:

- **Pillar 1 (Clean Code)** → `references/clean-code-criteria.md`
- **Pillar 2 (Test Strategy)** → `references/test-strategy.md`
- **Pillar 3 (Fun Factor)** → inline below (short enough to stay here)

### Step 3 — Produce structured output

Use this output format:

```
## Code Review — [File or Feature Name]

### ✅ Strengths
- [Specific, non-generic praise]

### 🧹 Clean Code Issues
- [SEVERITY: low/medium/high] Description + file + line reference.
  → Suggested fix with code snippet.

### 🧪 Test Strategy Issues
- [MUST FIX / SUGGESTION] Description.
  → Proposed rewrite if applicable (include snippet).

### 🎉 Fun Factor
- Assessment of example quality.
  → Alternatives if needed.

### 📋 Summary
Concise paragraph: overall quality + 2–3 most important actions.
```

Severity guide:
- **high**: violates a core principle, likely causes bugs or serious maintainability issues.
- **medium**: clearly improves quality or coverage.
- **low**: minor stylistic suggestion.

---

## Pillar 3 — Fun and Engaging Examples

This project is pedagogical. Examples should be memorable and slightly playful:

- Flag boring/generic examples (`Foo`, `Bar`, `MyClass`, `example1`, `test123`, a basic `Person` with `name`/`age`).
- Suggest alternatives that are thematic, pop-culture-inspired, or whimsical while remaining easy to understand. Examples: pizza orders, space missions, Pokémon stats, Mario Kart characters, board game scores, fictional characters with funny properties.
- The fun should serve the concept, not obscure it.
- Property-based test invariant names can be playful: `adding_toppings_never_reduces_pizza_price` rather than `test_price_increases`.

---

## Behavioral Guidelines

- Be constructive and specific — never vague criticism like "this is not clean".
- Always provide concrete code suggestions when proposing changes.
- If a file is too short or trivial to review meaningfully, say so briefly.
- If uncertain whether a `@Test` could be a `@Property`, explain the reasoning and let the developer decide.
- Respect module structure: don't suggest jqwik in modules where it is not available (`java-11`, `java-12`, `java-13`).
- **CRITICAL**: Flag magic constants — all domain values must be enums, not hardcoded.
  - ✅ `enum DisasterLevel { WOLF, TIGER, DEMON, DRAGON }`
  - ❌ `if (level > 5)` or `"DRAGON"` string literal
  - See clean-code-criteria.md for details
- **CRITICAL**: Flag poor readability — readability > concision in teaching code.
  - ❌ Nested ternaries, complex stream chains, one-liners that hide logic
  - ✅ If-else blocks, switch statements, intermediate variables for clarity
  - Example: Suggest breaking up `rank == ELITE ? HIGH : rank == VETERAN ? MODERATE : LOW` into a readable if-else block
- **CRITICAL**: Flag excessive code — this project demonstrates features, not builds systems.
  - ✅ Minimal, focused code that shows the feature
  - ✅ If classes are needed, each gets its own `.java` file
  - ❌ Extra helpers, utilities, or "nice-to-have" enhancements
  - ❌ Nested/internal classes (flag these and suggest separate files)
  - ❌ Code that obscures the feature with surrounding infrastructure
- **CRITICAL**: Flag Javadoc that:
  - Explains business logic instead of the Java feature
  - Missing JEP number and status (finalized or preview)
  - ✅ "Demonstrates Java 14 Records (JEP 359, finalized): syntax, immutability, accessors"
  - ❌ "Stores a point's coordinates" (business detail)
  - ❌ "Demonstrates Records" (missing JEP info)

## Additional Resources

- **`references/clean-code-criteria.md`** — Detailed Clean Code checklist (naming, functions, classes, DRY, error handling)
- **`references/test-strategy.md`** — Property-based vs unit testing decision hierarchy, jqwik best practices
