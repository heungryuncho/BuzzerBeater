package com.buzzerbeater.domain.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buzzerbeater.domain.game.dto.GameDto;
import com.buzzerbeater.domain.game.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    // 경기 생성
    @PostMapping
    public ResponseEntity<Void> createGame(@RequestBody GameDto.CreateGameRequest request) {
        gameService.createGame(request);
        return ResponseEntity.ok().build();
    }

    // 특정 경기 조회
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto.GameResponse> getGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    // 전체 경기 목록 조회
    @GetMapping
    public ResponseEntity<List<GameDto.GameResponse>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }
}