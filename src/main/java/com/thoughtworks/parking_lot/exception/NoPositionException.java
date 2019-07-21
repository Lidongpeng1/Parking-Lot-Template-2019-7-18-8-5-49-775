package com.thoughtworks.parking_lot.exception;

public class NoPositionException extends RuntimeException {
    public NoPositionException() {}

    public NoPositionException(String message) {
        super(message);
    }
}
