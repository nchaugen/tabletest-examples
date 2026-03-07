package io.github.nchaugen.examples.legacy;

import io.github.nchaugen.tabletest.junit.TableTest;
import org.tabletest.junit.TypeConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegacyCustomConverterExampleTest {

    @TableTest("""
        A | B | Sum?
        1 | 2 | 3
        4 | 5 | 9
        """)
    void shouldApplyCustomNumberConverter(Long a, Long b, Long expectedSum) {
        assertEquals(expectedSum, a + b);
    }

    @TypeConverter
    public static Long parseLongValue(String input) {
        return Long.parseLong(input);
    }
}
