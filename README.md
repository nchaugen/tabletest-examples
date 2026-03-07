Collection of examples showing how to use [TableTest](https://github.com/nchaugen/tabletest) for table-driven tests.

The repository is used both as a user-facing examples project and as a local regression testbed while developing TableTest and related tooling.

## Build and test

- Default (stable suites only, always green target): `mvn -B clean test`
- Probe tests only (manual checks): `mvn -B test -Pprobe-tests`
- All tests including probes: `mvn -B test -Pall-tests`

## TableTest version override

The default TableTest dependency is pinned in `pom.xml` (`tabletest.version=1.0.0`).

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

## IDE fixture files

Syntax-highlighting and parser fixture files that are intentionally outside the Maven build live under:

- `ide-fixtures/`
