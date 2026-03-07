package io.github.nchaugen.orders;

public record CartItem(
    String productId,
    int quantity,
    FulfillmentType fulfillmentType,
    String deliveryAddress,        // null for BOPIS or default address
    String pickupLocationId,       // only for BOPIS
    boolean isPreOrder,
    String dependsOnProductId      // null if no dependency, otherwise productId of dependent item
) {
    // Convenience constructors
    public CartItem(String productId, int quantity, FulfillmentType fulfillmentType) {
        this(productId, quantity, fulfillmentType, null, null, false, null);
    }

    public CartItem(String productId, int quantity, FulfillmentType fulfillmentType, String pickupLocationId) {
        this(productId, quantity, fulfillmentType, null, pickupLocationId, false, null);
    }
}
