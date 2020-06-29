import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.CSVStateCensus;
import com.indianCensusAnalyser.models.CSVStateCode;
import com.indianCensusAnalyser.services.ClassAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class StateCodeAnalysisTest {
    private static final String INDIA_STATE_CSV_FILE_PATH = "src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndiaData(INDIA_STATE_CSV_FILE_PATH, CSVStateCode.class);
        Assert.assertEquals(38, numOfRecords);
    }
}
