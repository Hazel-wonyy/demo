package com.timely.demo.common.config.exception;

public class OAuthProviderMissMatchException extends RuntimeException {
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
