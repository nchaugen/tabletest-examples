package io.github.nchaugen.tickets;

import org.tabletest.junit.Description;
import org.tabletest.junit.TableTest;
import org.tabletest.junit.TypeConverter;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NextPurchaseDiscountTest {

    @DisplayName("Calculate Next Purchase Discount")
    @Description("""
        The discount for the next purchase is calculated based on the number of
        purchases within the last 30 days.
        """)
    @TableTest("""
        Scenario        | Purchases last 30 days  | Discount?
        No discount     | {0, 1, 2, 3}            | 0%
        Tier 1 discount | {4, 5, 6, 7, 8}         | 5%
        Tier 2 discount | {9, 10, 11, 12, 13}     | 10%
        Tier 3 discount | {14, 15, 16, 17, 18}    | 15%
        Tier 4 discount | {19, 20, 21, 22, 23}    | 20%
        Tier 5 discount | {24, 25, 26, 27, 28}    | 25%
        Tier 6 discount | {29, 30, 31, 32, 33}    | 30%
        Tier 7 discount | {34, 35, 36, 37, 38}    | 35%
        Caps at tier 8  | {39, 40, 50, 100, 1000} | 40%
        """)
    void shouldCalculateNextPurchaseDiscount(
        int purchasesLast30Days,
        PercentageDiscount expectedDiscount
    ) {
        assertEquals(
            expectedDiscount.percentage(),
            TieredDiscount.calculateDiscountPercentage(
                purchasesLast30Days
            )
        );
    }

    @TypeConverter
    public static PercentageDiscount parseDiscount(String input) {
        String digits = input.substring(0, input.length() - 1);
        return new PercentageDiscount(Integer.parseInt(digits));
    }

}
