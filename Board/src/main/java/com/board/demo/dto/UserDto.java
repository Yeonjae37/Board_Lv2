package com.board.demo.dto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    private String userEmail;
    private String userId;
    private String userPw;
    private String userPwCheck;
    private String userName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //Date 타입과 웹 폼 또는 JSON 등에서 사용되는 문자열 기반의 날짜 표현 간에 자동 변환이 항상 명확하지 않기 때문에 해줘야함
    private Date userBirth;

    public int getId() {
        return id;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserId() {
        return userId;
    }
    public String getUserPw() {
        return userPw;
    }
    public String getUserPwCheck() {
        return userPwCheck;
    }
    public String getUserName() {
        return userName;
    }
    public Date getUserBirth() {
        return userBirth;
    }
}

