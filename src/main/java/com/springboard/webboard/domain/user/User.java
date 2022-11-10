package com.springboard.webboard.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboard.webboard.domain.board.Board;
import com.springboard.webboard.domain.Role;
import com.springboard.webboard.domain.TimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String birth;

    private Boolean enabled;

    @Column(unique = true)
    private String email;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    public User(){}

    @Builder
    public User(String username, String email, String password, String birth, Boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.enabled = enabled;
    }

    public User update(String name) {
        this.username = username;
        return this;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

//    @OneToMany(mappedBy = "user")
//    private List<Reply> replyList;
}