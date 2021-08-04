package com.example.webstudy.domian.service;

import com.example.webstudy.domian.entity.Board;
import com.example.webstudy.domian.repository.BoardRepository;
import com.example.webstudy.dto.BoardDto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT=5;
    private static final int PAGE_POST_COUNT =4;

    @Transactional
    public Long savePost(BoardDto boardDto)
    {
        return boardRepository.save(boardDto.toEntity()).getId();

    }
    @Transactional
    public List<BoardDto> getBoardList(Integer pageNum)
    {
        Page<Board> page =boardRepository.findAll(PageRequest.of(pageNum-1,PAGE_POST_COUNT,
                Sort.by(Sort.Direction.ASC,"createDate")));
        List<Board> board = page.getContent();
        List<BoardDto> boardDtoList =new ArrayList<>();

        for(Board boards:board)
        {
            BoardDto boardDto =BoardDto.builder()
                    .id(boards.getId())
                    .writer(boards.getWriter())
                    .title(boards.getTitle())
                    .content(boards.getContent())
                    .createDate(boards.getCreateDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }
    @Transactional
    public BoardDto getPost(Long id)
    {
        Optional<Board> boardWrapper=boardRepository.findById(id);
        Board board =boardWrapper.get();
        BoardDto boardDto=BoardDto.builder()
                .id(board.getId())
                .writer(board.getWriter())
                .title(board.getTitle())
                .content(board.getContent())
                .createDate(board.getCreateDate())
                .build();
        return boardDto;

    }
    @Transactional
    public void deletePost(Long id)
    {
        boardRepository.deleteById(id);
    }
    @Transactional
    public List<BoardDto> searchPosts(String keyword)
    {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();
        if(boards.isEmpty()) return boardDtoList;
        for (Board board:boards)
        {
            boardDtoList.add(this.convertEntityToDto(board));
        }
        return boardDtoList;
    }
    private BoardDto convertEntityToDto(Board board)
    {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .createDate(board.getCreateDate())
                .build();
    }
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        Double postsTotalCount = Double.valueOf(this.getBoardCount());
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }
        return pageList;
    }

}
