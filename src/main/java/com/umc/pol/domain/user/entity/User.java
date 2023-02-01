package com.umc.pol.domain.user.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column
    private long id;

    @Column
    private String password;

    @NotNull
    @Column
    private String nickname;

    @NotNull
    @Column(columnDefinition = "0")
    private long score;

    @NotNull
    @Column(columnDefinition = "0")
    private long level;

    @Column(name = "profile_img")
    private String profileImg;

    @Builder
    public User(long id, String password, String nickname, long score, long level, String profileImg) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.score = score;
        this.level = level;
        this.profileImg = profileImg;
    }
}