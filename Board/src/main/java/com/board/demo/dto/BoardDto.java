package com.board.demo.dto;

import com.board.demo.entity.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@JsonIgnoreProperties(value = {"password"})
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String user;
    private int viewcnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getUser() { return user; }
    public int getViewcnt() { return viewcnt; }
    public LocalDateTime getcreatedAt() { return createdAt; }
    public LocalDateTime getmodifiedAt() { return modifiedAt; }

    public void setcreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setmodifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
