package com.example.springbasicnewspeed.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {

    private String email;
    private String password;
    private String userName;
}
