package com.likelion.mimiki.exception;

public class WikiNotFoundException extends RuntimeException {
    public WikiNotFoundException(String message) {
        super(message);
    }
}
