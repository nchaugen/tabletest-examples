package io.github.nchaugen.examples.probe.manualsuite;

import io.github.nchaugen.tabletest.junit.TableTest;
import org.junit.jupiter.api.Tag;

@Tag("probe")
public class LegacyJavaManualSuiteProbe {

    @TableTest("""
            Scenario|Map literal|Result?
            legacy text block|[plain: other]|active
            quoted key with punctuation|["[a]{b}(c),|": "(works)"]|shown
            """)
    void legacyTextBlockShouldActivate() {
    }

    @TableTest(
            "Scenario|Map literal|Result?\n" +
            "legacy java8 string concatenation|[plain: other]|active\n" +
            "legacy bare comma scalar|World, hello|shown\n" +
            "legacy bare colon scalar|key: value|shown\n" +
            "legacy quoted key|['[s]u(c)c{e}s[s': 3]|shown")
    void legacyJava8MultilineStringShouldActivate() {
    }

    // expect diagnostic on next table: legacy invalid map key containing whitespace
    @TableTest("""
            Value|Expected?
            [key with spaces: value]|x
            """)
    void legacyInvalidMapKeyShouldHighlight() {
    }

    // expect diagnostic on next table: legacy trailing comma
    @TableTest("""
            Value|Expected?
            [a, b,]|x
            """)
    void legacyTrailingCommaShouldHighlight() {
    }
}
