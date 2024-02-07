package com.example.board.controller;

import com.example.board.config.auth.PrincipalDetail;
import com.example.board.dto.ReplySaveRequestDto;
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
    return new ResponseDto<>(HttpStatus.OK, 1L);
  }

  @DeleteMapping("/api/board/{id}")
  public ResponseDto<Long> deletePost(@PathVariable Long id) {
    boardService.deletePost(id);
    return new ResponseDto<>(HttpStatus.OK, 1L);
  }

  @PutMapping("/api/board/{id}")
  public ResponseDto<Long> updatePost(@PathVariable Long id, @RequestBody Board board) {
    boardService.updatePost(id, board);
    return new ResponseDto<>(HttpStatus.OK, 1L);
  }

  @PostMapping("/api/board/{boardId}/reply")
  public ResponseDto<Long> saveReply(@RequestBody ReplySaveRequestDto replySaveRequestDto) {

    boardService.writeReply(replySaveRequestDto);
    return new ResponseDto<>(HttpStatus.OK, 1L);
  }

  @DeleteMapping("/api/board/reply/{replyId}")
  public ResponseDto<Long> deleteReply(@PathVariable Long replyId) {
    boardService.deleteReply(replyId);
    return new ResponseDto<>(HttpStatus.OK, 1L);
  }
}
