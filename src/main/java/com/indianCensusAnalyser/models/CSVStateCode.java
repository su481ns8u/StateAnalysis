package com.indianCensusAnalyser.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CSVStateCode {
    @CsvBindByPosition(position = 0, required = true)
    public String srNo;

    @CsvBindByPosition(position = 1, required = true)
    public String stateName;

    @CsvBindByPosition(position = 2, required = true)
    public String tin;

    @CsvBindByPosition(position = 3, required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "srNo='" + srNo + '\'' +
                ", stateName=" + stateName +
                ", tin=" + tin +
                ", stateCode=" + stateCode +
                '}';
    }
}