package com.board.demo.service;

import com.board.demo.dto.LoginDto;
import com.board.demo.entity.User;
import com.board.demo.dto.UserDto;
import com.board.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
@Transactional
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto fromEntity(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserId(user.getUserId());
        userDto.setUserPw(user.getUserPw());
        return userDto;
    }

    public String checkValidCondition(UserDto userDto) {
        //'UserDto' 타입의 객체를 메소드의 인자로 받아서 안에서 필요한 것만 추출하며 유효성 검사 실시
        String userId = userDto.getUserId();
        String userPw = userDto.getUserPw();

        //사용자 id 중복 확인
        User found = userRepository.findByUserId(userDto.getUserId());
        if (found != null) {
            return "중복된 사용자 id가 존재합니다.";
        }

        String idPattern = "^[a-z0-9]*$"; //영문 소문자, 0~9
        String pwPattern = "^[a-zA-Z0-9]*$"; //영문 대문자, 영문 소문자, 0~9
        if (!(matches(idPattern, userId))) {
            return "아이디는 영문 소문자와 숫자로만 이루어져야합니다.";
        } else if (!(userId.length() >= 4) || !(userId.length() <= 10)) {
            return "아이디는 4자 이상, 10자 이하로 이루어져야합니다.";
        } else if (!(matches(pwPattern, userPw))) {
            return "비밀번호는 영문 대소문자와 숫자로만 이루어져야합니다.";
        } else if (!(userPw.length() >= 8) || !(userPw.length() <= 15)) {
            return "비밀번호는 8자 이상, 15자 이하로 이루어져야합니다.";
        }
        return null;
    }

    public ResponseEntity<?> register(UserDto userDto){
        String validResponse = checkValidCondition(userDto);
        if (validResponse != null){
           return ResponseEntity.badRequest().body(validResponse);
        }
        String password = userDto.getUserPw();

        User user = new User();

        user.setUserId(userDto.getUserId());
        user.setUserPw(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        userDto = fromEntity(user);
        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<?> login(LoginDto loginDto) {
        String id = loginDto.getUserId();
        String pw = loginDto.getUserPw();
        User user = userRepository.findByUserId(id);
        if (user != null) {
            String userPassword = user.getUserPw();
            if (pw.equals(userPassword)) return ResponseEntity.ok(loginDto);
        }
        return ResponseEntity.badRequest().body("no!!!");
    }
}
