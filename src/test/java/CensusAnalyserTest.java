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
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INCORRECT_FILE_OR_HEADER, e.type);
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
            Assert.assertEquals(CSVAnalyserException.ExceptionType.EXCEPTION_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(CSV_WITH_WRONG_DELIMITER, CSVStateCensus.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.DELIMITER_INCORRECT, e.type);
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
            Assert.assertEquals(CSVAnalyserException.ExceptionType.INCORRECT_FILE_OR_HEADER, e.type);
        }
    }
}
