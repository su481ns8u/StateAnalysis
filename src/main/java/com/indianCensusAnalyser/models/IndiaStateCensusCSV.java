package com.indianCensusAnalyser.models;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCensusCSV extends Object{

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;

    public String getState() {
        return state;
    }

    public int getPopulation() {
        return population;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}