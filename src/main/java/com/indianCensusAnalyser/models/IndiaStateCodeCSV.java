package com.indianCensusAnalyser.models;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    public IndiaStateCodeCSV() {
    }

    public IndiaStateCodeCSV(String stateName, String stateCode) {
        this.stateName = stateName;
        this.stateCode = stateCode;
    }
}
