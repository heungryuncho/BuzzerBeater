package com.buzzerbeater.domain.user.dto;

import com.buzzerbeater.domain.user.entity.Role;
import com.buzzerbeater.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserJoinRequest(
        @NotBlank(message = "이메일은 필수 입력 값입니다.") @Email(message = "이메일 형식이 올바르지 않습니다.") String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.") String password,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.") String nickname) {
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }
}
