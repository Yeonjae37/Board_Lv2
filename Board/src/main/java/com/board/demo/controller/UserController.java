package com.board.demo.controller;

import com.board.demo.dto.LoginDto;
import com.board.demo.service.LoginService;
import com.board.demo.service.UserService;
import com.board.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/users")  // URL 경로 구조화
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    public UserController(UserService userService, LoginService loginService){
        this.userService = userService;
        this.loginService = loginService;
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        return userService.register(userDto);
    }

    /*@PostMapping("/login")
    public void login(@RequestBody LoginDto loginDto) {
        loginService.loadUserByUsername(loginDto.getuserId());
    }*/
}
