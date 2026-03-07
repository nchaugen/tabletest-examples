<!-- Copilot instructions for contributors and AI agents -->
# Repository-specific guidance for AI coding agents

Purpose
- Short: help an AI quickly understand this Maven Java examples repo and be productive.

Big picture
- This is a small Maven Java project of domain examples under `io.github.nchaugen`.
- Main domain packages: `orders` (cart, inventory, order splitting) and `tickets` (pricing, discounts, purchase history).
- Tests heavily use a custom table-driven JUnit extension via `@TableTest` (see tests under `src/test/java`).

Key files to inspect
- `pom.xml` — build and dependency configuration.
- `src/main/java/io/github/nchaugen/orders/Cart.java` — example domain model and business logic.
- `src/main/java/io/github/nchaugen/tickets/PurchaseHistory.java` — business logic around purchases.
- `src/test/java/io/github/nchaugen/json/MapToJsonTest.java` — demonstrates table-driven tests and value converters.
- `src/test/resources/junit-platform.properties` — test publisher config (tabletest output format = asciidoc).

Developer workflows (concrete commands)
- Run full test suite: `mvn test` (or `mvn -DskipTests=false test`).
- Run a single test class: `mvn -Dtest=io.github.nchaugen.json.MapToJsonTest test`.
- Run a single test method: `mvn -Dtest=io.github.nchaugen.json.MapToJsonTest#shouldConvertMapToJson test`.
- Build without tests: `mvn -DskipTests package`.

Testing patterns and conventions
- Table-driven tests: many tests use the `@TableTest("""...""")` annotation. Table rows map to method parameters and the framework looks for static helper conversion methods in the same test class (e.g. `toJson`, `toDomainObject`). See `MapToJsonTest` for examples.
- JUnit platform: tests use JUnit Jupiter; test output/publishers are configured via `src/test/resources/junit-platform.properties`.
- Test artifacts: test reports and per-test folders appear under `target/junit-jupiter/` and Surefire output under `target/surefire-reports/`.

Project-specific patterns
- Conversions inline in tests: tests often include small serializer/deserializer helpers (see `SmartStringSerializer` used by `MapToJsonTest`). When editing tests, follow this pattern: prefer small, local static helpers for table conversions.
- Domain-first code: core domain objects are plain POJOs/records in `src/main/java/io/github/nchaugen/*`. Business logic lives in the same package and is exercised directly from tests.

Integration and external dependencies
- No external services are contacted by tests; all behavior is in-process and driven by domain code and JUnit extensions.
- Check `pom.xml` for third-party dependencies (Jackson, JUnit, any tabletest publisher) before adding new libraries.

Editing guidance for AI agents
- When changing behavior, run impacted tests locally using the `mvn -Dtest=... test` pattern to get quick feedback.
- Preserve table-driven test signatures: if you change parameter types, update any `toX` helper conversion methods in the test class.
- Keep changes minimal and focused: library aims to be example-driven; avoid introducing heavy frameworks or infra.

Examples to reference when proposing changes
- Converter example: `src/test/java/io/github/nchaugen/json/SmartStringSerializer.java`.
- Table-test example: `src/test/java/io/github/nchaugen/json/MapToJsonTest.java`.
- Order splitting example: `src/main/java/io/github/nchaugen/orders/Inventory.java` and `src/test/java/io/github/nchaugen/orders/OrderSplittingTest.java`.

If unclear
- Ask which package or test to focus on and whether to run the full test suite or a targeted subset. Mention the failing test name and any stack traces.

Feedback request
- After applying changes, run the related tests and report failures so instructions can be updated with exact reproduction steps.
