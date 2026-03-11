package io.github.nchaugen.tickets;

import org.tabletest.junit.Description;
import org.tabletest.junit.TableTest;
import org.tabletest.junit.TypeConverter;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountPurchasesWithRelativeTimestampsTest {

    @DisplayName("Count discount qualifying purchases")
    @Description("""
        The discount level is based on the number of qualifying purchases within the last 30 days.
        Given a list of past purchases, calculate how many of them count toward the discount tier of
        the next purchase.
        
        In the table, past purchases are expressed by their time of purchase relative to the current
        time, using the following syntax:
        * T-60d = 60 days ago
        * T-30d1s = 30 days and 1 second ago
        * T-30d1h2m3s = 30 days and 1 hour, 2 minutes and 3 seconds ago
        """)
    @TableTest("""
        Scenario                         | Time of past purchases | Discount qualifying count?
        No previous purchases            | []                     | 0
        Purchase way outside window      | [T-60d]                | 0
        Purchase just outside window     | [T-30d01s]             | 0
        Purchase just inside window      | [T-30d]                | 1
        Multiple purchases inside window | [T-28d, T-27d]         | 2
        One inside, one not              | [T-45d, T-15d]         | 1
        """)
    void shouldCountDiscountQualifyingPurchases(
        PurchaseHistory purchases,
        int expectedCount
    ) {
        assertEquals(
            expectedCount,
            TieredDiscount.countPurchasesPast30Days(TIME_NOW, purchases)
        );
    }

    private static final LocalDateTime TIME_NOW = LocalDateTime.now();
    private static final RelativeTimestampParser TIMESTAMP_PARSER = new RelativeTimestampParser(TIME_NOW);

    @TypeConverter
    public static LocalDateTime parseRelativeTimestamp(String input) {
        return TIMESTAMP_PARSER.parse(input);
    }

    @TypeConverter
    public static PurchaseHistory createPurchaseHistory(List<Purchase> purchases) {
        return new PurchaseHistory(purchases);
    }

    @TypeConverter
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
