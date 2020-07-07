package com.indianCensusAnalyser.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indianCensusAnalyser.models.CensusDao;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CensusDao;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.IndiaStateCodeCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ClassAnalyser {
    /* JSON File paths */
    private final String INDIA_CENSUS_JSON_FILE = "src/test/resources/IndiaStateCensus.json";
    private final String INDIA_STATE_CODE_JSON_FILE = "src/test/resources/IndiaStateCode.json";

    private static List<CensusDao> stateDAOList = null;
    private static Map<String, CensusDao> stateDAOMap = null;

    public enum Country {
        INDIA,
        INDIA_CODE,
        US
    }

    /**
     * Constructor to set parameters
     */
    public ClassAnalyser() {
        stateDAOMap = new HashMap<>();
    }

    public int loadIndianStateDataList(String csvFilePath, Country country, Class csvClass)
            throws CSVAnalyserException {
        stateDAOMap = new CensusAdapterFactory().getCensusData(country, csvFilePath);
        return stateDAOMap.size();
    }

    /**
     * Get IndiaStateCensus file sorted based on any parameter in file
     * Convert to json
     *
     * @param stateCensusFilePath
     * @param parameter
     * @return
     * @throws CSVAnalyserException
     * @throws IOException
     */
    public String getSorted(String stateCensusFilePath, String parameter)
            throws CSVAnalyserException, IOException {
        Comparator<CensusDao> comparator = this.getComparator(parameter);
        this.loadIndianStateDataList(stateCensusFilePath, Country.INDIA, IndiaStateCensusCSV.class);
        stateDAOList = new ArrayList<>(stateDAOMap.values());
        this.isEmpty(stateDAOList);
        stateDAOList.sort(comparator);
        if (!parameter.equals("State")) {
            Collections.reverse(stateDAOList);
        }
        this.writeIntoJson(INDIA_CENSUS_JSON_FILE, stateDAOList);
        return new Gson().toJson(stateDAOList);
    }

    /**
     * get IndiaStateCode file sorted by state code and convert to json
     *
     * @param stateCodeDataFilePath
     * @return
     * @throws CSVAnalyserException
     * @throws IOException
     */
    public String getSortedStateCodeDataOnStateCode(String stateCodeDataFilePath)
            throws CSVAnalyserException, IOException {
        this.loadIndianStateDataList(stateCodeDataFilePath, Country.INDIA, IndiaStateCodeCSV.class);
        Comparator<CensusDao> comparator = Comparator.comparing(census -> census.stateCode);
        stateDAOList = new ArrayList<>(stateDAOMap.values());
        this.isEmpty(stateDAOList);
        stateDAOList.sort(comparator);
        this.writeIntoJson(INDIA_STATE_CODE_JSON_FILE, stateDAOList);
        return new Gson().toJson(stateDAOList);
    }

    /**
     * Switch case to create comparator based in parameter for India Census Data
     *
     * @param parameter
     * @return
     * @throws CSVAnalyserException
     */
    public Comparator<CensusDao> getComparator(String parameter) throws CSVAnalyserException {
        switch (parameter) {
            case "Area":
                return Comparator.comparing(census -> census.totalArea);
            case "Density":
                return Comparator.comparing(census -> census.populationDensity);
            case "Population":
                return Comparator.comparing(census -> census.population);
            case "State":
                return Comparator.comparing(census -> census.state);
            default:
                throw new CSVAnalyserException("Parameter does not exists !",
                        CSVAnalyserException.ExceptionType.INVALID_PARAMETER);
        }
    }

    /**
     * Function to check if list empty or not
     *
     * @param csvList
     * @throws CSVAnalyserException
     */
    public void isEmpty(List csvList) throws CSVAnalyserException {
        if (csvList.size() == 0) {
            throw new CSVAnalyserException("No data exists!", CSVAnalyserException.ExceptionType.EMPTY_CSV);
        }
    }

    /**
     * Return state with greatest population from both US and India
     *
     * @param indiaCensusFilePath
     * @param usCensusFilePath
     * @return
     * @throws CSVAnalyserException
     * @throws IOException
     */
    public String getMostDenseState(String indiaCensusFilePath, String usCensusFilePath)
            throws CSVAnalyserException, IOException {
        this.getSorted(indiaCensusFilePath, "Density");
        List<CensusDao> indiaList = stateDAOList;
        stateDAOMap.clear();
        this.getSorted(usCensusFilePath, "Population Density");
        List<CensusDao> usList = stateDAOList;
        if (indiaList.get(0).density > usList.get(0).density)
            return new Gson().toJson(indiaList.get(0));
        return new Gson().toJson(usList.get(0));
    }

    /**
     * Function to write list to json
     *
     * @param fileName
     * @param listToWrite
     * @throws IOException
     */
    public void writeIntoJson(String fileName, List listToWrite) throws IOException {
        Writer writer = new FileWriter(fileName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(listToWrite, writer);
    }
}