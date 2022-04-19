package ru.sbrf.sbercrm.sass.auth.exception;
 

public class ApplicationNotFoundException extends BusinessLogicException {
    private static final String ERROR_MESSAGE = "Приложение не найдено";

    public ApplicationNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
