package com.umc.pol.Global.Response;

import lombok.Getter;

@Getter
public class SingleResponse<T> extends CommonResponse {
    T data;
}
