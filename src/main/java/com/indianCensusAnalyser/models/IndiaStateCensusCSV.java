package com.indianCensusAnalyser.models;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCensusCSV extends Object {
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;

    public String stateCode;

    public IndiaStateCensusCSV() {
    }

    public IndiaStateCensusCSV(String stateCode, String state, int areaInSqKm, int densityPerSqKm, int totalArea, int population) {
        this.stateCode = stateCode;
        this.state = state;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;
        this.population = population;
    }
}
