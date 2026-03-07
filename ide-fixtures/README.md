This directory contains IDE and parser fixture files.

These files are intentionally kept outside `src/test/**` so they are not compiled or executed by Maven.

Naming convention:
- File names describe the parsing/formatting feature under test.
- Probe class/method names describe the exact situation they demonstrate.

Current groups:
- `table-files/`: raw `.table` syntax fixtures (comments, quoting, unicode, collection literals, scalar corpora)
- `table-probes/`: Java probe files for parser/conversion exploration
- `kotlin-probes/`: Kotlin probe files for parser/converter lookup exploration
