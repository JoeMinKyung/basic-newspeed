package com.example.springbasicnewspeed.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    @Builder
    private ErrorResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ErrorResponse<T> of(String code, String message) {
        return new ErrorResponse<>(code, message, null);
    }

    public static <T> ErrorResponse<T> of(String code, String message, T data) {
        return new ErrorResponse<>(code, message, data);
    }
}
