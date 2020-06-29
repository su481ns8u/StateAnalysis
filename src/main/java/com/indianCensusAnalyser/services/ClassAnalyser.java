package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class ClassAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CSVAnalyserException {
        int namOfEateries = 0;
        try {
            Reader reader = null;
            Iterator<CSVStateCensus> censusCSVIterator = null;
            try {
                reader = Files.newBufferedReader(Paths.get(csvFilePath));
                CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(CSVStateCensus.class);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
                censusCSVIterator = csvToBean.iterator();
            } catch (NoSuchFileException e) {
                throw new CSVAnalyserException(e.getMessage(),
                        CSVAnalyserException.ExceptionType.FILE_TYPE_INCORRECT);
            } catch (RuntimeException e) {
                throw new CSVAnalyserException(e.getMessage(),
                        CSVAnalyserException.ExceptionType.INCORRECT_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            namOfEateries = 0;
            while (censusCSVIterator.hasNext()) {
                namOfEateries++;
                CSVStateCensus censusData = censusCSVIterator.next();
            }
        } catch (CSVAnalyserException e) {
            throw e;
        }catch (RuntimeException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.DELIMITER_INCORRECT);
        }
        return namOfEateries;
    }
}