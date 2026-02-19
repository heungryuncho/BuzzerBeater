package com.buzzerbeater.domain.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.buzzerbeater.domain.game.entity.Game;
import com.buzzerbeater.domain.game.repository.GameRepository;
import com.buzzerbeater.domain.ticket.dto.TicketDto;
import com.buzzerbeater.domain.ticket.repository.TicketRepository;
import com.buzzerbeater.domain.user.entity.Role;
import com.buzzerbeater.domain.user.entity.User;
import com.buzzerbeater.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional // 테스트가 끝나면 데이터가 롤백(삭제)됩니다.
class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("정상적으로 티켓을 예매할 수 있다.")
    void bookTicket_success() {
        // 1. 테스트를 위한 데이터 준비 (Given)
        // 사용자 생성
        User user = User.builder()
                .email("test@test.com")
                .password("password")
                .nickname("tester")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // 경기 생성
        Game game = Game.builder()
                .title("KBL Final")
                .homeTeam("Home")
                .awayTeam("Away")
                .stadium("Gym")
                .maxSeats(100)
                .startTime(LocalDateTime.now().plusDays(1))
                .build();
        gameRepository.save(game);

        // 예매 요청 객체 생성
        TicketDto.CreateTicketRequest request = new TicketDto.CreateTicketRequest(
                game.getId(),
                user.getId(),
                "A-1" // A구역 1번 좌석
        );

        // 2. 실제 예매 실행 (When)
        Long ticketId = ticketService.bookTicket(request);

        // 3. 결과 검증 (Then)
        // 티켓 ID가 존재해야 한다.
        assertThat(ticketId).isNotNull();
        // 실제 DB에 저장되었는지 확인
        assertThat(ticketRepository.findByGameAndSeatNumber(game, "A-1")).isPresent();

        System.out.println("✅ 테스트 성공: 예매가 정상적으로 완료되었습니다!");
    }

    @Test
    @DisplayName("이미 예약된 좌석은 예매할 수 없다.")
    void bookTicket_fail_duplicate() {
        // 1. 데이터 준비 (Given)
        User user1 = userRepository
                .save(User.builder().email("user1@test.com").password("pw").nickname("u1").role(Role.USER).build());
        User user2 = userRepository
                .save(User.builder().email("user2@test.com").password("pw").nickname("u2").role(Role.USER).build());

        Game game = gameRepository.save(Game.builder()
                .title("Match")
                .homeTeam("H")
                .awayTeam("A")
                .stadium("S")
                .maxSeats(100)
                .startTime(LocalDateTime.now())
                .build());

        // user1이 먼저 "B-1" 좌석을 예매함
        ticketService.bookTicket(new TicketDto.CreateTicketRequest(game.getId(), user1.getId(), "B-1"));

        // 2. user2가 같은 "B-1" 좌석을 예매 시도 (When)
        TicketDto.CreateTicketRequest request2 = new TicketDto.CreateTicketRequest(game.getId(), user2.getId(), "B-1");

        // 3. 검증 (Then)
        // 예외가 발생해야 한다.
        assertThrows(IllegalArgumentException.class, () -> {
            ticketService.bookTicket(request2);
        });

        System.out.println("✅ 테스트 성공: 중복 예매가 정상적으로 막혔습니다!");
    }
}
