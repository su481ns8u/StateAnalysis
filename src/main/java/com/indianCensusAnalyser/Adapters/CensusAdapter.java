package com.indianCensusAnalyser.Adapters;


import com.indianCensusAnalyser.dao.CensusDAO;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
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
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVAnalyserException;

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> CSVClass, String csvFilePath) throws CSVAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> indiaCensusCodeIterator = csvBuilder.csvFileIterator(reader, CSVClass);
            Iterable<E> stateCensuses = () -> indiaCensusCodeIterator;
            switch (CSVClass.getSimpleName()) {
                case "IndiaStateCensusCSV":
                    StreamSupport.stream(stateCensuses.spliterator(), false)
                            .map(IndiaStateCensusCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                    break;
                case "USCensusDataCSV":
                    StreamSupport.stream(stateCensuses.spliterator(), false)
                            .map(USCensusDataCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                    break;
            }
            return censusMap;
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
