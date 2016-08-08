package com.fed.file.exception;

public class FileHandlingException extends RuntimeException {
    private static final long serialVersionUID = 8087838292736931178L;

    public FileHandlingException(String message) {
        this(message, null);
    }
    
    public FileHandlingException(Throwable exception) {
        super(exception);
    }

    public FileHandlingException(String message, Throwable exception) {
        super(message, exception);
    }
}
