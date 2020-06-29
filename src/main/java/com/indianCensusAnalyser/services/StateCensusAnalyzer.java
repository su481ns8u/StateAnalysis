package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CensusAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyzer {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        int namOfEateries = 0;
        try {
            Reader reader = null;
            reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStateCensus> censusCSVIterator = csvToBean.iterator();
            namOfEateries = 0;
            while (censusCSVIterator.hasNext()) {
                namOfEateries++;
                CSVStateCensus censusData = censusCSVIterator.next();
            }
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_NOT_FOUND);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_EXTENSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return namOfEateries;
    }
}
