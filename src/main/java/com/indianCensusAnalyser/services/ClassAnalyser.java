package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class ClassAnalyser {
    public <E> int loadIndianStateData(String csvFilePath, Class<E> csvClass) throws CSVAnalyserException {
        int numOfEntries = 0;
        try {
            Iterator<E> csvIterator = null;
            try {
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                csvIterator = new OpenCSVBuilder().csvFileIterator(reader, csvClass);
            } catch (NoSuchFileException e) {
                throw new CSVAnalyserException
                        ("No such file exists",
                                CSVAnalyserException.ExceptionType.EXCEPTION_TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            numOfEntries = this.getCount(csvIterator);
        } catch (CSVAnalyserException e) {
            throw e;
        }
        return numOfEntries;
    }

    private <E> int getCount (Iterator<E> csvIterator) throws CSVAnalyserException {
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