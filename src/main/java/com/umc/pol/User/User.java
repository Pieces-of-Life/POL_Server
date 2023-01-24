package com.umc.pol.User;


import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nickname;

    @Column
    private long score;

    @Column
    private long level;

    @Column
    private String profile_img;

    public User(String name) {
        this.nickname = nickname;
    }
}