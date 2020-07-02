package com.indianCensusAnalyser.exceptions;

public class CSVAnalyserException extends Exception {
    public CSVAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public enum ExceptionType {
        EMPTY_CSV,
        UNABLE_TO_PARSE,
        FILE_PROBLEM,
        CSV_FILE_INTERNAL_ISSUE,
        WRONG_HEADER_OR_UNABLE_TO_PARSE,
        WRONG_DELIMITER
    }

    public ExceptionType type;

    public CSVAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
