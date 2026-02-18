package com.buzzerbeater.domain.game.repository;

import com.buzzerbeater.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByStartTimeAfter(LocalDateTime now);
}
