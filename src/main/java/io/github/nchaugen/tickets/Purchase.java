package io.github.nchaugen.tickets;

import java.time.LocalDateTime;

public record Purchase(
    LocalDateTime timeOfPurchase,
    TicketType ticketType,
    Discount discount,
    Price purchasePrice,
    Price ticketFaceValue
) {
}
