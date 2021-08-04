package com.example.webstudy.dto;

import com.example.webstudy.domian.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiDate;

    public Board toEntity()
    {
        Board build =Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return build;
    }
    @Builder
    public BoardDto(Long id, String writer,String title,String content,LocalDateTime createDate,LocalDateTime modifiDate)
    {
        this.id=id;
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.createDate=createDate;
        this.modifiDate=modifiDate;
    }
}
