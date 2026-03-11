package io.github.nchaugen.examples.stringarraytable;

import org.junit.jupiter.api.Tag;
import org.tabletest.junit.TableTest;
import org.tabletest.junit.TypeConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("snapshot")
public class ModernStringArrayTableExampleTest {

    @TableTest({
            "Scenario                     | Left operand | Right operand | Sum?",
            "Two positive numbers         | 2            | 3             | 5   ",
            "Negative and positive number | -4           | 7             | 3   ",
            "Zero and positive number     | 0            | 9             | 9   "
    })
    void shouldReadTableDefinedAsStringArray(int leftOperand, int rightOperand, int expectedSum) {
        assertEquals(expectedSum, leftOperand + rightOperand);
    }

    @TableTest({
            "Scenario              | Purchases in last 30 days | Discount?",
            "No discount           | 0                         | 0%       ",
            "Tier 1 discount       | 4                         | 5%       ",
            "Tier 2 discount       | 9                         | 10%      ",
            "Maximum tier discount | 40                        | 40%      "
    })
    void shouldSupportTypeConvertersWithStringArrayTables(int purchasesInLast30Days, int expectedDiscountPercentage) {
        int actualDiscountPercentage = Math.min(40, Math.floorDiv(purchasesInLast30Days + 1, 5) * 5);
        assertEquals(expectedDiscountPercentage, actualDiscountPercentage);
    }

    @TypeConverter
    public static int parseDiscountPercentage(String input) {
        if (input.endsWith("%")) {
            return Integer.parseInt(input.substring(0, input.length() - 1));
        }
        return Integer.parseInt(input);
    }
}
