package ru.sbrf.sbercrm.sass.auth.exception;

public class OAuth2AuthorizationException extends BusinessLogicException {
    public OAuth2AuthorizationException(String singleError) {
        super(singleError);
    }

    public OAuth2AuthorizationException(String errorType, String singleError) {
        super(errorType, singleError);
    }
}
