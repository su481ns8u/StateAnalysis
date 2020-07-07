package com.indianCensusAnalyser.models;

import com.opencsv.bean.CsvBindByName;

public class USCensusDataCSV {
    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;

    @CsvBindByName(column = "Housing units", required = true)
    public double housingUnits;

    @CsvBindByName(column = "Water area", required = true)
    public double waterArea;

    public USCensusDataCSV() {
    }

    public USCensusDataCSV(String stateId,
                           String state,
                           double totalArea,
                           int population,
                           double populationDensity,
                           double housingUnits,
                           double waterArea) {
        this.stateId = stateId;
        this.state = state;
        this.totalArea = totalArea;
        this.population = population;
        this.populationDensity = populationDensity;
        this.housingUnits = housingUnits;
        this.waterArea = waterArea;
    }
}
