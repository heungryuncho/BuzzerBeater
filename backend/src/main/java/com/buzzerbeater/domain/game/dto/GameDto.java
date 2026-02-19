package com.buzzerbeater.domain.game.dto;

import java.time.LocalDateTime;

import com.buzzerbeater.domain.game.entity.Game;

public class GameDto {
    // 1. 경기 생성 요청
    public record CreateGameRequest(
            String title,
            String homeTeam,
            String awayTeam,
            String stadium,
            Integer maxSeats,
            LocalDateTime startTime) {
        // DTO -> Entity 변환 메서드
        public Game toEntity() {
            return Game.builder()
                    .title(title)
                    .homeTeam(homeTeam)
                    .awayTeam(awayTeam)
                    .stadium(stadium)
                    .maxSeats(maxSeats)
                    .startTime(startTime)
                    .build();
        }
    }

    // 2. 경기 조회 응답 ()
    public record GameResponse(
            Long id,
            String title,
            String homeTeam,
            String awayTeam,
            String stadium,
            Integer maxSeats,
            LocalDateTime startTime) {
        // Entity -> DTO 변환 메서드
        public static GameResponse from(Game game) {
            return new GameResponse(
                    game.getId(),
                    game.getTitle(),
                    game.getHomeTeam(),
                    game.getAwayTeam(),
                    game.getStadium(),
                    game.getMaxSeats(),
                    game.getStartTime());
        }
    }
}
