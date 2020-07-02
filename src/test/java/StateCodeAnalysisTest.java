import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCode;
import com.indianCensusAnalyser.services.ClassAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCodeAnalysisTest {
    private static final String INDIA_STATE_CSV_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_EXTENSION = "src/test/resources/IndiaStateCode.json";
    private static final String CSV_WITH_WRONG_DELIMITER = "src/test/resources/IndiaStateCodeWithDelIssue.csv";
    private static final String CSV_WITH_WRONG_HEADER = "src/test/resources/IndiaStateCodeWithHeadIssue.csv";

    @Test
    public void givenIndianCensusCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndianStateData(INDIA_STATE_CSV_FILE_PATH, CSVStateCode.class);
        Assert.assertEquals(37, numOfRecords);
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(INDIA_CENSUS_CSV_FILE_PATH, CSVStateCode.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(WRONG_CSV_FILE_EXTENSION, CSVStateCode.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongDelimiter_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(CSV_WITH_WRONG_DELIMITER, CSVStateCode.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateData(CSV_WITH_WRONG_HEADER, CSVStateCode.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }
}
