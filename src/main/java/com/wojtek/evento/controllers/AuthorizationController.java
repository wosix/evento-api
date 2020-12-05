package com.wojtek.evento.controllers;

import com.wojtek.evento.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {

    @PostMapping("/signup")
    public void signUp(@RequestBody UserDto userDto) {

    }

}
