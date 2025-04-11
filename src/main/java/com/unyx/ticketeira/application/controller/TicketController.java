package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.domain.model.TicketPurchase;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{ticketId}/purchase")
    public ResponseEntity<String> purchaseTicket(
            @PathVariable Long ticketId,
            @RequestParam Integer quantity,
            @AuthenticationPrincipal User buyer) {

        TicketPurchase purchase = ticketService.purchaseTicket(ticketId, quantity, buyer);

        return ResponseEntity.ok("Purchase successful! Purchase ID: " + purchase.getId());
    }
}
