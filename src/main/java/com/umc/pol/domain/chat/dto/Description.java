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
public class Description {

    // firebase timestamp type
    private Timestamp date;
    private String sender;
    private String writer;
}