package com.borikov.task7.exception;

public class XMLFlowerParserException extends Exception {
    public XMLFlowerParserException() {
    }

    public XMLFlowerParserException(String message) {
        super(message);
    }

    public XMLFlowerParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLFlowerParserException(Throwable cause) {
        super(cause);
    }
}
