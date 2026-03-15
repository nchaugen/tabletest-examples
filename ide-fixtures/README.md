This directory contains IDE and parser fixture files.

These files are intentionally kept outside `src/test/**` so they are not compiled or executed by Maven.

For manual IDE verification in real test source/resource roots, use:

- `src/test/java/io/github/nchaugen/examples/probe/manualsuite/`
- `src/test/kotlin/io/github/nchaugen/examples/probe/manualsuite/`
- `src/test/resources/manual-suite/`

Those files are the primary manual suite for checking that IntelliJ and VS Code activate TableTest functionality in normal test sources/resources.

Naming convention:
- File names describe the parsing/formatting feature under test.
- Probe class/method names describe the exact situation they demonstrate.

Current groups:
- `table-files/`: raw `.table` syntax fixtures (comments, quoting, unicode, collection literals, scalar corpora)
- `table-probes/`: Java probe files for parser/conversion exploration
- `kotlin-probes/`: Kotlin probe files for parser/converter lookup exploration
