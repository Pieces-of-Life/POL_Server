package com.umc.pol.domain.chat.dto;

import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatSuccessResponse {

    private Long status;
    private Boolean success;
    private String message;

    @Builder
    public ChatSuccessResponse(Long status, Boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}