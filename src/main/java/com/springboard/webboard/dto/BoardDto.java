package com.springboard.webboard.dto;

import com.springboard.webboard.entity.Board;
import com.springboard.webboard.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Builder
    public BoardDto(Long id, String title, String content, String filename, String filepath, Integer view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.view = view;
    }

    public Board toEntity(){
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
                .view(view)
                .build();
    }
}
