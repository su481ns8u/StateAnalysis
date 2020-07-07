package com.indianCensusAnalyser.exceptions;

public class CSVAnalyserException extends Exception {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, INVALID_HEADER_AND_DELIMITER, INVALID_COUNTRY, EMPTY_FILE, INVALID_PARAMETER
    }

    public ExceptionType type;

    public CSVAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public CSVAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
