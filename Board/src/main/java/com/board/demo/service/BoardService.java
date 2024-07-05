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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public BoardDto fromEntity(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setUser(board.getUser());
        boardDto.setViewcnt(board.getViewcnt());
        boardDto.setCreatedAt(board.getCreatedAt());
        boardDto.setModifiedAt(board.getModifiedAt());
        return boardDto;
    }

    public ApiResponseDto<BoardDto> getBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new CustomException(CustomErrorCode.INVALID_CODE);
        }
        Board board = boardOptional.get();
        BoardDto boardDto = fromEntity(board);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", boardDto);
    }

    public ApiResponseDto<BoardDto> addBoard(BoardDto boardDto, UserDetails userDetails) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("user is" + userDetails);
        boardDto.setCreatedAt(currentDateTime);
        boardDto.setUser(userDetails.getUsername());
        Board board = new Board(boardDto);
        board = boardRepository.save(board);
        boardDto = fromEntity(board);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", boardDto);
    }

    public ApiResponseDto<Long> deleteBoard(Long id, String pw) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            throw new CustomException(CustomErrorCode.RESOURCE_NOT_FOUND);
        }
        String storedPassword = boardRepository.findPasswordById(id);
        if (!storedPassword.equals(pw)) {
            throw new CustomException(CustomErrorCode.PASSWORD_ERROR);
        }
        boardRepository.delete(board.get());
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", id);
    }

    @Transactional
    public ApiResponseDto<BoardDto> updateBoard(BoardDto boardDto) {
        Optional<Board> boardOptional = boardRepository.findById(boardDto.getId());
        if (!boardOptional.isPresent()) {
            throw new CustomException(CustomErrorCode.RESOURCE_NOT_FOUND);
        }
        Board board = boardOptional.get();
        //Optional 은 null이 될 수 있는 객체를 감싸는데 사용되는데 Optional 객체에서 직접 getPassword를
        // 호출 할 수 없기 때문에 메서드를 호출하기 전에 Optional에서 실제 Board 객체를 추출해야 함
        board.update(boardDto);
        return ApiResponseDto.success(HttpStatus.OK.value(), "S001", boardDto);
    }

    public void validCondition(BoardDto boardDto) {
        if (boardDto.getTitle() == null) {
            throw new CustomException(CustomErrorCode.NO_TITLE);
        } else if (boardDto.getContent() == null) {
            throw new CustomException(CustomErrorCode.NO_CONTENT);
        }
    }
}
