package com.springboard.webboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@Setter
public class Board extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //(message="이 값은 비어 있을 수 없습니다!!") //@Pattern(regexp="^[0-7]*$", message="PT횟수를 선택해주세요!!")
//    @NotNull
//    @Size(min = 2, max = 30, message = "제목을 2자이상 30자 이하로 입력해주세요.")
    private String title;

    private String content;

    private String filename;

    private String filepath;

    private Integer view;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

//    @OneToMany(mappedBy = "board")
//    private List<Reply> reply;

    public Board() {
    }

    @Builder
    public Board(Long id, String title, String content, String filename, String filepath, Integer view, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.view = view;
        this.user = user;
    }
}