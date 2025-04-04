package com.example.springbasicnewspeed.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank @Email(message="이메일 형식을 지켜주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+=-]).{8,}$",
            message = "비밀번호는 8자 이상이며, 영어, 숫자, 특수문자를 최소 1개 이상 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordCheck;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    private String userName;
}
