package com.wojtek.evento.controllers;

import com.wojtek.evento.dto.AuthResponse;
import com.wojtek.evento.dto.RefreshTokenRequest;
import com.wojtek.evento.dto.UserDto;
import com.wojtek.evento.model.RefreshToken;
import com.wojtek.evento.model.User;
import com.wojtek.evento.service.RefreshTokenService;
import com.wojtek.evento.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserAuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return new ResponseEntity<>("user registration success", HttpStatus.OK);
    }

    @GetMapping("accountVerify/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>("verify success", HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping("/add")
    public Long addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @GetMapping("/cur")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/refToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("refresh token deleted");
    }

}
