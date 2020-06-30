package com.indianCensusAnalyser.services;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder<E> implements ICSVBuilder {
    public Iterator<E> csvFileIterator(Reader reader, Class csvClass) throws CSVAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            Iterator<E> csvIterator = csvToBean.iterator();
            return csvIterator;
        } catch (RuntimeException e) {
            throw new CSVAnalyserException
                    ("File type is incorrect or incorrect header",
                            CSVAnalyserException.ExceptionType.INCORRECT_FILE_OR_HEADER);
        }
    }
}
