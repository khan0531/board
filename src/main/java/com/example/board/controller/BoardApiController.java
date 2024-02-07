package com.example.board.controller;

import com.example.board.config.auth.PrincipalDetail;
import com.example.board.dto.ResponseDto;
import com.example.board.model.Board;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
  private final BoardService boardService;

  @PostMapping("/api/board")
  public ResponseDto<Long> savePost(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail) {
    boardService.writePost(board, principalDetail.getUser());
    return new ResponseDto<>(HttpStatus.OK, board.getId());
  }

  @DeleteMapping("/api/board/{id}")
  public ResponseDto<Long> deletePost(@PathVariable Long id) {
    boardService.deletePost(id);
    return new ResponseDto<>(HttpStatus.OK, id);
  }

  @PutMapping("/api/board/{id}")
  public ResponseDto<Long> updatePost(@PathVariable Long id, @RequestBody Board board) {
    boardService.updatePost(id, board);
    return new ResponseDto<>(HttpStatus.OK, id);
  }
}
