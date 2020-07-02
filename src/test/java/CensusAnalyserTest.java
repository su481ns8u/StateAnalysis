import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCensus;
import com.indianCensusAnalyser.services.ClassAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_EXTENSION = "src/test/resources/IndiaStateCensusData.json";
    private static final String CSV_WITH_WRONG_DELIMITER = "src/test/resources/IndiaStateCensusDataWithDelIssue.csv";
    private static final String CSV_WITH_WRONG_HEADER = "src/test/resources/IndiaStateCensusDataWithHeadIssue.csv";

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
            censusAnalyser.loadIndianStateData(INDIA_STATE_CSV_FILE_PATH, CSVStateCensus.class);
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
            censusAnalyser.loadIndianStateDataList(INDIA_STATE_CSV_FILE_PATH, CSVStateCensus.class);
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
}
