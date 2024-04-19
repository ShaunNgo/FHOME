package com.example.fhome.Model;

public class IncomePortFolioItem {
    int id;
    String incomePortFolioName;

    public IncomePortFolioItem(int id, String incomePortFolioName) {
        this.id = id;
        this.incomePortFolioName = incomePortFolioName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncomePortFolioName() {
        return incomePortFolioName;
    }

    public void setIncomePortFolioName(String incomePortFolioName) {
        this.incomePortFolioName = incomePortFolioName;
    }
}
