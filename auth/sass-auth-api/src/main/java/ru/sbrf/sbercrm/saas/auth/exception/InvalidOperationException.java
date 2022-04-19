package ru.sbrf.sbercrm.sass.auth.exception;

import java.util.List;

public class InvalidOperationException extends BusinessLogicException {
    public InvalidOperationException(List<String> errors) {
        super(errors);
    }

    public InvalidOperationException(String singleError) {
        super(singleError);
    }

    public InvalidOperationException(String type, String singleError) {
        super(type, singleError);
    }
}
