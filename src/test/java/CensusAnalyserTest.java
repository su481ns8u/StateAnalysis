import com.google.gson.Gson;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.USCensusDataCSV;
import com.indianCensusAnalyser.services.ClassAnalyser;
import com.sun.tools.jdeprscan.CSV;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static com.indianCensusAnalyser.services.ClassAnalyser.Country.*;
import static com.indianCensusAnalyser.services.ClassAnalyser.SortParameter.*;

public class CensusAnalyserTest {
    private static final String INDIA_STATE_CENSUS_CSV_FILE = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_FILE_EXTENSION = "./src/test/resources/IndiaStateCensusData.sh";
    private static final String INDIA_CENSUS_CSV_WRONG_DELIMITER = "./src/test/resources/WrongDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_WRONG_HEADER = "./src/test/resources/WrongHeader.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE = "./src/test/resources/USCensusData.csv";

    ClassAnalyser censusAnalyser;

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            censusAnalyser = new ClassAnalyser();
            Map numOfRecords = censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE, INDIA_STATE_CODE_CSV_FILE);
            Assert.assertEquals(29, numOfRecords.size());
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFilePath_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CENSUS_CSV_FILE_PATH);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileState_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CENSUS_FILE_EXTENSION);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCsvFile_WhenImproperDelimiter_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_CENSUS_CSV_WRONG_DELIMITER);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INVALID_HEADER_AND_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianCensusCsvFile_WhenImproperHeader_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_CENSUS_CSV_WRONG_HEADER);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INVALID_HEADER_AND_DELIMITER, e.type);
        }
    }

    @Test
    public void giveIndianCensusData_WhenSortOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE, INDIA_STATE_CODE_CSV_FILE);
            String fileName = "./src/test/resources/IndiaStateCensusJson.json";
            String sortCensusData = censusAnalyser.sortAndWrite(STATE, fileName);
            IndiaStateCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaStateCensusCSV[].class);
            Assert.assertTrue(indiaCensusCSV[0].state.equals("Andhra Pradesh") &&
                    indiaCensusCSV[indiaCensusCSV.length - 1].state.equals("West Bengal"));
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void giveIndianCensusData_WhenSortOnPopulation_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE, INDIA_STATE_CODE_CSV_FILE);
            String fileName = "./src/test/resources/IndiaCensusDataPopulation.json";
            String sortCensusData = censusAnalyser.sortAndWrite(POPULATION, fileName);
            IndiaStateCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(199812341, indiaCensusCSV[0].population);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void giveIndianCensusData_WhenSortOnDensity_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE, INDIA_STATE_CODE_CSV_FILE);
            String fileName = "./src/test/resources/IndiaCensusDataDensity.json";
            String sortCensusData = censusAnalyser.sortAndWrite(DENSITY, fileName);
            IndiaStateCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(1102, indiaCensusCSV[0].densityPerSqKm);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void giveIndianCensusData_WhenSortOnArea_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE, INDIA_STATE_CODE_CSV_FILE);
            String fileName = "./src/test/resources/IndiaCensusDataArea.json";
            String sortCensusData = censusAnalyser.sortAndWrite(AREA, fileName);
            IndiaStateCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(342239, indiaCensusCSV[0].areaInSqKm);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void givenUsCensusCSVFile_ReturnsCorrectRecords() {
        try {
            censusAnalyser = new ClassAnalyser();
            Map numOfRecords = censusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE);
            Assert.assertEquals(51, numOfRecords.size());
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusCSVFile_Return_MaximumPopulationState() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE);
            String fileName = "./src/test/resources/UsStateMaximumPopulationCensusJson.json";
            String sortCensusData = censusAnalyser.sortAndWrite(POPULATION, fileName);
            USCensusDataCSV[] usCensusCSV = new Gson().fromJson(sortCensusData, USCensusDataCSV[].class);
            Assert.assertEquals("California", usCensusCSV[0].state);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusCSVFile_Return_MaximumPopulationState() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE);
            String fileName = "./src/test/resources/UsStateMaximumPopulationCensusJson.json";
            String sortCensusData = censusAnalyser.sortAndWrite(POPULATION, fileName);
            USCensusDataCSV[] usCensusCSV = new Gson().fromJson(sortCensusData, USCensusDataCSV[].class);
            Assert.assertEquals("California", usCensusCSV[0].state);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaAndUSCensusCSVFilePath_ReturnsJsonObjectWithMaxPopulationDensity() throws CSVAnalyserException {
        try {
            censusAnalyser = new ClassAnalyser();
            String mostDenseState = censusAnalyser.getMostDenseState(INDIA_STATE_CENSUS_CSV_FILE, US_CENSUS_CSV_FILE);
            Assert.assertEquals("{" +
                            "\"stateId\":\"DC\"," +
                            "\"state\":\"District of Columbia\"" +
                            ",\"totalArea\":177.0," +
                            "\"population\":601723," +
                            "\"populationDensity\":3805.61," +
                            "\"housingUnits\":18.88," +
                            "\"waterArea\":296719.0" +
                            "}"
                    , mostDenseState);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void giveIndianCensusData_WhenSortOnStateCode_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE, INDIA_STATE_CODE_CSV_FILE);
            String fileName = "./src/test/resources/IndiaCensusDataArea.json";
            String sortCensusData = censusAnalyser.sortAndWrite(STATE_CODE, fileName);
            IndiaStateCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("AP", indiaCensusCSV[0].stateCode);
        } catch (CSVAnalyserException e) {
        }
    }

    @Test
    public void givenWrongCountryName_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(PAK, INDIA_STATE_CENSUS_CSV_FILE);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INVALID_COUNTRY, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongStateCodeFilePath_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, WRONG_CENSUS_CSV_FILE_PATH, US_CENSUS_CSV_FILE);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithStateCodeFileWithWrongHeader_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(INDIA, INDIA_STATE_CENSUS_CSV_FILE
                    , "./src/test/resources/StateCodeWithWrongHeader.csv");
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INVALID_HEADER_AND_DELIMITER
                    , e.type);
        }
    }

    @Test
    public void givenUSCensusData_IfFileEmptyShouldThrowException() {
        censusAnalyser = new ClassAnalyser();
        try {
            censusAnalyser.loadCensusData(US,"src/test/resources/USCensusEmptyFile.csv");
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.EMPTY_FILE, e.type);
        }
    }

    @Test
    public void givenUSCensusData_WithWrongSortingParameter_ShouldThrowException() {
        try {
            censusAnalyser = new ClassAnalyser();
            censusAnalyser.loadCensusData(US, US_CENSUS_CSV_FILE);
            String fileName = "./src/test/resources/UsStateMaximumPopulationCensusJson.json";
            String sortCensusData = censusAnalyser.sortAndWrite(SOME_WRONG_PARAMETER, fileName);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INVALID_PARAMETER, e.type);
        }
    }
}