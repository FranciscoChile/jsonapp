package com.sonatafy.exception;

public class JsonException extends RuntimeException {
    
    public JsonException() {
        super();
    }

    public JsonException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JsonException(final String message) {
        super(message);
    }

    public JsonException(final Throwable cause) {
        super(cause);
    }

}
