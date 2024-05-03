package com.board.demo.repository;

import com.board.demo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{
    void delete(Board board);
    @Query("SELECT u.password FROM Board u WHERE u.id = :id")
    String findPasswordById(@Param("id") Long id);
}
