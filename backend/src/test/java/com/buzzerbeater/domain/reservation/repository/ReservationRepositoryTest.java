package com.buzzerbeater.domain.reservation.repository;

import com.buzzerbeater.config.JpaAuditingConfig;
import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.game.repository.GameRepository;
import com.buzzerbeater.domain.ticket.entity.Ticket;
import com.buzzerbeater.domain.ticket.repository.TicketRepository;
import com.buzzerbeater.domain.user.entity.Role;
import com.buzzerbeater.domain.user.entity.User;
import com.buzzerbeater.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class ReservationRepositoryTest {

        @Autowired
        private TicketRepository reservationRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private GameRepository gameRepository;

        @Test
        @DisplayName("예약_저장_테스트")
        void saveReservation() {
                // given
                User user = User.builder()
                                .email("test@buzzerbeater.com")
                                .password("pw")
                                .nickname("tester")
                                .role(Role.USER)
                                .build();
                userRepository.save(user);

                Game game = Game.builder()
                                .title("Game 1")
                                .homeTeam("A")
                                .awayTeam("B")
                                .stadium("Stadium")
                                .maxSeats(100)
                                .startTime(LocalDateTime.now())
                                .build();
                gameRepository.save(game);

                Ticket reservation = Ticket.builder()
                                .user(user)
                                .game(game)
                                .seatNumber("A-1")
                                .ticketCount(2)
                                .reservationTime(LocalDateTime.now())
                                .build();

                // when
                Ticket savedReservation = reservationRepository.save(reservation);

                // then
                assertThat(savedReservation.getId()).isNotNull();
                assertThat(savedReservation.getUser().getId()).isEqualTo(user.getId());
                assertThat(savedReservation.getGame().getId()).isEqualTo(game.getId());
                assertThat(savedReservation.getTicketCount()).isEqualTo(2);
        }

        @Test
        @DisplayName("회원으로_예약_조회")
        void findByUser() {
                // given
                User user = User.builder()
                                .email("user@test.com")
                                .password("pw")
                                .nickname("user")
                                .role(Role.USER)
                                .build();
                userRepository.save(user);

                Game game = Game.builder()
                                .title("Game 2")
                                .homeTeam("C")
                                .awayTeam("D")
                                .stadium("Stadium")
                                .maxSeats(100)
                                .startTime(LocalDateTime.now())
                                .build();
                gameRepository.save(game);

                Ticket reservation = Ticket.builder()
                                .user(user)
                                .game(game)
                                .seatNumber("B-1")
                                .ticketCount(1)
                                .reservationTime(LocalDateTime.now())
                                .build();
                reservationRepository.save(reservation);

                // when
                List<Ticket> reservations = reservationRepository.findByUser(user);

                // then
                assertThat(reservations).hasSize(1);
                assertThat(reservations.get(0).getSeatNumber()).isEqualTo("B-1");
        }
}
