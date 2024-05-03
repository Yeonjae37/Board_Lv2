package com.board.demo.entity;

import com.board.demo.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @JsonProperty("user_email")
    private String userEmail;

    @Column(nullable = false)
    @JsonProperty("user_id")
    private String userId;

    @Column(nullable = false)
    @JsonProperty("user_pw")
    private String userPw;

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.userEmail = userDto.getUserEmail();
        this.userId = userDto.getUserId();
        this.userPw = userDto.getUserPw();
    }

}