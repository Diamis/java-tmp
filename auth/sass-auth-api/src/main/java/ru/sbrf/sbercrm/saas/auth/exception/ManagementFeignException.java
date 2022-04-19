package ru.sbrf.sbercrm.sass.auth.exception;

public class ManagementFeignException extends BusinessLogicException {
    private static final String DEFAULT_ERROR_MESSAGE = "Внутренняя ошибка сервера";

    public ManagementFeignException(String singleError) {
        super(singleError);
    }
    
    public ManagementFeignException(String errorType, String singleError) {
        super(errorType, singleError);
    }

    public ManagementFeignException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
