package com.example.springbasicnewspeed.domain.auth.dto;

import lombok.Getter;

@Getter
public class SigninResponse {

    private final String bearerJwt;

    public SigninResponse(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
