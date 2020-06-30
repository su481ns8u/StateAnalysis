package com.indianCensusAnalyser.exceptions;

public class CSVAnalyserException extends Exception {
    public CSVAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public enum ExceptionType {
        INCORRECT_FILE_OR_HEADER, EXCEPTION_TYPE, DELIMITER_INCORRECT
    }

    public ExceptionType type;

    public CSVAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
