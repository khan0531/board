package com.example.board.controller;

import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  @GetMapping({"", "/"})
  public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    model.addAttribute("boards", boardService.getPostList(pageable));
    return "index";
  }

  @GetMapping("/board/saveForm")
  public String saveForm() {
    return "board/saveForm";
  }

  @GetMapping("/board/{id}")
  public String getPost(@PathVariable Long id, Model model) {
    model.addAttribute("boards", boardService.getPost(id));
    return "board/detail";
  }

  @GetMapping("/board/{id}/updateForm")
  public String updateForm(@PathVariable Long id, Model model) {
    model.addAttribute("boards", boardService.getPost(id));
    return "board/updateForm";
  }

}
