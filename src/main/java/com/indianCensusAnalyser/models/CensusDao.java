package com.indianCensusAnalyser.models;

public class CensusDao {
    public double landArea;
    public double waterArea;
    public double populationDensity;
    public String tin;
    public String stateName;
    public String srNo;
    public String stateCode;
    public double population;
    public int areaInSqKm;
    public int densityPerSqKm;
    public String state;
    public String stateId;
    public double housingUnits;
    public double totalArea;
    public double density;
    public double housingDensity;

    public CensusDao(IndiaStateCensusCSV indiaStateCensusCSV) {
        this.state = indiaStateCensusCSV.state;
        this.population = indiaStateCensusCSV.population;
        this.totalArea = indiaStateCensusCSV.areaInSqKm;
        this.populationDensity = indiaStateCensusCSV.densityPerSqKm;
    }

    public CensusDao(IndiaStateCodeCSV indiaStateCodeCSV) {
        this.stateCode = indiaStateCodeCSV.stateCode;
        this.srNo = indiaStateCodeCSV.srNo;
        this.stateName = indiaStateCodeCSV.stateName;
        this.tin = indiaStateCodeCSV.tin;
    }

    public CensusDao(USCensusDataCSV usCensusDataCSV) {
        this.stateCode = usCensusDataCSV.stateId;
        this.state = usCensusDataCSV.state;
        this.population = usCensusDataCSV.population;
        this.populationDensity = usCensusDataCSV.populationDensity;
        this.totalArea = usCensusDataCSV.totalArea;
    }
}
