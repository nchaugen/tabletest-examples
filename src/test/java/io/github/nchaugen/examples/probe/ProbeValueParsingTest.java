package io.github.nchaugen.examples.probe;

import org.junit.jupiter.api.Tag;
import org.tabletest.junit.TableTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("probe")
public class ProbeValueParsingTest {

    @TableTest("""
        Scenario                     | Input      | Length?
        Tab character from Java text | a\tb       | 3
        Escaped quote sequence       | Say \"hi\" | 8
        Backslash in value           | path\\file | 9
        Unicode escape sequence      | \u0041B    | 2
        """)
    void shouldParseValueEscapes(String input, int expectedLength) {
        assertEquals(expectedLength, input.length(), "Input: " + input);
    }
}
