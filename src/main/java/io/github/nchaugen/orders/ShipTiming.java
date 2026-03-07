package io.github.nchaugen.orders;

public enum ShipTiming {
    SHIP_NOW,       // In stock, ready to ship
    SHIP_LATER,     // Pre-order or backorder
    PICKUP          // BOPIS - customer pickup
}
