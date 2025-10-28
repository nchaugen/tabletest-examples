package io.github.nchaugen.tickets;

import java.time.LocalDateTime;

public class LevelledDiscount implements DiscountStrategy {

    private static final int PURCHASES_HISTORY_WINDOW = 30;
    private static final int PURCHASES_PER_LEVEL = 5;
    private static final int DISCOUNT_STEP = 5;
    private static final int MAX_DISCOUNT = 40;

    @Override
    public PercentageDiscount calculate(
        TicketType ticketType,
        LocalDateTime purchaseTime,
        PurchaseHistory purchaseHistory
    ) {
        return new PercentageDiscount(
            calculateDiscountPercentage(
                countPurchasesPast30Days(purchaseTime, purchaseHistory)
            )
        );
    }

    static long countPurchasesPast30Days(LocalDateTime purchaseTime, PurchaseHistory purchaseHistory) {
        return purchaseHistory.countPurchasesSince(startOfPurchaseWindow(purchaseTime));
    }

    static LocalDateTime startOfPurchaseWindow(LocalDateTime purchaseTime) {
        return purchaseTime.minusDays(PURCHASES_HISTORY_WINDOW);
    }

    static long calculateDiscountPercentage(long purchasesLast30Days) {
        return Math.min(calculateDiscountLevel(purchasesLast30Days) * DISCOUNT_STEP, MAX_DISCOUNT);
    }

    static long calculateDiscountLevel(long purchasesLast30Days) {
        return (purchasesLast30Days + 1) / PURCHASES_PER_LEVEL;
    }

}
