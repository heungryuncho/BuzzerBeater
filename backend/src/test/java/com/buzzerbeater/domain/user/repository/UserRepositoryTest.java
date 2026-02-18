package com.buzzerbeater.domain.user.repository;

import com.buzzerbeater.config.JpaAuditingConfig;
import com.buzzerbeater.domain.user.entity.Role;
import com.buzzerbeater.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class) // Auditing 설정 import
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입_및_Auditing_테스트")
    void saveUser() {
        // given
        String email = "test@buzzerbeater.com";
        String nickname = "michael_jordan";

        User user = User.builder()
                .email(email)
                .password("password123") // 실제로는 암호화해야 함
                .nickname(nickname)
                .role(Role.USER)
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getNickname()).isEqualTo(nickname);
        assertThat(savedUser.getRole()).isEqualTo(Role.USER);

        // Auditing 확인
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getUpdatedAt()).isNotNull();

    }
}
