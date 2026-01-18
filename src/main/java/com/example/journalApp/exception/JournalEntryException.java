package com.example.journalApp.exception;

public class JournalEntryException extends RuntimeException {
    public JournalEntryException(String message) {
        super(message);
    }

    public JournalEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
