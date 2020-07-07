package com.indianCensusAnalyser.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indianCensusAnalyser.Adapters.CensusAdapterFactory;
import com.indianCensusAnalyser.dao.CensusDAO;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.USCensusDataCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

public class ClassAnalyser {
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    public enum Country {
        INDIA, INDIA_CODE, US
    }

    public enum SortParameter {
        STATE, STATE_CODE, POPULATION, DENSITY, AREA
    }

    private Country country;
    private Map<String, CensusDAO> censusDAOMap;
    private List<CensusDAO> censusDAOList;

    public Map<String, CensusDAO> loadCensusData(Country country, String... csvFilePath) throws CSVAnalyserException {
        this.country = country;
        censusDAOMap = new CensusAdapterFactory().getCensusData(country, csvFilePath);
        censusDAOList = censusDAOMap.values().stream().collect(Collectors.toList());
        return censusDAOMap;
    }

    public void writeCSVToJSON(String fileName, List listToWrite) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToWrite, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sortAndWrite(SortParameter parameter, String filePath) throws CSVAnalyserException {
        if (censusDAOList == null || censusDAOMap.size() == 0) {
            throw new CSVAnalyserException("empty file", CSVAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<CensusDAO> censusComparator = this.getComparator(parameter);
        List censusDTOList = censusDAOList.stream().sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        if (parameter != SortParameter.STATE && parameter != SortParameter.STATE_CODE) {
            Collections.reverse(censusDTOList);
        }
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        writeCSVToJSON(filePath, censusDTOList);
        return sortedStateCensusJson;
    }

    public Comparator<CensusDAO> getComparator(SortParameter parameter) throws CSVAnalyserException {
        switch (parameter) {
            case AREA:
                return Comparator.comparing(census -> census.totalArea);
            case DENSITY:
                return Comparator.comparing(census -> census.populationDensity);
            case POPULATION:
                return Comparator.comparing(census -> census.population);
            case STATE:
                return Comparator.comparing(census -> census.state);
            case STATE_CODE:
                return Comparator.comparing(census -> census.stateCode);
            default:
                throw new CSVAnalyserException("Parameter does not exists !",
                        CSVAnalyserException.ExceptionType.INVALID_PARAMETER);
        }
    }

    public String getMostDenseState(String indiaCensusFilePath, String usCensusFilePath) throws CSVAnalyserException {
        this.loadCensusData(Country.INDIA,indiaCensusFilePath,INDIA_STATE_CODE_CSV_FILE_PATH);
        String indiaJson = this.sortAndWrite
                (SortParameter.DENSITY, "./src/test/resources/IndiaCensusDataDensity.json");
        IndiaStateCensusCSV[] indiaCensusCSV = new Gson().fromJson(indiaJson, IndiaStateCensusCSV[].class);

        this.loadCensusData(Country.US, usCensusFilePath);
        String usJson = this.sortAndWrite
                (SortParameter.DENSITY, "./src/test/resources/USCensusDataDensity.json");
        USCensusDataCSV[] usCensusCSV = new Gson().fromJson(usJson, USCensusDataCSV[].class);

        if (indiaCensusCSV[0].densityPerSqKm > usCensusCSV[0].populationDensity)
            return new Gson().toJson(indiaCensusCSV[0]);
        return new Gson().toJson(usCensusCSV[0]);
    }
}