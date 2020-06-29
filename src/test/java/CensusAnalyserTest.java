import com.indianCensusAnalyser.exceptions.CensusAnalyserException;
import com.indianCensusAnalyser.services.StateCensusAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_EXTENSION = "src/test/resources/IndiaStateCensusData.json";

    @Test
    public void givenIndianCensusCSVFileReturnCorrectRecords() throws CensusAnalyserException {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int numOfRecords = stateCensusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        StateCensusAnalyzer censusAnalyser = new StateCensusAnalyzer();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CensusAnalyserException.class);
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        StateCensusAnalyzer censusAnalyser = new StateCensusAnalyzer();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CensusAnalyserException.class);
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_TYPE_INCORRECT,e.type);
        }
    }
}
