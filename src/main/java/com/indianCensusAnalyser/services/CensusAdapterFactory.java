package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.models.CensusDao;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CensusDao;

import java.util.Map;

public class CensusAdapterFactory {
    public Map<String, CensusDao> getCensusData(ClassAnalyser.Country country, String... csvFilePath) throws CSVAnalyserException {
        if (country.equals(ClassAnalyser.Country.INDIA)) {
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
//        } else if (country.equals(ClassAnalyser.Country.INDIA_CODE)) {
//            return new IndiaStateAdapter().loadCensusData(csvFilePath);
        } else if (country.equals(ClassAnalyser.Country.US)) {
            return new USCensusAdapter().loadCensusData(csvFilePath);
        } else {
            throw new CSVAnalyserException("Invalid Country", CSVAnalyserException.ExceptionType.INVALID_PARAMETER);
        }
    }
}
