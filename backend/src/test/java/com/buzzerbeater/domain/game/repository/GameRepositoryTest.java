package com.buzzerbeater.domain.game.repository;

import com.buzzerbeater.config.JpaAuditingConfig;
import com.buzzerbeater.domain.game.entity.Game;
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
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("경기 저장 및 조회 테스트")
    void saveGame() {
        // given
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        Game game = Game.builder()
                .title("KBL Finals Game 1")
                .homeTeam("Team A")
                .awayTeam("Team B")
                .stadium("Jamsil Arena")
                .maxSeats(5000)
                .startTime(startTime)
                .build();

        // when
        Game savedGame = gameRepository.save(game);

        // then
        assertThat(savedGame.getId()).isNotNull();
        assertThat(savedGame.getTitle()).isEqualTo("KBL Finals Game 1");
        assertThat(savedGame.getStartTime()).isEqualTo(startTime);
    }

    @Test
    @DisplayName("시작 시간 이후 경기 조회 테스트")
    void findByStartTimeAfter() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Game game1 = Game.builder()
                .title("Future Game")
                .homeTeam("A")
                .awayTeam("B")
                .stadium("Stadium")
                .maxSeats(100)
                .startTime(now.plusHours(2))
                .build();

        Game game2 = Game.builder()
                .title("Past Game")
                .homeTeam("C")
                .awayTeam("D")
                .stadium("Stadium")
                .maxSeats(100)
                .startTime(now.minusHours(2))
                .build();

        gameRepository.save(game1);
        gameRepository.save(game2);

        // when
        List<Game> games = gameRepository.findByStartTimeAfter(now);

        // then
        assertThat(games).hasSize(1);
        assertThat(games.get(0).getTitle()).isEqualTo("Future Game");
    }
}
