package io.github.com.aplaraujo.dto;

public record AuthResponse(
        String token,
        String tokenType,
        Long expiresIn
) {
}
