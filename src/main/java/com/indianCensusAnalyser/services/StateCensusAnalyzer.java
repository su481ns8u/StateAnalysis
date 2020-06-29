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
            Iterator<CSVStateCensus> censusCSVIterator = null;
            try {
                reader = Files.newBufferedReader(Paths.get(csvFilePath));
                CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(CSVStateCensus.class);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
                censusCSVIterator = csvToBean.iterator();
            } catch (NoSuchFileException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.FILE_TYPE_INCORRECT);
            } catch (RuntimeException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INCORRECT_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            namOfEateries = 0;
            while (censusCSVIterator.hasNext()) {
                namOfEateries++;
                CSVStateCensus censusData = censusCSVIterator.next();
            }
        } catch (CensusAnalyserException e) {
            throw e;
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_INCORRECT);
        }
        return namOfEateries;
    }
}
