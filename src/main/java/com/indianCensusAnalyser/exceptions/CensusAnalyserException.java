package com.indianCensusAnalyser.exceptions;

public class CensusAnalyserException extends Exception{
    public enum ExceptionType {
        INCORRECT_FILE,FILE_TYPE_INCORRECT,DELIMITER_INCORRECT
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
