package com.buzzerbeater.domain.ticket.service;

import org.springframework.stereotype.Service;

import com.buzzerbeater.domain.game.exception.GameNotFoundException;
import com.buzzerbeater.domain.ticket.exception.SeatAlreadyBookedException;
import com.buzzerbeater.domain.user.exception.UserNotFoundException;
import com.buzzerbeater.domain.ticket.exception.TicketNotFoundException;
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
                .orElseThrow(GameNotFoundException::new);

        // 2. 사용자 조회
        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

        // 3. 좌석 중복 확인
        // 해당 경기의 해당 좌석이 이미 예매되었는지 확인
        boolean isBooked = ticketRepository.existsByGameAndSeatNumber(game, request.seatNumber());
        if (isBooked) {
            throw new SeatAlreadyBookedException();
        }

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