package com.umc.pol.domain.firebase;

import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private String id;
    private String name;
    private String email;

    // firebase timestamp type
    private Timestamp create_dt;
    private Timestamp update_dt;

}