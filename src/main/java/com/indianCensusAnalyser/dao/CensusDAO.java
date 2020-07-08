package com.indianCensusAnalyser.dao;


import com.indianCensusAnalyser.models.IndiaStateCensusCSV;
import com.indianCensusAnalyser.models.USCensusDataCSV;
import com.indianCensusAnalyser.services.ClassAnalyser;

public class CensusDAO {
    public double populationDensity;
    public double totalArea;
    public String state;
    public int population;
    public String stateCode;
    public double totalAreaUS;
    public double housingUnits;
    public double waterArea;

    public CensusDAO(IndiaStateCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.totalArea = indiaCensusCSV.areaInSqKm;
        this.populationDensity = indiaCensusCSV.densityPerSqKm;
        this.population = indiaCensusCSV.population;
    }

    public CensusDAO(USCensusDataCSV censusCSV) {
        this.stateCode = censusCSV.stateId;
        this.state = censusCSV.state;
        this.totalArea = censusCSV.totalArea;
        this.population = censusCSV.population;
        this.populationDensity = censusCSV.populationDensity;
        this.waterArea = censusCSV.waterArea;
        this.housingUnits = censusCSV.housingUnits;
    }

    public Object getCensusDTO(ClassAnalyser.Country country) {
        if (country.equals(ClassAnalyser.Country.US))
            return new USCensusDataCSV
                    (stateCode, state, totalArea, population, populationDensity, waterArea, housingUnits);
        return new IndiaStateCensusCSV
                (stateCode, state, (int) totalArea, (int) populationDensity, (int) totalAreaUS, (int) population);
    }
}