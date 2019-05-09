package ru.ifmo.lab3.exception;

public class FileFormatException extends RuntimeException {
    public FileFormatException(String message) {
        super(message);
    }
}