package io.github.nchaugen.tickets;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseHistory(List<Purchase> purchases) {

    public long countPurchasesSince(LocalDateTime fromTime) {
        return purchases.stream()
            .filter(it -> it.timeOfPurchase().isAfter(fromTime))
            .count();
    }
}
