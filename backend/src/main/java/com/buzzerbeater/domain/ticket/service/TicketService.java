package com.buzzerbeater.domain.ticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.game.repository.GameRepository;
import com.buzzerbeater.domain.ticket.dto.TicketDto;
import com.buzzerbeater.domain.ticket.entity.Ticket;
import com.buzzerbeater.domain.ticket.repository.TicketRepository;
import com.buzzerbeater.domain.user.entity.User;
import com.buzzerbeater.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

    private final TicketRepository ticketRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    /**
     * 티켓 예매
     */
    @Transactional
    public Long bookTicket(TicketDto.CreateTicketRequest request) {
        // 1. 경기 조회
        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));

        // 2. 사용자 조회
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 3. 좌석 중복 확인
        ticketRepository.findByGameAndSeatNumber(game, request.seatNumber())
                .ifPresent(t -> {
                    throw new IllegalArgumentException("이미 예약된 좌석입니다: " + request.seatNumber());
                });

        // 4. 티켓 생성 및 저장
        Ticket ticket = Ticket.builder()
                .game(game)
                .user(user)
                .seatNumber(request.seatNumber())
                .ticketCount(1)
                .build();

        return ticketRepository.save(ticket).getId();
    }
}