package io.github.nchaugen.tickets;

import io.github.nchaugen.tabletest.junit.TableTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountPurchasesWithRelativeTimestampsTest {

    /*
     * Syntax for time of past purchases:
     * - T-60d = 60 days ago
     * - T-30d1s = 30 days and 1 second ago
     * - T-30d1h2m3s = 30 days and 1 hour, 2 minutes and 3 seconds ago
     */
    @TableTest("""
        Scenario              | Time of past purchases | Count last 30 days?
        No purchases          | []                     | 0
        Purchase too old      | [T-60d]                | 0
        Purchase just inside  | [T-29d23h59m59s]       | 1
        Purchase just outside | [T-30d]                | 0
        All purchases inside  | [T-28d, T-27d]         | 2
        One inside, one not   | [T-45d, T-15d]         | 1
        """)
    void count_purchases_last_30_days_with_relative_timestamps(
        PurchaseHistory purchases,
        int expectedCount
    ) {
        assertEquals(
            expectedCount,
            LevelledDiscount.countPurchasesPast30Days(TIME_NOW, purchases)
        );
    }

    private static final LocalDateTime TIME_NOW = LocalDateTime.now();
    private static final RelativeTimestampParser TIMESTAMP_PARSER = new RelativeTimestampParser(TIME_NOW);

    @SuppressWarnings("unused")
    public static LocalDateTime parseRelativeTimestamp(String input) {
        return TIMESTAMP_PARSER.parse(input);
    }

    @SuppressWarnings("unused")
    public static PurchaseHistory createPurchaseHistory(List<Purchase> purchases) {
        return new PurchaseHistory(purchases);
    }

    @SuppressWarnings("unused")
    public static Purchase createPurchase(LocalDateTime purchaseTimestamp) {
        return new Purchase(
            purchaseTimestamp,
            TicketType.SINGLE,
            Discount.NONE,
            new Price(BigDecimal.ONE, Currency.getInstance("NOK")),
            new Price(BigDecimal.ONE, Currency.getInstance("NOK"))
        );
    }

}
