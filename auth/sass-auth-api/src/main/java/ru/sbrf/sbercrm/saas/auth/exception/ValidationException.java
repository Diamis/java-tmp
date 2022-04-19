package ru.sbrf.sbercrm.sass.auth.exception;

public class ValidationException extends BusinessLogicException {
    public ValidationException(String singleError) {
        super(singleError);
    }
}
