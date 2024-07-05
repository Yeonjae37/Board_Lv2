package com.board.demo.controller;

import com.board.demo.dto.ApiResponseDto;
import com.board.demo.dto.BoardDto;
import com.board.demo.dto.UserDetails;
import com.board.demo.entity.Board;
import com.board.demo.entity.User;
import com.board.demo.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public List<Board> getBoards(){
        return boardService.findAllBoards();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ApiResponseDto<BoardDto> getBoard(@PathVariable("id") Long id){
        return boardService.getBoard(id);
    }

    @ResponseBody
    @PostMapping("/add")
    public ApiResponseDto<BoardDto> addBoard(@RequestBody BoardDto boardDto, @AuthenticationPrincipal UserDetails userDetails){
        boardService.validCondition(boardDto);
        return boardService.addBoard(boardDto, userDetails);
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}/{pw}")
    public ApiResponseDto<Long> deleteBoard(@PathVariable("id") Long id, @PathVariable("pw") String pw){
        return boardService.deleteBoard(id, pw);
    }

    @ResponseBody
    @PutMapping("/update")
    public ApiResponseDto<BoardDto> updateBoard(@RequestBody BoardDto boardDto){
        return boardService.updateBoard(boardDto);
    }
}
