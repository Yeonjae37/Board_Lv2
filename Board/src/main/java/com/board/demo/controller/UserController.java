package com.board.demo.controller;

import com.board.demo.service.UserService;
import com.board.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/user")  // URL 경로 구조화
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // Login 을 위한 HTML 파일 내려주기
    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @GetMapping("/joinUser")
    public String createMemberForm() {
        return "joinUser";
    }
    @PostMapping("/joinUser")
    public String userJoin(@ModelAttribute UserDto userDto){
        if (userService.join(userDto)){
            return "index";
        }
        else {
            return "joinFail";
        }
    }

    @GetMapping("/loginUser")
    public String createLoginForm() {
        return "loginUser";
    }
    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("userId") String userId, @RequestParam("userPw") String userPw) {
        if (userService.login(userId, userPw)){
            return "boards";
        } else {
            return "loginFail";
        }
    }

    @DeleteMapping("/drop")
    public void userDrop() {

    }
}
