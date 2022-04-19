package ru.sbrf.sbercrm.sass.auth.exception;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base-класс для всех пользовательских исключений
 */
@AllArgsConstructor
public class BusinessLogicException extends RuntimeException {
    @Getter
    protected List<String> errors;
    @Getter
    protected String errorType;

    public BusinessLogicException(String singleError) {
        this.errors = Collections.singletonList(singleError);
    }

    public BusinessLogicException(List<String> errors) {
        this.errors = errors;
    }

    public BusinessLogicException(String errorType, List<String> errors) {
        this.errorType = errorType;
        this.errors = errors;
    }

    public BusinessLogicException(String errorType, String singleError) {
        this.errorType = errorType;
        this.errors = Collections.singletonList(singleError);
    }
}
