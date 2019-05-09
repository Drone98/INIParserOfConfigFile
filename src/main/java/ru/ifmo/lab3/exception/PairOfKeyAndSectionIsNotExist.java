package ru.ifmo.lab3.exception;

public class PairOfKeyAndSectionIsNotExist extends RuntimeException {
    public PairOfKeyAndSectionIsNotExist(String message) {
        super(message);
    }
}