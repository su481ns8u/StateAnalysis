package com.indianCensusAnalyser.services;

import indianCensusAnalyser.exceptions.CSVAnalyserException;
import indianCensusAnalyser.models.CensusDao;
import indianCensusAnalyser.models.IndiaStateCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter{

    @Override
    public Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CSVAnalyserException {
        Map<String, CensusDao> usCensusMap = super.loadCensusData(IndiaStateCensusCSV.class, csvFilePath[0]);
        return usCensusMap;
    }
}
