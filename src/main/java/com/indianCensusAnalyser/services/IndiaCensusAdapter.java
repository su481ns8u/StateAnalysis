package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CensusDao;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;

import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter{

    @Override
    public Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CSVAnalyserException {
        Map<String, CensusDao> indiaCensusMap = super.loadCensusData(IndiaStateCensusCSV.class, csvFilePath[0]);
        return indiaCensusMap;
    }
}
