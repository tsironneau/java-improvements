# Clean Code Criteria

## Naming

- Classes, methods, and variables have **intention-revealing names**.
- No abbreviations, cryptic names, or misleading names.
- Boolean variables/methods read as predicates (`isValid`, `hasContent`, not `check`, `flag`).

## Functions / Methods

- Each method does **one thing only** (Single Responsibility).
- Methods are **short** — flag anything longer than ~20 lines that could be decomposed.
- No side effects hidden in methods with innocent-sounding names.
- Arguments: prefer 0–2 parameters; flag 3+ and suggest refactoring (parameter object, builder, etc.).

## Classes

- Follow SRP — one reason to change.
- Encapsulation respected (no unnecessary exposure of internals).
- For Records: fields are appropriately immutable and the record abstraction is used correctly.

## Comments

- Code should be **self-documenting**; flag redundant comments that just restate the code.
- Flag missing comments only where the *why* is genuinely non-obvious (e.g., magic numbers, non-trivial formulas).
- No commented-out code.

## Formatting & Structure

- Consistent indentation and blank line usage.
- Related code grouped together.
- Imports are clean (no wildcards, no unused imports).

## Error Handling

- No swallowed exceptions or empty catch blocks.
- Meaningful exception messages.

## DRY (Don't Repeat Yourself)

- Flag duplicated logic and suggest extraction.
