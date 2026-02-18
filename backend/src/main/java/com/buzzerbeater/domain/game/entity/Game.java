package com.buzzerbeater.domain.game.entity;

import java.time.LocalDateTime;
import com.buzzerbeater.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "game")
public class Game extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String homeTeam;

    @Column(nullable = false)
    private String awayTeam;

    @Column(nullable = false)
    private String stadium;

    @Column(nullable = false)
    private Integer maxSeats;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Builder
    public Game(String title, String homeTeam, String awayTeam, String stadium, Integer maxSeats,
            LocalDateTime startTime) {
        this.title = title;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.maxSeats = maxSeats;
        this.startTime = startTime;
    }
}
