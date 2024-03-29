package com.springboard.webboard.web.dto;

import com.springboard.webboard.domain.board.Board;
import com.springboard.webboard.domain.user.User;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

    private Long id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    private Integer view;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private User user;


    @Builder
    public BoardDto(Long id, String title, String content, String filename, String filepath, Integer view, LocalDateTime createdDate, LocalDateTime modifiedDate, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.view = view;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.user = user;
    }

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
                .view(view)
                .build();
    }

    public Page<BoardDto> toDtoList(Page<Board> boards) {
        Page<BoardDto> boardDtoList = boards.map(m -> BoardDto.builder()
                .id(m.getId())
                .title(m.getTitle())
                .content(m.getContent())
                .filename(m.getFilename())
                .filepath(m.getFilepath())
                .view(m.getView())
                .user(m.getUser())
                .createdDate(m.getCreatedDate())
                .modifiedDate(m.getModifiedDate())
                .build());
        return boardDtoList;
    }


}
