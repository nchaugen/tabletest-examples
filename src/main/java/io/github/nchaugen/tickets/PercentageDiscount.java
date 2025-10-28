package io.github.nchaugen.tickets;

import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

public record PercentageDiscount(long percentage, BigDecimal discountMultiplier) implements Discount {

    public PercentageDiscount(long percentage) {
        BigDecimal percentageFraction = BigDecimal.valueOf(percentage).movePointLeft(2);
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(percentageFraction);
        this(percentage, discountMultiplier);
    }

    @Override
    public Price applyTo(Price price) {
        return new Price(price.amount().multiply(discountMultiplier), price.currency());
    }

    @Override
    public @NonNull String toString() {
        return percentage + "%";
    }
}
