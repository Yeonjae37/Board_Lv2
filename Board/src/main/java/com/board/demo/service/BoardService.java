package com.board.demo.service;

import com.board.demo.dto.BoardDto;
import com.board.demo.entity.Board;
import com.board.demo.entity.StatusEnum;
import com.board.demo.repository.BoardRepository;
import com.board.demo.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List <Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public BoardDto fromEntity(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setType(board.getType());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setWriter(board.getWriter());
        boardDto.setViewcnt(board.getViewcnt());
        boardDto.setDate(board.getDate());
        boardDto.setPassword(board.getPassword());
        return boardDto;
    }

    public ResponseEntity<?> getBoard(Long id){
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()){
            return ResponseEntity.badRequest().body("게시글을 찾을 수 없습니다.");
        }
        Board board = boardOptional.get();
        BoardDto boardDto = fromEntity(board);
        return ResponseUtil.createResponse(boardDto, StatusEnum.OK, "게시글 가져오기 성공", HttpStatus.OK);
    }

    public ResponseEntity<?> addBoard(BoardDto boardDto) {
        LocalDate currentDateTime = LocalDate.now();
        boardDto.setDate(currentDateTime);
        Board board = new Board(boardDto);
        board = boardRepository.save(board);
        boardDto = fromEntity(board);
        return ResponseUtil.createResponse(boardDto, StatusEnum.OK, "게시글 올리기 성공", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBoard(Long id, String pw) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ResponseEntity.badRequest().body("게시글을 찾을 수 없습니다.");
        }
        String storedPassword = boardRepository.findPasswordById(id);
        if (!storedPassword.equals(pw)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
        boardRepository.delete(board.get());
        return ResponseEntity.ok("ok");
    }

    @Transactional
    public ResponseEntity<?> updateBoard(BoardDto boardDto){
        Optional<Board> boardOptional = boardRepository.findById(boardDto.getId());
        if (!boardOptional.isPresent()) {
            return ResponseEntity.badRequest().body("게시글을 찾을 수 없습니다.");
        }
        Board board = boardOptional.get();
        //Optional 은 null이 될 수 있는 객체를 감싸는데 사용되는데 Optional 객체에서 직접 getPassword를
        // 호출 할 수 없기 때문에 메서드를 호출하기 전에 Optional에서 실제 Board 객체를 추출해야 함
        if (!board.getPassword().equals(boardDto.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
        board.update(boardDto);
        return ResponseUtil.createResponse(boardDto, StatusEnum.OK, "게시글 수정 성공", HttpStatus.OK);
    }

    public boolean validCondition(BoardDto boardDto) {
        if (boardDto.getTitle() == null) {
            System.out.println("타이틀이 비어있습니다.");
            return false;
        } else if (boardDto.getContent() == null) {
            System.out.println("내용이 비어있습니다.");
            return false;
        }
        else if (boardDto.getWriter() == null) {
            System.out.println("작성자가 비어있습니다.");
            return false;
        }
        else if (boardDto.getType() == null) {
            System.out.println("타입이 비어있습니다.");
            return false;
        }
        else if (boardDto.getPassword() == null) {
            System.out.println("게시글 비밀번호가 비어있습니다.");
            return false;
        }
        else {
            return true;
        }
    }
}
