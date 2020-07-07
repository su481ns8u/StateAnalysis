package com.indianCensusAnalyser.Adapters;

import com.indianCensusAnalyser.dao.CensusDAO;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.services.ClassAnalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public Map<String, CensusDAO> getCensusData(ClassAnalyser.Country country, String... csvFilePath)
            throws CSVAnalyserException {
        switch (country) {
            case INDIA:
                return new IndiaCensusAdapter().loadCensusData(csvFilePath);
            case US:
                return new USCensusAdapter().loadCensusData(csvFilePath);
            default:
                throw new CSVAnalyserException("Invalid Country", CSVAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
    }
}
