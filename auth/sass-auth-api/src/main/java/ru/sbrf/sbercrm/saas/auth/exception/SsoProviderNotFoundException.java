package ru.sbrf.sbercrm.sass.auth.exception;

public class SsoProviderNotFoundException extends BusinessLogicException {
    private static final String ERROR_MESSAGE = "Провайдер не найден";

    public SsoProviderNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
