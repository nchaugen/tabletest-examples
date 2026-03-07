package io.github.nchaugen.orders;

import java.util.List;

public record Order(
    String orderId,
    List<OrderLine> lines,
    String sourceLocationId,      // Primary fulfillment location
    FulfillmentType fulfillmentType,
    String destinationAddress,
    ShipTiming shipTiming
) {
    // Convenience constructor for simple cases
    public Order(List<OrderLine> lines, String sourceLocationId, FulfillmentType fulfillmentType, ShipTiming shipTiming) {
        this(null, lines, sourceLocationId, fulfillmentType, null, shipTiming);
    }
}
