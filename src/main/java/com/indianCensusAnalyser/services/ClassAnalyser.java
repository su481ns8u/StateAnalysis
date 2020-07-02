package com.indianCensusAnalyser.services;

import com.google.gson.Gson;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCensus;
import com.indianCensusAnalyser.models.CSVStateCode;
import com.openCsvBuilder.exceptions.CSVBuilderException;
import com.openCsvBuilder.services.CSVBuilderFactory;
import com.openCsvBuilder.services.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class ClassAnalyser {
    private static List csvList = null;

    public int loadIndianStateData(String csvFilePath, Class csvClass) throws CSVAnalyserException {
        try {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<Class> csvIterator = csvBuilder.csvFileIterator(reader, csvClass);
            return this.getCount(csvIterator);
        } catch (IOException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CSVAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> csvIterator) throws CSVAnalyserException {
        Iterable<E> iterable = () -> csvIterator;
        try {
            return (int) StreamSupport.stream(iterable.spliterator(), false).count();
        } catch (RuntimeException e) {
            throw new CSVAnalyserException(e.getMessage(), CSVAnalyserException.ExceptionType.WRONG_DELIMITER);
        }
    }

    public int loadIndianStateDataList(String csvFilePath, Class csvClass) throws CSVAnalyserException {
        try {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            csvList = csvBuilder.csvFileList(reader, csvClass);
            return csvList.size();
        } catch (IOException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CSVAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getSortedStateCensusData(String stateCensusData, Comparator<CSVStateCensus> comparator)
            throws CSVAnalyserException {
        this.loadIndianStateDataList(stateCensusData, CSVStateCensus.class);
        this.isEmpty();
        csvList.sort(comparator);
        String sortedJsonList = new Gson().toJson(csvList);
        return sortedJsonList;
    }

    public String getSortedStateCodeData(String stateCensusData, Comparator<CSVStateCode> comparator)
            throws CSVAnalyserException {
        this.loadIndianStateDataList(stateCensusData, CSVStateCode.class);
        this.isEmpty();
        csvList.sort(comparator);
        String sortedJsonList = new Gson().toJson(csvList);
        return sortedJsonList;
    }

    public String getSortedStateCensusDataReverse(String stateCensusData, Comparator<CSVStateCensus> comparator)
            throws CSVAnalyserException {
        this.loadIndianStateDataList(stateCensusData, CSVStateCensus.class);
        this.isEmpty();
        csvList.sort(comparator);
        Collections.reverse(csvList);
        String sortedJsonList = new Gson().toJson(csvList);
        return sortedJsonList;
    }

    public void isEmpty() throws CSVAnalyserException {
        if (csvList.size() == 0) {
            throw new CSVAnalyserException("No data exists!", CSVAnalyserException.ExceptionType.EMPTY_CSV);
        }
    }
}