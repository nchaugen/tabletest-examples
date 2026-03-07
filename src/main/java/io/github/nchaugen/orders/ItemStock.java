package io.github.nchaugen.orders;

import java.time.LocalDate;

/**
 * @param availableDate null if available now, future date for pre-orders
 */
public record ItemStock(
    String productId,
    String locationId,
    int quantityAvailable,
    LocalDate availableDate
) {
    public ItemStock(String productId, String locationId, int quantityAvailable) {
        this(productId, locationId, quantityAvailable, null);
    }

    public boolean isAvailableNow() {
        return availableDate == null || !availableDate.isAfter(LocalDate.now());
    }
}
