package com.example.springbasicnewspeed.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordUpdateRequest {

    @NotBlank(message = "현재 비밀번호를 입력해야 합니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해야 합니다.")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,}",
            message = "비밀번호는 8자 이상이며, 영어, 숫자, 특수문자를 최소 1글자 이상 포함해야 합니다."
    )
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인을 입력해야 합니다.")
    private String newPasswordCheck;
}
