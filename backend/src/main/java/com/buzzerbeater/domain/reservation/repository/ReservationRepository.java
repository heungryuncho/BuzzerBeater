package com.buzzerbeater.domain.reservation.repository;

import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.reservation.entity.Reservation;
import com.buzzerbeater.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    List<Reservation> findByGame(Game game);
}
