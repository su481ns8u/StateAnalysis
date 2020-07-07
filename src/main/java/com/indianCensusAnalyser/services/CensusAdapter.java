package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CensusDao;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.USCensusDataCSV;
import com.openCsvBuilder.exceptions.CSVBuilderException;
import com.openCsvBuilder.services.CSVBuilderFactory;
import com.openCsvBuilder.services.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;


public abstract class CensusAdapter {
    private static Map<String, CensusDao> stateDAOMap = null;
    private ICSVBuilder csvBuilder;

    public CensusAdapter() {
        csvBuilder = CSVBuilderFactory.createCSVBuilder();
        stateDAOMap = new HashMap<>();
    }

    public abstract Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CSVAnalyserException;

    public <E> Map<String, CensusDao> loadCensusData(Class<E> csvClass, String... csvFilePath) throws CSVAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));
            switch (csvClass.getSimpleName()) {
                case "IndiaStateCensusCSV":
                    Iterator<IndiaStateCensusCSV> indiaCensusCSVIterator = csvBuilder
                            .csvFileIterator(reader, csvClass);
                    Iterable<IndiaStateCensusCSV> indiaStateCensusCSVIterable = () -> indiaCensusCSVIterator;
                    StreamSupport.stream(indiaStateCensusCSVIterable.spliterator(), false)
                            .forEach(census -> stateDAOMap.put(census.state, new CensusDao(census)));
                    break;
                case "USCensusDataCSV":
                    Iterator<USCensusDataCSV> usCensusDataCSVIterator = csvBuilder
                            .csvFileIterator(reader, csvClass);
                    Iterable<USCensusDataCSV> usCensusDataCSVIterable = () -> usCensusDataCSVIterator;
                    StreamSupport.stream(usCensusDataCSVIterable.spliterator(), false)
                            .forEach(census -> stateDAOMap.put(census.state, new CensusDao(census)));
                    break;
                default:
                    throw new CSVAnalyserException("Parameter does not exists !",
                            CSVAnalyserException.ExceptionType.INVALID_PARAMETER);
            }
            return stateDAOMap;
        } catch (IOException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CSVAnalyserException(e.getMessage(), e.type.name());
        } catch (Exception e) {
            throw new CSVAnalyserException
                    ("Incorrect Delimiter", CSVAnalyserException.ExceptionType.WRONG_DELIMITER);
        }
    }
}
