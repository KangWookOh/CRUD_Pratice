package com.example.webstudy.controller;

import com.example.webstudy.domian.service.BoardService;
import com.example.webstudy.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;
    @GetMapping("/")
    public String list(Model model,@RequestParam(value = "page",defaultValue = "1")Integer pageNum)
    {
        List<BoardDto> boardDtoList =boardService.getBoardList(pageNum);
        Integer[] pageList =boardService.getPageList(pageNum);
        model.addAttribute("boardList",boardDtoList);
        model.addAttribute("pageList",pageList);
        return "board/list.html";
    }
    @GetMapping("/post")
    public String write()
    {
        return "board/write.html";
    }
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long id,Model model)
    {
        BoardDto boardDto =boardService.getPost(id);
        model.addAttribute("boardDto",boardDto);
        return "board/detail.html";
    }
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDto)
    {
        boardService.savePost(boardDto);
        return "redirect:/";
    }
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long id,Model model)
    {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto",boardDto);
        return "board/update.html";
    }
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long id)
    {
        boardService.deletePost(id);
        return "redirect:/";
    }
    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }
    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword,Model model)
    {
        List<BoardDto> boardDtoList =boardService.searchPosts(keyword);
        model.addAttribute("boardList",boardDtoList);
        return "board/list.html";
    }

}
