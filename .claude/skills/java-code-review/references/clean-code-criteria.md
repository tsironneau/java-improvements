# Clean Code Criteria

## Minimal Code (java-improvements specific)

**CRITICAL**: Generate ONLY the code strictly necessary to demonstrate the Java feature. This is a teaching project, not a production system.

- ✅ A 10-line Record with just enough fields to show the feature
- ✅ One or two methods that directly illustrate the feature
- ✅ If additional classes are needed, create them in dedicated `.java` files
- ❌ Extra helper methods, utilities, or "enhancements"
- ❌ Full-featured domain models or "complete" systems
- ❌ Code that obscures the feature with surrounding infrastructure
- **CRITICAL**: **Do NOT use nested/internal classes** to avoid creating separate files
  - ❌ `class Main { static class Helper { ... } }` ← wrong
  - ✅ `Helper.java` as a dedicated file ← right

**If you find yourself writing more than ~20 lines of production code, ask: "Is all of this necessary to demonstrate the feature?"**
**If a class is needed, it deserves its own file — do not nest it.**

---

## Feature Class Naming (java-improvements specific)

**CRITICAL for this project**: Feature classes must have **explicit names that reveal which Java feature is demonstrated** without opening the class.

- ✅ `PointRecord` — clearly demonstrates Records
- ✅ `RacerSealed` — clearly demonstrates sealed classes/interfaces
- ✅ `StreamToList` — clearly demonstrates Stream.toList()
- ✅ `DisasterLevelEnum` — clearly demonstrates Enums
- ❌ `FeatureClass` — unclear what is being taught
- ❌ `Example` — could be anything
- ❌ `Impl` — vague and unhelpful

Recommendation: If the feature name doesn't instantly convey *which Java feature* is illustrated, suggest renaming.

## Naming

- Classes, methods, and variables have **intention-revealing names**.
- No abbreviations, cryptic names, or misleading names.
- Boolean variables/methods read as predicates (`isValid`, `hasContent`, not `check`, `flag`).

## Functions / Methods

- Each method does **one thing only** (Single Responsibility).
- Methods are **short** — flag anything longer than ~20 lines that could be decomposed.
- No side effects hidden in methods with innocent-sounding names.
- Arguments: prefer 0–2 parameters; flag 3+ and suggest refactoring (parameter object, builder, etc.).

## Readability > Concision (CRITICAL in java-improvements)

**Always prefer clarity over brevity.** Teaching code should be immediately understandable.

- ❌ **Nested ternaries**: `return rank == ELITE ? HIGH : rank == VETERAN ? MODERATE : LOW;`
- ✅ **If-else blocks**: 
  ```java
  if (rank == ELITE) return HIGH;
  if (rank == VETERAN) return MODERATE;
  return LOW;
  ```
- ❌ **Complex stream chains**: `.filter(...).map(...).flatMap(...).collect(...)`
- ✅ **Intermediate variables** for clarity:
  ```java
  var filtered = list.stream().filter(...).collect(toList());
  var mapped = transform(filtered);
  ```
- ❌ **One-liners that hide logic**
- ✅ **Explicit, readable code even if longer**

**Rule**: If you have to pause to understand what the code does, it's too condensed. Refactor for clarity.

## Classes

- Follow SRP — one reason to change.
- Encapsulation respected (no unnecessary exposure of internals).
- For Records: fields are appropriately immutable and the record abstraction is used correctly.

## Comments & Javadoc

- Code should be **self-documenting**; flag redundant comments that just restate the code.
- Flag missing comments only where the *why* is genuinely non-obvious (e.g., magic numbers, non-trivial formulas).
- No commented-out code.

### Javadoc in java-improvements (Feature-Focused)

**CRITICAL**: Javadoc on classes and public methods must:
1. **Explain the Java feature being demonstrated**, NOT business logic
2. **Include JEP number and status** (finalized or preview)

- ✅ "Demonstrates Java 14 Records (JEP 359, finalized): immutability, compact constructors, auto-generated accessors/equals/hashCode"
- ✅ "Uses sealed interface (JEP 409, finalized) to restrict implementation hierarchy and enable exhaustive pattern matching"
- ✅ "Shows Stream.toList() (JEP 411, finalized) as a convenient alternative to collect(Collectors.toList())"
- ❌ "A data structure for holding coordinates" (business context, not feature)
- ❌ "Stores racer information" (vague, not educational, missing JEP)
- ❌ "A utility to process streams" (too generic, missing JEP)

**If Javadoc reads like it could apply to any business domain, rewrite it to focus on the Java feature.**
**Always include the JEP and its status (finalized or preview) in Javadoc.**

## Formatting & Structure

- Consistent indentation and blank line usage.
- Related code grouped together.
- Imports are clean (no wildcards, no unused imports).

## Error Handling

- No swallowed exceptions or empty catch blocks.
- Meaningful exception messages.

## Magic Constants (CRITICAL in java-improvements)

**MUST FLAG**: Magic numbers, magic strings, and hardcoded domain values.

- ❌ `if (level > 5)` — magic number, unclear what 5 means
- ❌ `"DRAGON"` hardcoded in code — magic string, brittle to change
- ❌ `if (status.equals("active"))` — magic string comparison
- ✅ `if (level.isHighThreat())` — explicit intent using enum methods
- ✅ `enum DisasterLevel { WOLF, TIGER, DEMON, DRAGON, GOD }` — type-safe, centralized

**Rule**: All domain values (categories, statuses, types, names) MUST be represented as enums or named constants.
- Domain enums provide: type safety, compile-time checking, and exhaustive pattern matching
- Example: `Pokemon.PIKACHU` over `"Pikachu"` (string literal is a magic constant)

## DRY (Don't Repeat Yourself)

- Flag duplicated logic and suggest extraction.
