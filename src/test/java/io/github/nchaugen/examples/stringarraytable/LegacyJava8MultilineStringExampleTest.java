package io.github.nchaugen.examples.stringarraytable;

import io.github.nchaugen.tabletest.junit.TableTest;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("snapshot")
public class LegacyJava8MultilineStringExampleTest {

    @TableTest(
            "Scenario                     | Left operand | Right operand | Sum?\n" +
            "Two positive numbers         | 2            | 3             | 5\n" +
            "Negative and positive number | -4           | 7             | 3\n" +
            "Zero and positive number     | 0            | 9             | 9")
    void shouldKeepLegacyAnnotationUsableWithJava8StringConcatenation(int leftOperand, int rightOperand, int expectedSum) {
        assertEquals(expectedSum, leftOperand + rightOperand);
    }
}
