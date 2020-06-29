package com.indianCensusAnalyser.exceptions;

public class CSVAnalyserException extends Exception{
    public enum ExceptionType {
        INCORRECT_FILE,FILE_TYPE_INCORRECT,DELIMITER_INCORRECT
    }

    public ExceptionType type;

    public CSVAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

