package ru.sbrf.sbercrm.sass.auth.exception;

import java.util.List;

public class EntityNotFoundException extends BusinessLogicException {
    public EntityNotFoundException(List<String> errors) {
        super(errors);
    }

    public EntityNotFoundException(String singleError) {
        super(singleError);
    }
}
