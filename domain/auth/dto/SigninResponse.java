package com.example.springbasicnewspeed.domain.auth.dto;

public class SigninResponse {

    private final String bearerJwt;

    public SigninResponse(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
