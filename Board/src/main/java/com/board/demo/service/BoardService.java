package com.board.demo.service;

import com.board.demo.dto.ApiResponseDto;
import com.board.demo.dto.BoardDto;
import com.board.demo.dto.UserDetails;
import com.board.demo.entity.Board;
import com.board.demo.entity.User;
import com.board.demo.exception.CustomErrorCode;
import com.board.demo.exception.CustomException;
import com.board.demo.repository.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardDto> findAllBoards() {
        List<Board> boardLists = boardRepository.findAllByOrderByCreatedAtDesc();
        return boardLists.stream().map(BoardDto::new).toList();
        //List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        //return boardList;
    }

    public ApiResponseDto<BoardDto> getBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new CustomException(CustomErrorCode.INVALID_CODE);
        }
        Board board = boardOptional.get();
        BoardDto boardDto = new BoardDto(board);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", boardDto);
    }

    public ApiResponseDto<BoardDto> addBoard(BoardDto boardDto, UserDetails userDetails) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("user is" + userDetails);
        boardDto.setCreatedAt(currentDateTime);
        boardDto.setUser(userDetails.getUsername());
        Board board = new Board(boardDto);
        board = boardRepository.save(board);
        boardDto = new BoardDto(board);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", boardDto);
    }

    public ApiResponseDto<Long> deleteBoard(Long id, UserDetails userDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESOURCE_NOT_FOUND));
        BoardDto boardDto = new BoardDto(board);
        System.out.println("boardDto = " + boardDto.getUser());
        System.out.println("userDetails = " + boardDto.getUser());
        if (!Objects.equals(boardDto.getUser(), userDetails.getUsername())){
            throw new CustomException(CustomErrorCode.UNVALID_ERROR);
        }
        boardRepository.delete(board);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", id);
    }

    @Transactional
    public ApiResponseDto<BoardDto> updateBoard(BoardDto boardDto, UserDetails userDetails) {
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.RESOURCE_NOT_FOUND));
        if (!board.getUser().equals(userDetails.getUsername())){
            throw new CustomException(CustomErrorCode.UNVALID_ERROR);
        }
        board.update(boardDto);
        BoardDto boardUpdated = new BoardDto(board);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", boardUpdated);
    }

    public void validCondition(BoardDto boardDto) {
        if (boardDto.getTitle() == null) {
            throw new CustomException(CustomErrorCode.NO_TITLE);
        } else if (boardDto.getContent() == null) {
            throw new CustomException(CustomErrorCode.NO_CONTENT);
        }
    }
}