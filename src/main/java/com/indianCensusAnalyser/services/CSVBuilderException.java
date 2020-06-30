package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;

public class CSVBuilderException extends Exception {
    public enum ExceptionType {
        INCORRECT_FILE_OR_HEADER, EXCEPTION_TYPE, DELIMITER_INCORRECT
    }

    public ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
