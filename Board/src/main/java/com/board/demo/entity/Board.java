package com.board.demo.entity;

import com.board.demo.dto.BoardDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"password"})
public class Board {
    protected Board() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String user;

    @ColumnDefault("0")
    private int viewcnt;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;


    public Board(BoardDto boardDto){
        this.id = boardDto.getId();
        this.title = boardDto.getTitle();
        this.user = boardDto.getUser();
        this.content = boardDto.getContent();
        this.viewcnt = boardDto.getViewcnt();
        this.createdAt = boardDto.getcreatedAt();
        this.modifiedAt = boardDto.getmodifiedAt();
    }

    public void update(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
}
