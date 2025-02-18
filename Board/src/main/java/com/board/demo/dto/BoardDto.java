package com.board.demo.dto;

import com.board.demo.entity.Board;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    @Setter
    private String user;
    private int viewcnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardDto() {

    }

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.viewcnt = board.getViewcnt();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
