package io.github.nchaugen.tickets;

public interface Discount {
    Price applyTo(Price price);

    Discount NONE = (price) -> price;
}
