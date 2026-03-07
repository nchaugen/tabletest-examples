package io.github.nchaugen.tickets;

import java.time.LocalDateTime;

public class TieredDiscount implements DiscountStrategy {

    // Business constants to avoid magic numbers
    private static final int WINDOW_DAYS = 30;               // Lookback window in days
    private static final int PURCHASES_PER_TIER = 5;         // Each tier spans 5 purchases
    private static final int PERCENT_PER_TIER = 5;           // Each tier adds 5 percentage points
    private static final int MAX_PERCENT = 40;               // Cap for the discount percentage

    @Override
    public PercentageDiscount calculate(
        TicketType ticketType,
        LocalDateTime purchaseTime,
        PurchaseHistory purchaseHistory
    ) {
        long purchasesLast30Days = countPurchasesPast30Days(purchaseTime, purchaseHistory);
        long percentage = calculateDiscountPercentage(purchasesLast30Days);
        return new PercentageDiscount(percentage);
    }

    static long countPurchasesPast30Days(LocalDateTime purchaseTime, PurchaseHistory purchaseHistory) {
        // Boundary note: PurchaseHistory.countPurchasesSince uses isAfter, so exactly at cutoff is excluded
        LocalDateTime fromTime = purchaseTime.minusDays(WINDOW_DAYS);
        return purchaseHistory.countPurchasesSince(fromTime);
    }
    
    static long calculateDiscountPercentage(long purchasesLast30Days) {
        // Each full block of PURCHASES_PER_TIER starting at 4 purchases grants PERCENT_PER_TIER.
        // Using floorDiv(purchasesLast30Days + 1, PURCHASES_PER_TIER) yields:
        // 0..3 -> 0; 4..8 -> 1; 9..13 -> 2; ...
        long steps = Math.floorDiv(purchasesLast30Days + 1, PURCHASES_PER_TIER);
        long percentage = steps * PERCENT_PER_TIER;
        return Math.min(MAX_PERCENT, percentage);
    }
}
