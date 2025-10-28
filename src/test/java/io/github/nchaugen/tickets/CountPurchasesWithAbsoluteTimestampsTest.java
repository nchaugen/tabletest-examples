package io.github.nchaugen.tickets;

import io.github.nchaugen.tabletest.junit.TableTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountPurchasesWithAbsoluteTimestampsTest {

    @TableTest("""
        Scenario              | Time now            | Purchases                                      | Count?
        No purchases          | 2025-09-30T23:59:59 | []                                             | 0
        Purchase too old      | 2025-09-30T23:59:59 | ["2025-08-01T00:00:00"]                        | 0
        Purchase just inside  | 2025-09-30T23:59:59 | ["2025-09-01T00:00:00"]                        | 1
        Purchase just outside | 2025-09-30T23:59:59 | ["2025-08-31T23:59:59"]                        | 0
        All purchases inside  | 2025-09-30T23:59:59 | ["2025-09-02T00:00:00", "2025-09-03T00:00:00"] | 2
        One inside, one not   | 2025-09-30T23:59:59 | ["2025-08-02T00:00:00", "2025-09-03T00:00:00"] | 1
        """)
    void count_purchases_last_30_days_with_absolute_timestamps(
        LocalDateTime timeNow,
        PurchaseHistory purchases,
        int expectedCount
    ) {
        assertEquals(
            expectedCount,
            LevelledDiscount.countPurchasesPast30Days(timeNow, purchases)
        );
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
