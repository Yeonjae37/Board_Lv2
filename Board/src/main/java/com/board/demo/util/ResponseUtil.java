package com.board.demo.util;

import com.board.demo.dto.ResponseDto;
import com.board.demo.entity.StatusEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;

public class ResponseUtil {
    public static ResponseEntity<?> createResponse(Object data, StatusEnum status, String message, HttpStatus httpStatus){
        ResponseDto responseDto = new ResponseDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        responseDto.setStatus(status);
        responseDto.setData(data);
        responseDto.setMessage(message);

        return new ResponseEntity<>(responseDto, headers, httpStatus);
    }
}
