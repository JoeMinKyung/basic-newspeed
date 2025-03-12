package com.example.springbasicnewspeed.domain.auth.dto;

public class SignupResponse {

    private final String bearerJwt;

    public SignupResponse(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
