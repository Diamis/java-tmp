package ru.sbrf.sbercrm.sass.auth.exception;

public class SsoProviderConfigurationNotFoundException extends BusinessLogicException {
    public static final String ERROR_MESSAGE = "Провайдер не найден";

    public SsoProviderConfigurationNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
