package com.springboard.webboard.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//(message="이 값은 비어 있을 수 없습니다!!") //@Pattern(regexp="^[0-7]*$", message="PT횟수를 선택해주세요!!")

    @NotNull
    @Size(min = 2, max = 30, message = "제목을 2자이상 30자 이하로 입력해주세요.")
    private String title;
    private String content;
}