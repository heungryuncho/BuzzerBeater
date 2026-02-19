package com.buzzerbeater.domain.ticket.entity;

import java.time.LocalDateTime;
import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.buzzerbeater.domain.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String seatNumber;

    private Integer ticketCount;

    private LocalDateTime reservationTime;

    @Builder
    public Ticket(Game game, User user, String seatNumber, Integer ticketCount, LocalDateTime reservationTime) {
        this.game = game;
        this.user = user;
        this.seatNumber = seatNumber;
        this.ticketCount = ticketCount;
        this.reservationTime = reservationTime;
    }
}
