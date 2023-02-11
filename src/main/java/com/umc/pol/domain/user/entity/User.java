package com.umc.pol.domain.user.entity;


import com.umc.pol.domain.story.entity.Like;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "kakao_id")
    @NotNull
    private Long kakaoId;

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

    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public User(Long id, String password, Long kakaoId, String nickname, long score, long level, String profileImg, String email, List<Like> likes) {
        this.id = id;
        this.password = password;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.score = score;
        this.level = level;
        this.profileImg = profileImg;
        this.email = email;
        this.likes = likes;
    }

    public void updateScoreAndLevel(Long score, Long level){
        this.score = score;
        this.level = level;
    }

}