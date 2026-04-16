# XP Feature Implementation Skill

Implements a **complete feature slice** for the java-improvements project:
production class + PropertyTest (jqwik) + Test (JUnit 5).

This replaces the granular RED → GREEN → REFACTOR cycle with a single pass
that respects the same quality constraints.

## Pre-requisites

Before writing any code, **read the project reference**:
```
.claude/PROJECT_REFERENCE.md
```

This gives you all conventions, templates, and patterns. Do NOT re-explore the codebase
unless the reference is insufficient for your specific case.

For jqwik details beyond the quick reference, consult:
- `.claude/skills/jqwik-property-testing/references/arbitraries.md`
- `.claude/skills/jqwik-property-testing/references/advanced.md`

## Implementation Order

For each feature, implement in this order:

### 1. Production Class (`src/main/java`)
- Follow conventions from PROJECT_REFERENCE.md §6
- Javadoc on class and public methods with thematic references
- Private constructor for utility classes
- Keep methods short, focused, intention-revealing

### 2. Property Tests (`*PropertyTest.java`)
- Write these FIRST — they define the universal invariants
- One `@Property` method per invariant
- Use `@ForAll` with default ranges (constrain only for technical reasons)
- Custom `@Provide` methods when built-in arbitraries don't suffice
- `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` — MANDATORY
- `snake_case` method names — MANDATORY

### 3. Unit Tests (`*Test.java`)
- Write these SECOND — specific examples, edge cases, concrete scenarios
- Use thematic/fun naming (reference the module's chosen theme)
- Cover: empty inputs, boundary values, error paths, thematic examples
- `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` — MANDATORY
- `snake_case` method names — MANDATORY

## Verification

After writing all code for the feature:

```bash
# Compile check
mvn package -DskipTests -pl {module}

# Run only this feature's tests
mvn test -pl {module} -Dtest={PropertyTestClass},{TestClass}

# Run all module tests (regression check)
mvn test -pl {module}
```

All three must pass before reporting success.

## Quality Checklist

Before declaring done, verify:
- [ ] Property tests cover universal invariants (not just happy path)
- [ ] Unit tests cover edge cases and thematic examples
- [ ] `@DisplayNameGeneration` on every test class
- [ ] `snake_case` method names in all tests
- [ ] No redundant comments in code
- [ ] Methods are short (<20 lines) and focused
- [ ] Naming is intention-revealing
- [ ] Javadoc on production class and public methods
- [ ] Tests use fun, thematic examples (not generic "foo", "bar")

## Output Format

Report back with:
```
## Feature: [name]

### Files Created/Modified
- `src/main/java/.../FeatureClass.java` — [brief description]
- `src/test/java/.../FeaturePropertyTest.java` — [N] @Property tests
- `src/test/java/.../FeatureTest.java` — [N] @Test methods

### Test Results
- Total tests: [N]
- Property tests: [N] passing
- Unit tests: [N] passing
- Module regression: none

### Key Design Decisions
- [any notable choices made]
```
