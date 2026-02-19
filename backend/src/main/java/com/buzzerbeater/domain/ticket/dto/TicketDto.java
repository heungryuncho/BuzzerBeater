package com.buzzerbeater.domain.ticket.dto;

import java.time.LocalDateTime;

import com.buzzerbeater.domain.ticket.entity.Ticket;

public record TicketDto() {

    // 예매 요청
    public record CreateTicketRequest(
            Long gameId,
            Long userId,
            String seatNumber) {
    }

    // 예매 응답
    public record TicketResponse(
            Long id,
            String gameTitle,
            String seatNumber,
            LocalDateTime ticketDate) {
        public static TicketResponse from(Ticket ticket) {
            return new TicketResponse(
                    ticket.getId(),
                    ticket.getGame().getTitle(),
                    ticket.getSeatNumber(),
                    ticket.getCreatedAt());
        }
    }
}
