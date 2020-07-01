package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.openCsvBuilder.services.ICSVBuilder;
import com.openCsvBuilder.services.CSVBuilderFactory;
import com.openCsvBuilder.exceptions.CSVBuilderException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class ClassAnalyser {
    public int loadIndianStateData(String csvFilePath, Class csvClass) throws CSVAnalyserException {
        int numOfEntries = 0;
        try {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<Class> csvIterator = null;
            try {
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                csvIterator = csvBuilder.csvFileIterator(reader, csvClass);
            } catch (NoSuchFileException e) {
                throw new CSVAnalyserException
                        ("No such file exists",
                                CSVAnalyserException.ExceptionType.EXCEPTION_TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CSVBuilderException e) {
                throw new CSVAnalyserException(e.getMessage(), e.type.name());
            }
            numOfEntries = this.getCount(csvIterator);
        } catch (CSVAnalyserException e) {
            throw e;
        }
        return numOfEntries;
    }

    private <E> int getCount(Iterator<E> csvIterator) throws CSVAnalyserException {
        int count = 0;
        try {
            while (csvIterator.hasNext()) {
                count++;
                Object data = csvIterator.next();
            }
        } catch (RuntimeException e) {
            throw new CSVAnalyserException
                    ("File contains wrong delimiter", CSVAnalyserException.ExceptionType.DELIMITER_INCORRECT);
        }
        return count;
    }
}