package com.umc.pol.domain.user.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, columnDefinition = "0")
    private long score;

    @Column(nullable = false, columnDefinition = "0")
    private long level;

    @Column
    private String profileImg;

    @Builder
    public User(long id, String nickname, long score, long level, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.score = score;
        this.level = level;
        this.profileImg = profileImg;
    }
}