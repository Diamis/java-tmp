package ru.sbrf.sbercrm.sass.auth.exception;

public class UserNotFoundException extends BusinessLogicException {
    public static final String ERROR_MESSAGE = "Пользователь не найден";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
