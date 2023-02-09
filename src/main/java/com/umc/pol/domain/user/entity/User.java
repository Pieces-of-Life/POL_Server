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
    @Column
    private Long id;

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

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public User(Long id, String password, String nickname, long score, long level, String profileImg, List<Like> likes) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.score = score;
        this.level = level;
        this.profileImg = profileImg;
        this.likes = likes;
    }
}