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
    private String userId;
    private String userPw;
    private String role;

    public int getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public String getUserPw() {
        return userPw;
    }
    public String getRole() {return role;}
}

