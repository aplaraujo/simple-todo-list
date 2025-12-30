package io.github.com.aplaraujo.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ResponseError(int status, String message, List<CustomErrorDTO> errors) {
    public static ResponseError responseError(String message) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseError conflito(String mensagem) {
        return new ResponseError(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
