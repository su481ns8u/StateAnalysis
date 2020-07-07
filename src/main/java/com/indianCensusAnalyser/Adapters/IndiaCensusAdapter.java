package com.indianCensusAnalyser.Adapters;


import com.indianCensusAnalyser.dao.CensusDAO;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.IndiaStateCodeCSV;
import com.openCsvBuilder.exceptions.CSVBuilderException;
import com.openCsvBuilder.services.CSVBuilderFactory;
import com.openCsvBuilder.services.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    Map<String, CensusDAO> indiaCensusMap;

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVAnalyserException {
        indiaCensusMap = super.loadCensusData(IndiaStateCensusCSV.class, csvFilePath[0]);
        this.loadCensusData(indiaCensusMap, csvFilePath[1]);
        return indiaCensusMap;
    }

    public int loadCensusData(Map<String, CensusDAO> indiaCensusMap, String csvFilePath) throws CSVAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder
                    .csvFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> indianStateCodeCSV = () -> censusCSVIterator;
            StreamSupport.stream((indianStateCodeCSV.spliterator()), false)
                    .filter(csvState -> indiaCensusMap.get(csvState.stateName) != null)
                    .forEach(scState -> indiaCensusMap.get(scState.stateName).stateCode = scState.stateCode);
            return indiaCensusMap.size();
        } catch (IOException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CSVAnalyserException("Invalid header or delimiter",
                    CSVAnalyserException.ExceptionType.INVALID_HEADER_AND_DELIMITER);
        } catch (CSVBuilderException e) {
            throw new CSVAnalyserException(e.getMessage(), e.type.name());
        }
    }
}
