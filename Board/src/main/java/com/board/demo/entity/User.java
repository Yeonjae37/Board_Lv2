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
    @Column(name = "id")
    private int id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @JsonProperty("role")
    private String role;

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.userId = userDto.getUserId();
        this.userPw = userDto.getUserPw();
        this.role = userDto.getRole();
    }

}