package ru.sbrf.sbercrm.sass.auth.exception;

import java.util.List;

public class InvalidPasswordException  extends BusinessLogicException {
    public InvalidPasswordException(List<String> errors) {
        super(errors);
    }

    public InvalidPasswordException(String singleError) {
        super(singleError);
    }

    public InvalidPasswordException(String type, String singleError) {
        super(type, singleError);
    }
}
