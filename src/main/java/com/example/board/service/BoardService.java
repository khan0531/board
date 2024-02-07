package com.example.board.service;

import com.example.board.dto.ReplySaveRequestDto;
import com.example.board.model.Board;
import com.example.board.model.Reply;
import com.example.board.model.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final ReplyRepository replyRepository;
  private final UserRepository userRepository;

  @Transactional
  public void writePost(Board board, User user) {
    board.setUser(user);
    boardRepository.save(board);
  }

  public Page<Board> getPostList(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Board getPost(Long id) {
    return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
  }

  @Transactional
  public void deletePost(Long id) {
    boardRepository.deleteById(id);
  }

  @Transactional
  public void updatePost(Long id, Board requestBoard) {
    Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    board.setTitle(requestBoard.getTitle());
    board.setContent(requestBoard.getContent());
  }

  @Transactional
  public void writeReply(ReplySaveRequestDto replySaveRequestDto) {
    Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

    User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

    Reply reply = Reply.builder()
      .user(user)
      .board(board)
      .content(replySaveRequestDto.getContent())
      .build();

    replyRepository.save(reply);
  }

  @Transactional
  public void deleteReply(Long replyId) {
    replyRepository.deleteById(replyId);
  }

}
