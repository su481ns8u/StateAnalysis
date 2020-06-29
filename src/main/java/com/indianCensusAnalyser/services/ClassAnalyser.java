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
    public int loadIndiaData(String csvFilePath, Object impClass) throws CSVAnalyserException {
        int namOfEateries = 0;
        try {
            Iterator<Object> CSVIterator = null;
            try {
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                CsvToBeanBuilder<Object> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType((Class) impClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<Object> csvToBean = csvToBeanBuilder.build();
                CSVIterator = csvToBean.iterator();
            } catch (NoSuchFileException e) {
                throw new CSVAnalyserException(e.getMessage(),
                        CSVAnalyserException.ExceptionType.FILE_TYPE_INCORRECT);
            } catch (RuntimeException e) {
                throw new CSVAnalyserException(e.getMessage(),
                        CSVAnalyserException.ExceptionType.INCORRECT_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (CSVIterator.hasNext()) {
                namOfEateries++;
                Object censusData = CSVIterator.next();
            }
        } catch (CSVAnalyserException e) {
            throw e;
        }catch (RuntimeException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.DELIMITER_INCORRECT);
        }
        return namOfEateries;
    }
//    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
//        int namOfEateries = 0;
//        try {
//            Reader reader = null;
//            Iterator<CSVStateCensus> censusCSVIterator = null;
//            try {
//                reader = Files.newBufferedReader(Paths.get(csvFilePath));
//                CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
//                csvToBeanBuilder.withType(CSVStateCensus.class);
//                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
//                CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
//                censusCSVIterator = csvToBean.iterator();
//            } catch (NoSuchFileException e) {
//                throw new CensusAnalyserException(e.getMessage(),
//                        CensusAnalyserException.ExceptionType.FILE_TYPE_INCORRECT);
//            } catch (RuntimeException e) {
//                throw new CensusAnalyserException(e.getMessage(),
//                        CensusAnalyserException.ExceptionType.INCORRECT_FILE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            namOfEateries = 0;
//            while (censusCSVIterator.hasNext()) {
//                namOfEateries++;
//                CSVStateCensus censusData = censusCSVIterator.next();
//            }
//        } catch (CensusAnalyserException e) {
//            throw e;
//        }catch (RuntimeException e) {
//            throw new CensusAnalyserException(e.getMessage(),
//                    CensusAnalyserException.ExceptionType.DELIMITER_INCORRECT);
//        }
//        return namOfEateries;
//    }
}
