package io.github.nchaugen.examples.probe.manualsuite

import io.github.nchaugen.tabletest.junit.TableTest
import org.junit.jupiter.api.Tag

@Tag("probe")
class LegacyKotlinManualSuiteProbe {

    @TableTest(
        """
        Scenario|Map literal|Result?
        legacy raw string|[plain: other]|active
        legacy bare comma scalar|World, hello|shown
        legacy bare colon scalar|key: value|shown
        quoted key with punctuation|["[a]{b}(c),|": "(works)"]|shown
        """
    )
    fun legacyRawStringShouldActivate() {
    }

    // expect diagnostic on next table: legacy invalid map key containing whitespace
    @TableTest(
        """
        Value|Expected?
        [key with spaces: value]|x
        """
    )
    fun legacyInvalidMapKeyShouldHighlight() {
    }

    // expect diagnostic on next table: legacy trailing comma
    @TableTest(
        """
        Value|Expected?
        [a, b,]|x
        """
    )
    fun legacyTrailingCommaShouldHighlight() {
    }
}
