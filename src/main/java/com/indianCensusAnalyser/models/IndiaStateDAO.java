package com.indianCensusAnalyser.models;

public class IndiaStateDAO {
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

    public IndiaStateDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        this.state = indiaStateCensusCSV.state;
        this.population = indiaStateCensusCSV.population;
        this.areaInSqKm = indiaStateCensusCSV.areaInSqKm;
        this.densityPerSqKm = indiaStateCensusCSV.densityPerSqKm;
    }

    public IndiaStateDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        this.stateCode = indiaStateCodeCSV.stateCode;
        this.srNo = indiaStateCodeCSV.srNo;
        this.stateName = indiaStateCodeCSV.stateName;
        this.tin = indiaStateCodeCSV.tin;
    }

    public IndiaStateDAO(USCensusDataCSV usCensusDataCSV) {
        this.stateId = usCensusDataCSV.stateId;
        this.state = usCensusDataCSV.state;
        this.population = usCensusDataCSV.population;
        this.populationDensity = usCensusDataCSV.populationDensity;
        this.housingDensity = usCensusDataCSV.housingDensity;
        this.housingUnits = usCensusDataCSV.housingUnits;
        this.totalArea = usCensusDataCSV.totalArea;
        this.waterArea = usCensusDataCSV.waterArea;
        this.landArea = usCensusDataCSV.landArea;
    }
}
