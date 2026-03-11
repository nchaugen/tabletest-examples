Collection of examples showing how to use [TableTest](https://github.com/nchaugen/tabletest) for table-driven tests.

The repository is used both as a user-facing examples project and as a local regression testbed while developing TableTest and related tooling.

## Build and test

- Default (stable suites only, always green target): `mvn -B clean test`
- Probe tests only (manual checks): `mvn -B test -Pprobe-tests`
- All tests including probes: `mvn -B test -Pall-tests`
- String-array table examples: `mvn -B test -Psnapshot-array-tests`

## TableTest version override

The default TableTest dependency is pinned in `pom.xml` (`tabletest.version=1.0.1-SNAPSHOT`).

To test against another local/snapshot version:

`mvn -B -Dtabletest.version=<version> clean test`

Example:

`mvn -B -Dtabletest.version=1.0.1-SNAPSHOT clean test`

## Example layout

Stable suites are split by namespace:

- Modern namespace (`org.tabletest...`): `src/test/java/io/github/nchaugen/examples/modern` and `src/test/kotlin/io/github/nchaugen/examples/modern`
- Legacy namespace (`io.github.nchaugen.tabletest...`): `src/test/java/io/github/nchaugen/examples/legacy` and `src/test/kotlin/io/github/nchaugen/examples/legacy`

Probe suites are tagged with `@Tag("probe")` and live under:

- `src/test/java/io/github/nchaugen/examples/probe`

String-array table suites (opt-in profile) live under:

- `src/test/java/io/github/nchaugen/examples/stringarraytable`
  - `ModernStringArrayTableExampleTest` demonstrates `@TableTest({ ... })` string-array table input (Java 8 compatible syntax).
  - `ModernCollectionStylesStringArrayTableTest` demonstrates collection literals in string-array tables:
    `List`, `Set`, `Map`, nested `List<List<...>>`, `Map<String, List<...>>`, `List<Map<...>>`, and `Map<String, Map<...>>`.
  - `LegacyJava8MultilineStringExampleTest` demonstrates legacy namespace usage with Java-8-style string concatenation.

## IDE fixture files

Syntax-highlighting and parser fixture files that are intentionally outside the Maven build live under:

- `ide-fixtures/`
