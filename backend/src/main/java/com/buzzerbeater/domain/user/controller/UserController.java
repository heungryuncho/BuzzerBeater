package com.buzzerbeater.domain.user.controller;

import com.buzzerbeater.domain.user.dto.UserJoinRequest;
import com.buzzerbeater.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid UserJoinRequest request) {
        userService.join(request);
        return ResponseEntity.ok("회원가입 성공");
    }
}
