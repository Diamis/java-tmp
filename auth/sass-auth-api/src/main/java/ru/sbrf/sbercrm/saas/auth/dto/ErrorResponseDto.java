package ru.sbrf.sbercrm.sass.auth.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sbrf.sbercrm.saas.auth.exception.BusinessLogcException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private List<String> errors;

    private String errorType;

    public ErrorResponseDto(BusinessLogcException exception) {
        this.errors = exception.getErrors();
    }

    public ErrorResponseDto(String singleError) {
        this.errors = Collections.singletonList(singleError);
    }

    public ErrorResponseDto(List<String> errors) {
        this.errors = errors;
    }

    public ErrorResponseDto(String errorType, List<String> errors) {
        this.errorType = errorType;
        this.errors = errors;
    }
}

