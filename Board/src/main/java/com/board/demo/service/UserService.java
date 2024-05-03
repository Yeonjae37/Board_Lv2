package com.board.demo.service;

import com.board.demo.entity.User;
import com.board.demo.dto.UserDto;
import com.board.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.matches;

@Service
@RequiredArgsConstructor //private final UserRepository userRepository; 여기에 필요한건가봄
public class UserService {
    private final UserRepository userRepository;

    public boolean checkValidCondition(UserDto userDto){
        //'UserDto' 타입의 객체를 메소드의 인자로 받아서 안에서 필요한 것만 추출하며 유효성 검사 실시
        String userid = userDto.getUserId();
        String userpw = userDto.getUserPw();
        String userpwcheck = userDto.getUserPwCheck();

        boolean checkValid = true;
        String pattern = "^[a-zA-Z0-9]*$";
        if ( !(matches(pattern,userid) && userid.length() >= 4 && userid.length() <= 10)){
            System.out.println("아이디 형식이 잘못 되었습니다.");
            checkValid = false;
        }
        else if( !(matches(pattern,userpw) && userpw.length() >= 8 && userpw.length() <= 16)) {
            System.out.println("비밀번호 형식이 잘못 되었습니다.");
            checkValid = false;
        }
        else if( !userpw.equals(userpwcheck) ){
            System.out.println("비밀번호가 일치하지 않습니다.");
            checkValid = false;
        }
        return checkValid;
    }

    public Boolean join(UserDto userDto){
        if(!checkValidCondition(userDto)){
            //throw new IllegalArgumentException("회원가입 정보가 정확하지 않습니다.");
            return false;
        };

        Optional<User> found = userRepository.findByUserId(userDto.getUserId());
        if(found.isPresent()){
            //throw new IllegalArgumentException("중복된 사용자 id가 존재합니다.");
            System.out.println("중복된 사용자 id가 존재합니다.");
            return false;
        }
        User user = new User(userDto);
        userRepository.save(user);
        return true;
    }

    public boolean login(String userId, String userPw) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userPassword = user.getUserPw();
            return userPw.equals(userPassword);
        }
        return false;
    }
}
