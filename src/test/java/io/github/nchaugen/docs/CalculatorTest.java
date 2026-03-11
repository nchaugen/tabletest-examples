package io.github.nchaugen.docs;

import org.tabletest.junit.TableTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();
    private int counter = 0;

    @TableTest("""
        Scenario              | A  | B  | Sum?
        Two positive numbers  | 2  | 3  | 5
        Positive and negative | 5  | -3 | 2
        Two negative numbers  | -4 | -6 | -10
        Zero and positive     | 0  | 7  | 7
        """)
    public void shouldAddTwoIntegers(int a, int b, int expectedSum) {
        int result = calculator.add(a, b);
        assertEquals(expectedSum, result);
        assertEquals(1, ++counter);
    }

}
