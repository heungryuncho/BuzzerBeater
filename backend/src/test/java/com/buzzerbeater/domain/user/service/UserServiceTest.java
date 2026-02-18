package com.buzzerbeater.domain.user.service;

import com.buzzerbeater.domain.user.dto.UserJoinRequest;
import com.buzzerbeater.domain.user.entity.Role;
import com.buzzerbeater.domain.user.entity.User;
import com.buzzerbeater.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입_성공")
    void join_success() {
        // given
        UserJoinRequest request = new UserJoinRequest("test@test.com", "password", "nickname");
        User user = request.toEntity(passwordEncoder);

        // Mocking
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());
        given(passwordEncoder.encode(any())).willReturn("encodedPassword");
        given(userRepository.save(any())).willReturn(User.builder()
                .email(request.email())
                .password("encodedPassword")
                .nickname(request.nickname())
                .role(Role.USER)
                .build());

        // when
        userService.join(request);

        // then
        // 에러가 발생하지 않으면 성공
    }

    @Test
    @DisplayName("회원가입_실패_중복이메일")
    void join_fail_duplicate() {
        // given
        UserJoinRequest request = new UserJoinRequest("duplicate@test.com", "password", "nickname");

        // Mocking
        given(userRepository.findByEmail(any())).willReturn(Optional.of(User.builder().build()));

        // when & then
        assertThatThrownBy(() -> userService.join(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 회원입니다.");
    }
}
