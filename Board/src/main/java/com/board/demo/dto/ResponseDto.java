package com.board.demo.dto;

import com.board.demo.entity.StatusEnum;
import lombok.Data;

@Data
public class ResponseDto {
    private StatusEnum status;
    private String message;
    private Object data;
    public ResponseDto() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}
