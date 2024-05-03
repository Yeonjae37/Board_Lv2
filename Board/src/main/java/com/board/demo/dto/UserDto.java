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
}

