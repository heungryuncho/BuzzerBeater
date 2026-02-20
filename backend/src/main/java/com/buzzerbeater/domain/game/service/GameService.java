package com.buzzerbeater.domain.game.service;

import org.springframework.stereotype.Service;
import com.buzzerbeater.domain.game.exception.GameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.game.repository.GameRepository;

import lombok.RequiredArgsConstructor;

import com.buzzerbeater.domain.game.dto.GameDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;

    // 경기 생성
    @Transactional
    public Long createGame(GameDto.CreateGameRequest request) {
        Game game = request.toEntity();
        Game savedGame = gameRepository.save(game);
        return savedGame.getId();
    }

    // 특정 경기 조회
    public GameDto.GameResponse getGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(GameNotFoundException::new);
        return GameDto.GameResponse.from(game);
    }

    // 전체 경기 목록 조회
    public List<GameDto.GameResponse> getAllGames() {
        return gameRepository.findAll().stream()
                .map(GameDto.GameResponse::from)
                .toList();
    }

}
