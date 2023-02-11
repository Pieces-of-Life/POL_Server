package com.umc.pol.domain.chat.dto;

import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Chat {

//    private String date;
//    private String userId;
    private String message;

    // firebase timestamp type
    // private Timestamp createAt;

}