package com.buzzerbeater.domain.ticket.repository;

import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.ticket.entity.Ticket;
import com.buzzerbeater.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // 특정 사용자의 예매 내역 조회
    List<Ticket> findByUser(User user);

    // 특정 경기의 예매 내역 조회
    List<Ticket> findByGame(Game game);

    // 특정 경기 + 좌석에 대한 티켓 조회 (중복 예약 방지)
    Optional<Ticket> findByGameAndSeatNumber(Game game, String seatNumber);
}
