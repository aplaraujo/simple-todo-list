package io.github.com.aplaraujo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FieldMessage {
    private String fieldName;
    private String message;
}
