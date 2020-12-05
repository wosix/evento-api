package com.wojtek.evento.controllers;

import com.wojtek.evento.dto.UserDto;
import com.wojtek.evento.model.User;
import com.wojtek.evento.repository.UserRepository;
import com.wojtek.evento.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/users/all")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users/add")
    public Long addUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }


}
