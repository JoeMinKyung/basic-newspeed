package com.example.springbasicnewspeed.common.exception;

import com.example.springbasicnewspeed.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation 실패 시 처리하는 메서드
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        // 모든 필드의 검증 에러 메시지를 리스트로 변환
        List<String> errors = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ErrorResponse.of("400", "유효성 검사 실패", errors);
    }

    // 이메일이 이미 존재할 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ErrorResponse handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 닉네임이 이미 존재할 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ErrorResponse handleUserNameAlreadyExistsException(UserNameAlreadyExistsException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 비밀번호가 일치하지 않을 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMismatchException.class)
    public ErrorResponse handlePasswordMismatchException(PasswordMismatchException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 이메일이 존재하지 않을 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotFoundException.class)
    public ErrorResponse handleEmailNotFoundException(EmailNotFoundException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 잘못된 비밀번호일 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectPasswordException.class)
    public ErrorResponse handleIncorrectPasswordException(IncorrectPasswordException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 유저가 존재하지 않을 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 비밀번호 변경 시 새 비밀번호와 현재 비밀번호가 동일하지 않을 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SamePasswordException.class)
    public ErrorResponse handleSamePasswordException(SamePasswordException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 포스트가 존재하지 않을 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 본인 게시물에 좋아요를 누를 때
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotLikeOwnPostException.class)
    public ErrorResponse handleCannotLikeOwnPostException(CannotLikeOwnPostException e) {
        return ErrorResponse.of("400", e.getMessage());
    }

    // 기타 모든 예외 처리 (서버 에러)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGlobalException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ErrorResponse.of("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");
    }
}
