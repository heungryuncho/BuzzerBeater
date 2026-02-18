package com.buzzerbeater.domain.user.service;

import com.buzzerbeater.domain.user.dto.UserJoinRequest;
import com.buzzerbeater.domain.user.dto.UserResponse;
import com.buzzerbeater.domain.user.entity.User;
import com.buzzerbeater.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserJoinRequest request) {
        validateDuplicateUser(request.email());
        User user = request.toEntity(passwordEncoder);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    private void validateDuplicateUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(u -> {
                    throw new IllegalArgumentException("이미 가입된 회원입니다.");
                });
    }
}
