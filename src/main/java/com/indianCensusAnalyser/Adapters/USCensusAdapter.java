package com.indianCensusAnalyser.Adapters;

import com.indianCensusAnalyser.dao.CensusDAO;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.USCensusDataCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVAnalyserException {
        Map<String, CensusDAO> usCensusMap = super.loadCensusData(USCensusDataCSV.class, csvFilePath[0]);
        return usCensusMap;
    }
}
