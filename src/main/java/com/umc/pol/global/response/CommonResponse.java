package com.umc.pol.global.response;

import lombok.Getter;

// https://velog.io/@minji/스프링부트-Response-감싸서-반환하기

@Getter
public class CommonResponse {

    int status;
    boolean success;
    String messsage;

}
