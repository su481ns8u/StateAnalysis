import com.google.gson.Gson;

import com.indianCensusAnalyser.exceptions.CSVAnalyserException;
import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.IndiaStateCodeCSV;
import com.indianCensusAnalyser.models.USCensusDataCSV;
import com.indianCensusAnalyser.services.ClassAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static com.indianCensusAnalyser.exceptions.CSVAnalyserException.ExceptionType.EMPTY_CSV;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_EXTENSION = "src/test/resources/IndiaStateCensusData.json";
    private static final String CSV_WITH_WRONG_DELIMITER = "src/test/resources/IndiaStateCensusDataWithDelIssue.csv";
    private static final String CSV_WITH_WRONG_HEADER = "src/test/resources/IndiaStateCensusDataWithHeadIssue.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_EMPTY = "src/test/resources/IndiaStateCensusDataEmpty.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH_EMPTY = "src/test/resources/IndiaStateCodeEmpty.csv";
    private static final String US_CENSUS_DATA_CSV_PATH = "src/test/resources/USCensusData.csv";
    private static final String WRONG_CSV_FILE_EXTENSION_FOR_STATE_CODE = "src/test/resources/IndiaStateCode.json";
    private static final String CSV_WITH_WRONG_DELIMITER_FOR_STATE_CODE = "src/test/resources/IndiaStateCodeWithDelIssue.csv";
    private static final String CSV_WITH_WRONG_HEADER_FOR_STATE_CODE = "src/test/resources/IndiaStateCodeWithHeadIssue.csv";


    @Test
    public void givenIndianCensusCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndianStateDataList(INDIA_CENSUS_CSV_FILE_PATH,"India State Census", IndiaStateCensusCSV.class);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(INDIA_STATE_CODE_CSV_FILE_PATH, "India State Census", IndiaStateCensusCSV.class);
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
            censusAnalyser.loadIndianStateDataList(WRONG_CSV_FILE_EXTENSION, "India State Census", IndiaStateCensusCSV.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
        try {
            ClassAnalyser censusAnalyser = new ClassAnalyser();
            int numOfRecords = censusAnalyser.loadIndianStateDataList(CSV_WITH_WRONG_DELIMITER, "India State Census", IndiaStateCensusCSV.class);
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
            censusAnalyser.loadIndianStateDataList(CSV_WITH_WRONG_HEADER, "India State Census", IndiaStateCensusCSV.class);
        } catch (CSVAnalyserException e) {
            System.out.println(e);
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndianCodeCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndianStateDataList
                (INDIA_STATE_CODE_CSV_FILE_PATH,"India State Code", IndiaStateCodeCSV.class);
        Assert.assertEquals(37, numOfRecords);
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList
                    (INDIA_CENSUS_CSV_FILE_PATH,"India State Code", IndiaStateCodeCSV.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileExtension_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(WRONG_CSV_FILE_EXTENSION_FOR_STATE_CODE,"India State Code", IndiaStateCodeCSV.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongDelimiter_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(CSV_WITH_WRONG_DELIMITER_FOR_STATE_CODE,"India State Code", IndiaStateCodeCSV.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongHeader_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(CSV_WITH_WRONG_HEADER_FOR_STATE_CODE,"India State Code", IndiaStateCodeCSV.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndianCensusListCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser.loadIndianStateDataList(INDIA_CENSUS_CSV_FILE_PATH,"India State Census", IndiaStateCensusCSV.class);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndiaCensusDataList_WithWrongFileExtension_ShouldThrowException() {
        ClassAnalyser censusAnalyser = new ClassAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CSVAnalyserException.class);
        try {
            censusAnalyser.loadIndianStateDataList(WRONG_CSV_FILE_EXTENSION,"India State Census", IndiaStateCensusCSV.class);
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
            censusAnalyser.loadIndianStateDataList(INDIA_STATE_CODE_CSV_FILE_PATH, "India State Census", IndiaStateCensusCSV.class);
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
            censusAnalyser.loadIndianStateDataList(CSV_WITH_WRONG_HEADER,"India State Census", IndiaStateCensusCSV.class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(CSVAnalyserException.ExceptionType.WRONG_HEADER_OR_UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser.getSortedIndiaCensusDataByParameters(INDIA_CENSUS_CSV_FILE_PATH,
                    "State");
            IndiaStateCensusCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCensusCSV[].class);
            Assert.assertTrue(censusCsv[0].state.equals("Andhra Pradesh") &&
                    censusCsv[censusCsv.length - 1].state.equals("West Bengal"));
        } catch (CSVAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenFileEmpty_ShouldReturnException() throws IOException {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser
                    .getSortedIndiaCensusDataByParameters(INDIA_CENSUS_CSV_FILE_PATH_EMPTY, "State");
            IndiaStateCensusCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCensusCSV[].class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(EMPTY_CSV, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser
                    .getSortedStateCodeDataOnStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            IndiaStateCodeCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCodeCSV[].class);
            Assert.assertTrue(censusCsv[0].stateCode.equals("AD") &&
                    censusCsv[censusCsv.length - 1].stateCode.equals("WB"));
        } catch (CSVAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenFileEmpty_ShouldReturnException() throws IOException {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser
                    .getSortedStateCodeDataOnStateCode(INDIA_STATE_CODE_CSV_FILE_PATH_EMPTY);
            IndiaStateCodeCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCodeCSV[].class);
        } catch (CSVAnalyserException e) {
            Assert.assertEquals(EMPTY_CSV, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDescending_ShouldReturnSortedResult() {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser.getSortedIndiaCensusDataByParameters(INDIA_CENSUS_CSV_FILE_PATH,
                    "Population");
            IndiaStateCensusCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCensusCSV[].class);
            Assert.assertTrue(censusCsv[0].population == 199812341 &&
                    censusCsv[censusCsv.length - 1].population == 607688);
        } catch (CSVAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensityDescending_ShouldReturnSortedResult() {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser.getSortedIndiaCensusDataByParameters(INDIA_CENSUS_CSV_FILE_PATH,
                    "Density");
            IndiaStateCensusCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCensusCSV[].class);
            Assert.assertTrue(censusCsv[0].densityPerSqKm == 1102 &&
                    censusCsv[censusCsv.length - 1].densityPerSqKm == 50);
        } catch (CSVAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnAreaDescending_ShouldReturnSortedResult() throws IOException {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser.getSortedIndiaCensusDataByParameters(INDIA_CENSUS_CSV_FILE_PATH,
                    "Area");
            IndiaStateCensusCSV[] censusCsv = new Gson().fromJson(sortedStateData, IndiaStateCensusCSV[].class);
            Assert.assertTrue(censusCsv[0].areaInSqKm == 342239 &&
                    censusCsv[censusCsv.length - 1].areaInSqKm == 3702);
        } catch (CSVAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnCorrectRecords() throws CSVAnalyserException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        int numOfRecords = classAnalyser
                .loadIndianStateDataList(US_CENSUS_DATA_CSV_PATH,"US Census Data", USCensusDataCSV.class);
        Assert.assertEquals(51, numOfRecords);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnAreaDescending_ShouldReturnSortedResult() {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser.getSortedUSCensusDataByParameters(US_CENSUS_DATA_CSV_PATH,
                    "Land Area");
            USCensusDataCSV[] censusCsv = new Gson().fromJson(sortedStateData, USCensusDataCSV[].class);
            Assert.assertEquals(1477954.35, censusCsv[0].landArea, 0.0);
        } catch (CSVAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnStateDescending_ShouldReturnSortedResult() {
        try {
            ClassAnalyser classAnalyser = new ClassAnalyser();
            String sortedStateData = classAnalyser.getSortedUSCensusDataByParameters(US_CENSUS_DATA_CSV_PATH,
                    "State");
            USCensusDataCSV[] censusCsv = new Gson().fromJson(sortedStateData, USCensusDataCSV[].class);
            Assert.assertEquals("Alabama", censusCsv[0].state);
        } catch (CSVAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSAndIndiaCensusData_GivesMostPopulasStateFromBoth() throws CSVAnalyserException, IOException {
        ClassAnalyser classAnalyser = new ClassAnalyser();
        String temp = classAnalyser.getMostDenseState(INDIA_CENSUS_CSV_FILE_PATH, US_CENSUS_DATA_CSV_PATH);
        Assert.assertEquals("{" +
                "\"landArea\":158.12," +
                "\"waterArea\":18.88," +
                "\"populationDensity\":3805.61," +
                "\"population\":601723.0," +
                "\"areaInSqKm\":0," +
                "\"densityPerSqKm\":0," +
                "\"state\":\"District of Columbia\"," +
                "\"stateId\":\"DC\"," +
                "\"housingUnits\":296719.0," +
                "\"totalArea\":177.0," +
                "\"density\":0.0," +
                "\"housingDensity" +
                "\":1876.61}"
                ,temp);
    }
}
