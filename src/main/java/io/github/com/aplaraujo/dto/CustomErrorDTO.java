package io.github.com.aplaraujo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class CustomErrorDTO {
    private final Instant timestamp;
    private final Integer status;
    private final String error;
    private final String path;
}
