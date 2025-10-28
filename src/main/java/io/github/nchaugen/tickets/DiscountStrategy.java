package io.github.nchaugen.tickets;

import java.time.LocalDateTime;

public interface DiscountStrategy {

    Discount calculate(
        TicketType ticketType,
        LocalDateTime purchaseTime,
        PurchaseHistory purchaseHistory
    );

}
