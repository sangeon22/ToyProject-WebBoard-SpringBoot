package com.springboard.webboard.dto;

import com.springboard.webboard.entity.Board;
import com.springboard.webboard.entity.TimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardViewDto extends TimeEntity {

    private Long id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    private Integer view;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    @Builder
    public BoardViewDto(Long id, String title, String content, String filename, String filepath, Integer view, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.view = view;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Board toEntity(){
        Board build = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
                .view(view)
                .build();
        return build;
    }

}

