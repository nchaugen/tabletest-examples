package io.github.nchaugen.tickets;

import java.math.BigDecimal;

public record PercentageDiscount(long percentage, BigDecimal discountMultiplier) implements Discount {

    public PercentageDiscount(long percentage) {
        this(percentage, calculateDiscountMultiplier(percentage));
    }

    private static BigDecimal calculateDiscountMultiplier(long percentage) {
        BigDecimal percentageFraction = BigDecimal.valueOf(percentage).movePointLeft(2);
        return BigDecimal.ONE.subtract(percentageFraction);
    }

    @Override
    public Price applyTo(Price price) {
        return new Price(price.amount().multiply(discountMultiplier), price.currency());
    }

    @Override
    public String toString() {
        return percentage + "%";
    }
}
