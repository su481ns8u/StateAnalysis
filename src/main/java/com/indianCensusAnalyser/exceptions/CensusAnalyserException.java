package com.indianCensusAnalyser.exceptions;

public class CensusAnalyserException extends Exception{
    public enum ExceptionType {
        CENSUS_FILE_NOT_FOUND,INCORRECT_FILE_EXTENSION
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
