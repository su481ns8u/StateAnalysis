import com.google.gson.Gson;
import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCensus;
import com.indianCensusAnalyser.models.CSVStateCode;
import com.indianCensusAnalyser.services.ClassAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Comparator;

import static com.indianCensusAnalyser.exceptions.CSVAnalyserException.ExceptionType.EMPTY_CSV;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_EXTENSION = "src/test/resources/IndiaStateCensusData.json";
    private static final String CSV_WITH_WRONG_DELIMITER = "src/test/resources/IndiaStateCensusDataWithDelIssue.csv";
    private static final String CSV_WITH_WRONG_HEADER = "src/test/resources/IndiaStateCensusDataWithHeadIssue.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_EMPTY = "src/test/resources/IndiaStateCensusDataEmpty.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH_EMPTY = "src/test/resources/IndiaStateCodeEmpty.csv";

    @Test
    public void givenIndianCensusCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndianStateData(INDIA_CENSUS_CSV_FILE_PATH, CSVStateCensus.class);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(INDIA_STATE_CODE_CSV_FILE_PATH, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(WRONG_CSV_FILE_EXTENSION, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
        try {
            ClassAnalyser censusAnalyser = new ClassAnalyser();
            int numOfRecords = censusAnalyser.loadIndianStateData(CSV_WITH_WRONG_DELIMITER, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(CSV_WITH_WRONG_HEADER, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            System.out.println(e);
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndianCensusListCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndianStateDataList(INDIA_CENSUS_CSV_FILE_PATH, CSVStateCensus.class);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndiaCensusDataList_WithWrongFileExtension_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(WRONG_CSV_FILE_EXTENSION, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusDataList_WithWrongFile_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(INDIA_STATE_CODE_CSV_FILE_PATH, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            System.out.println(e);
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusDataList_WithWrongHeader_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(CSV_WITH_WRONG_HEADER, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedStateData = null;
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            sortedStateData = classAnalyser.getSortedStateCensusData(INDIA_CENSUS_CSV_FILE_PATH,
                    Comparator.comparing(CSVStateCensus::getState));
            CSVStateCensus[] censusCsv = new Gson().fromJson(sortedStateData, CSVStateCensus[].class);
            Assert.assertTrue(censusCsv[0].state.equals("Andhra Pradesh") &&
                    censusCsv[censusCsv.length - 1].state.equals("West Bengal"));
        } catch (CSVAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenFileEmpty_ShouldReturnException() {
        String sortedStateData = null;
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            sortedStateData = classAnalyser.getSortedStateCensusData(INDIA_CENSUS_CSV_FILE_PATH_EMPTY,
                    Comparator.comparing(CSVStateCensus::getState));
            CSVStateCensus[] censusCsv = new Gson().fromJson(sortedStateData, CSVStateCensus[].class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(EMPTY_CSV,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        String sortedStateData = null;
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            sortedStateData = classAnalyser.getSortedStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH,
                    Comparator.comparing(CSVStateCode::getStateCode));
            CSVStateCode[] censusCsv = new Gson().fromJson(sortedStateData, CSVStateCode[].class);
            Assert.assertTrue(censusCsv[0].stateCode.equals("AD") &&
                    censusCsv[censusCsv.length - 1].stateCode.equals("WB"));
        } catch (CSVAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenFileEmpty_ShouldReturnException() {
        String sortedStateData = null;
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            sortedStateData = classAnalyser.getSortedStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH_EMPTY,
                    Comparator.comparing(CSVStateCode::getStateCode));
            CSVStateCode[] censusCsv = new Gson().fromJson(sortedStateData, CSVStateCode[].class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(EMPTY_CSV,e.type);
        }
    }
}
