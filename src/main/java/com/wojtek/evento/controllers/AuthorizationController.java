package com.wojtek.evento.controllers;

import com.wojtek.evento.dto.UserDto;
import com.wojtek.evento.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final UserService userService;

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
}
