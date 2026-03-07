package io.github.nchaugen.orders;

public record OrderLine(
    String productId,
    int quantity,
    String sourceLocationId
) {
}
