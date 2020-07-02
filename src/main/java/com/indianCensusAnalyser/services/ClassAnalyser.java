package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.openCsvBuilder.exceptions.CSVBuilderException;
import com.openCsvBuilder.services.CSVBuilderFactory;
import com.openCsvBuilder.services.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class ClassAnalyser {
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
        int namOfEateries = 0;
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
            List csvList = csvBuilder.csvFileList(reader, csvClass);
            csvList.forEach(System.out::println);
            return csvList.size();
        } catch (IOException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CSVAnalyserException(e.getMessage(), e.type.name());
        }
    }
}