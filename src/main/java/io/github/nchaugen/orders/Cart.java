package io.github.nchaugen.orders;

import java.util.List;

public record Cart(
    String cartId,
    List<CartItem> items
) {
    public Cart(List<CartItem> items) {
        this(null, items);
    }
}