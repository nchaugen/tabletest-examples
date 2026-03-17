# TableTest manual IDE verification suite

This directory complements the manual probe source files under:

- `src/test/java/io/github/nchaugen/examples/probe/manualsuite/`
- `src/test/kotlin/io/github/nchaugen/examples/probe/manualsuite/`
- `src/test/resources/manual-suite/`

The Java and Kotlin files live in real Maven test source roots so IntelliJ and VS Code pick them up as test sources. They are named `*Probe` and tagged with `@Tag("probe")` so they compile as part of test sources without joining the default green test run.

This suite follows the actual annotation API that compiles in `tabletest-examples`. That means:

- modern Java uses text blocks, fully-qualified modern annotations, and string-array tables, but not a `name = ...` attribute because the real annotation here does not define it
- Kotlin uses positional raw-string annotation arguments because the examples project does not expose a compile-safe Kotlin `name = ..., value = ...` form for `TableTest`
- legacy Java uses text blocks and Java-8-style multiline string concatenation rather than string-array tables because the legacy annotation here accepts a single string value

Open these files in this order:

1. `JavaTextBlockManualSuiteProbe.java`
2. `JavaStringArrayManualSuiteProbe.java`
3. `KotlinRawStringManualSuiteProbe.kt`
4. `LegacyJavaManualSuiteProbe.java`
5. `LegacyKotlinManualSuiteProbe.kt`
6. `manual-valid-unformatted.table`
7. `manual-unicode-width.table`
8. `manual-invalid-*.table` in numerical order

Manual verification checklist:

- Open each Java/Kotlin file and confirm TableTest highlighting activates immediately.
- Open each `.table` resource and confirm standalone table highlighting activates immediately.
- In every valid corpus, confirm there are no diagnostics.
- In every invalid corpus, confirm one diagnostic appears for the single bad table under each expectation comment.
- In the standalone invalid `.table` probes, confirm each file contains exactly one bad table and produces exactly one diagnostic.
- In `manual-unicode-width.table`, run format document and check where the second column's trailing pipe lands for each row. This file is specifically for comparing Unicode width behaviour across accented Latin, combining marks, CJK, Hangul, emoji, flags, keycaps, and ZWJ sequences.
- Run format document on Java text blocks, Java string arrays, Kotlin raw strings, `manual-valid-unformatted.table`, and a few representative `manual-invalid-*.table` files.
- In `manual-valid-unformatted.table`, also run format selection on just one table and confirm only that table changes.
- Confirm formatting fixes row indentation, column alignment, comment alignment, and collection normalisation without changing quoted content.
- Confirm supported annotation variants activate while `@Other`, `@TableTestX`, and annotation-like text in comments/strings remain inactive.
- Run formatting a second time on already formatted content and confirm there are no further visible changes.
- Check one light theme and one dark theme to confirm the token colours remain readable.
