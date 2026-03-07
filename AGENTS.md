# Repository Guidance for Coding Agents

## Purpose

Help coding agents quickly understand this repo and make safe, focused changes.

## Big Picture

- Maven-based Java examples project for TableTest usage.
- Also used as a regression testbed for TableTest and related tooling.
- Core domain examples live under `io.github.nchaugen` (`orders`, `tickets`, `docs`).

## Current Test Layout

- Stable example suites:
  - Modern namespace (`org.tabletest...`): `src/test/java/io/github/nchaugen/examples/modern`, `src/test/kotlin/io/github/nchaugen/examples/modern`
  - Legacy namespace (`io.github.nchaugen.tabletest...`): `src/test/java/io/github/nchaugen/examples/legacy`, `src/test/kotlin/io/github/nchaugen/examples/legacy`
- Manual probe tests (excluded by default): `src/test/java/io/github/nchaugen/examples/probe`
- IDE/parser fixtures (never compiled by Maven): `ide-fixtures/`

## Build and Test Commands

- Default stable suite (green target): `mvn -B clean test`
- Probe tests only: `mvn -B test -Pprobe-tests`
- All tests (stable + probe): `mvn -B test -Pall-tests`
- Override TableTest version locally: `mvn -B -Dtabletest.version=<version> clean test`

## Build/Dependency Notes

- Java target: 21 (`maven.compiler.release`)
- Kotlin test compilation enabled via `kotlin-maven-plugin`
- `tabletest.version` property controls `org.tabletest:tabletest-junit`
- Surefire excludes `probe` tag by default; profiles control inclusion

## Editing Guidance

- Preserve table-driven signatures when editing `@TableTest` methods.
- Keep conversion helpers close to tests when practical.
- Prefer descriptive test names reflecting behaviour/scenario.
- Keep fixture/probe names feature-oriented (for example: escape parsing, comment token handling, nested converter lookup).

## Useful References

- `pom.xml`
- `README.md`
- `src/test/java/io/github/nchaugen/json/MapToJsonTest.java`
- `src/test/java/io/github/nchaugen/orders/OrderSplittingTest.java`
- `ide-fixtures/README.md`
