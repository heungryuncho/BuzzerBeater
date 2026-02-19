package com.buzzerbeater.domain.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buzzerbeater.domain.ticket.dto.TicketDto;
import com.buzzerbeater.domain.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Void> bookTicket(@RequestBody TicketDto.CreateTicketRequest request) {
        ticketService.bookTicket(request);
        return ResponseEntity.ok().build();
    }
}
