# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build and run all tests
mvn test

# Run tests for a single module
mvn test -pl java-14-examples

# Run a single test class
mvn test -pl java-14-examples -Dtest=PointRecordTests

# Build without running tests
mvn package -DskipTests
```

## Architecture

Multi-module Maven project (`io.github.tsironneau:java-improvements`) where each module demonstrates language features introduced in a specific Java version.

**Module layout:**
- `java-11-examples`, `java-12-examples`, `java-13-examples`: examples live entirely in `src/test/java` — the feature under demonstration is exercised directly in test code.
- `java-14-examples` and beyond: `src/main/java` for production-style classes, `src/test/java` for tests.

When adding a new Java version module, follow the `java-14-examples` pattern (main + test split) rather than the earlier test-only pattern.

**Testing:**
- JUnit 5 (Jupiter) is used in all modules.
- jqwik (property-based testing) is available from `java-14-examples` onwards. Use `@Property` + `@ForAll` for property tests alongside regular `@Test` methods.
- Test naming convention: `snake_case` method names with `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)`.
- **Property-first**: write `@Property` tests for universal invariants BEFORE `@Test` examples.

**Dependencies** are declared in the root `pom.xml` `<dependencyManagement>` block and inherited by child modules without version pins.

## Agent Workflow

When adding a new Java version module:

1. **Main context (Opus)** — analyzes Java features, plans the module, creates scaffolding (pom.xml, directories, root pom registration)
2. **`xp-feature-implementer` agents (Sonnet)** — one per feature class, can run in parallel. Each implements: production class + PropertyTest + Test. Reads `.claude/PROJECT_REFERENCE.md` for conventions (no codebase re-exploration).
3. **`clean-code-reviewer` agent (Sonnet)** — reviews the final code for Clean Code, test strategy, and fun factor.

**Key files:**
- `.claude/PROJECT_REFERENCE.md` — comprehensive reference for sub-agents (conventions, templates, module inventory)
- `.claude/agents/xp-feature-implementer.md` — implements a complete feature slice
- `.claude/agents/clean-code-reviewer.md` — reviews code quality
- `.claude/skills/xp-feature-implementation/SKILL.md` — implementation guidelines
- `.claude/skills/java-code-review/` — review criteria and references
- `.claude/skills/jqwik-property-testing/` — jqwik patterns and arbitraries reference
