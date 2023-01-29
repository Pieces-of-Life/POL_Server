package com.umc.pol.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum ResponseCode {
    INVALIDATE_USER(400,"해당하는 사용자가 존재하지 않습니다.");

    private final int code;
    private final String message;
}