package com.indianCensusAnalyser.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.IndiaStateCodeCSV;
import com.indianCensusAnalyser.models.IndiaStateDAO;
import com.indianCensusAnalyser.models.USCensusDataCSV;
import com.openCsvBuilder.exceptions.CSVBuilderException;
import com.openCsvBuilder.services.CSVBuilderFactory;
import com.openCsvBuilder.services.ICSVBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class ClassAnalyser {
    /* JSON File paths */
    private final String INDIA_CENSUS_JSON_FILE = "src/test/resources/IndiaStateCensus.json";
    private final String US_STATE_JSON_FILE = "resources/USCensusData.json";
    private final String INDIA_STATE_CODE_JSON_FILE = "resources/IndiaStateCode.json";

    private static List<IndiaStateDAO> stateDAOList = null;
    private static Map<Object, IndiaStateDAO> stateDAOMap = null;
    private ICSVBuilder csvBuilder;

    /**
     * Constructor to set parameters
     */
    public ClassAnalyser() {
        csvBuilder = CSVBuilderFactory.createCSVBuilder();
        stateDAOMap = new HashMap<>();
    }

    /**
     * Class to create map of the defined bean class
     * @param csvFilePath
     * @param classType
     * @param csvClass
     * @return
     * @throws CSVAnalyserException
     */
    public int loadIndianStateDataList(String csvFilePath, String classType, Class csvClass)
            throws CSVAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            switch (classType) {
                case "India State Census":
                    Iterator<IndiaStateCensusCSV> indiaCensusCSVIterator = csvBuilder
                            .csvFileIterator(reader, csvClass);
                    Iterable<IndiaStateCensusCSV> indiaStateCensusCSVIterable = () -> indiaCensusCSVIterator;
                    StreamSupport.stream(indiaStateCensusCSVIterable.spliterator(), false)
                            .forEach(census -> stateDAOMap.put(census.state, new IndiaStateDAO(census)));
                    break;
                case "India State Code":
                    Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator = csvBuilder
                            .csvFileIterator(reader, csvClass);
                    Iterable<IndiaStateCodeCSV> indiaStateCodeCSVIterable = () -> indiaStateCodeCSVIterator;
                    StreamSupport.stream(indiaStateCodeCSVIterable.spliterator(), false)
                            .forEach(census -> stateDAOMap.put(census.stateCode, new IndiaStateDAO(census)));
                    break;
                case "US Census Data":
                    Iterator<USCensusDataCSV> usCensusDataCSVIterator = csvBuilder
                            .csvFileIterator(reader, csvClass);
                    Iterable<USCensusDataCSV> usCensusDataCSVIterable = () -> usCensusDataCSVIterator;
                    StreamSupport.stream(usCensusDataCSVIterable.spliterator(), false)
                            .forEach(census -> stateDAOMap.put(census.state, new IndiaStateDAO(census)));
                    break;
                default:
                    throw new CSVAnalyserException("Parameter does not exists !",
                            CSVAnalyserException.ExceptionType.INVALID_PARAMETER);
            }
            return stateDAOMap.size();
        } catch (IOException e) {
            throw new CSVAnalyserException(e.getMessage(),
                    CSVAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CSVAnalyserException(e.getMessage(), e.type.name());
        } catch (Exception e) {
            throw new CSVAnalyserException
                    ("Incorrect Delimiter", CSVAnalyserException.ExceptionType.WRONG_DELIMITER);
        }
    }

    /**
     * Get IndiaStateCensus file sorted based on any parameter in file
     * Convert to json
     * @param stateCensusFilePath
     * @param parameter
     * @return
     * @throws CSVAnalyserException
     * @throws IOException
     */
    public String getSortedIndiaCensusDataByParameters(String stateCensusFilePath, String parameter)
            throws CSVAnalyserException, IOException {
        Comparator<IndiaStateDAO> comparator = this.getComparatorForIndia(parameter);
        this.loadIndianStateDataList(stateCensusFilePath, "India State Census", IndiaStateCensusCSV.class);
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
     * Get USCensusData file sorted based on any parameter in file
     * Convert to json
     * @param usCensusFilePath
     * @param parameter
     * @return
     * @throws CSVAnalyserException
     * @throws IOException
     */
    public String getSortedUSCensusDataByParameters(String usCensusFilePath, String parameter)
            throws CSVAnalyserException, IOException {
        Comparator<IndiaStateDAO> comparator = this.getComparatorForUS(parameter);
        this.loadIndianStateDataList(usCensusFilePath, "US Census Data", USCensusDataCSV.class);
        stateDAOList = new ArrayList<>(stateDAOMap.values());
        this.isEmpty(stateDAOList);
        stateDAOList.sort(comparator);
        if (!(parameter.equals("State Id") || parameter.equals("State"))) {
            Collections.reverse(stateDAOList);
        }
        this.writeIntoJson(US_STATE_JSON_FILE, stateDAOList);
        return new Gson().toJson(stateDAOList);
    }

    /**
     * get IndiaStateCode file sorted by state code and convert to json
     * @param stateCodeDataFilePath
     * @return
     * @throws CSVAnalyserException
     * @throws IOException
     */
    public String getSortedStateCodeDataOnStateCode(String stateCodeDataFilePath)
            throws CSVAnalyserException, IOException {
        this.loadIndianStateDataList(stateCodeDataFilePath, "India State Code", IndiaStateCodeCSV.class);
        Comparator<IndiaStateDAO> comparator = Comparator.comparing(census -> census.stateCode);
        stateDAOList = new ArrayList<>(stateDAOMap.values());
        this.isEmpty(stateDAOList);
        stateDAOList.sort(comparator);
        this.writeIntoJson(INDIA_STATE_CODE_JSON_FILE, stateDAOList);
        return new Gson().toJson(stateDAOList);
    }

    /**
     * Switch case to create comparator based in parameter for India Census Data
     * @param parameter
     * @return
     * @throws CSVAnalyserException
     */
    public Comparator<IndiaStateDAO> getComparatorForIndia(String parameter) throws CSVAnalyserException {
        switch (parameter) {
            case "Area":
                return Comparator.comparing(census -> census.areaInSqKm);
            case "Density":
                return Comparator.comparing(census -> census.densityPerSqKm);
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
     * Switch case to create comparator based in parameter for US Census Data
     * @param parameter
     * @return
     * @throws CSVAnalyserException
     */
    public Comparator<IndiaStateDAO> getComparatorForUS(String parameter) throws CSVAnalyserException {
        switch (parameter) {
            case "State Id":
                return Comparator.comparing(census -> census.stateId);
            case "State":
                return Comparator.comparing(census -> census.state);
            case "Population":
                return Comparator.comparing(census -> census.population);
            case "Housing Units":
                return Comparator.comparing(census -> census.housingUnits);
            case "Total Area":
                return Comparator.comparing(census -> census.totalArea);
            case "Water Area":
                return Comparator.comparing(census -> census.waterArea);
            case "Land Area":
                return Comparator.comparing(census -> census.landArea);
            case "Population Density":
                return Comparator.comparing(census -> census.populationDensity);
            case "Housing Density":
                return Comparator.comparing(census -> census.housingDensity);
            default:
                throw new CSVAnalyserException("Parameter does not exists !",
                        CSVAnalyserException.ExceptionType.INVALID_PARAMETER);
        }
    }

    /**
     * Function to check if list empty or not
     * @param csvList
     * @throws CSVAnalyserException
     */
    public void isEmpty(List csvList) throws CSVAnalyserException {
        if (csvList.size() == 0) {
            throw new CSVAnalyserException("No data exists!", CSVAnalyserException.ExceptionType.EMPTY_CSV);
        }
    }

    /**
     * Function to write list to json
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