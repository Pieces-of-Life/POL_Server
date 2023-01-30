package com.umc.pol.domain.user.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column
    private long id;

    @Column
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, columnDefinition = "0")
    private long score;

    @Column(nullable = false, columnDefinition = "0")
    private long level;

    @Column
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