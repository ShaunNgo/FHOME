package com.example.fhome.Model;

public class SpendingPortFolioItem {
    int id;
    String spendingPortFolioName;

    public SpendingPortFolioItem(int id, String spendingPortFolioName) {
        this.id = id;
        this.spendingPortFolioName = spendingPortFolioName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpendingPortFolioName() {
        return spendingPortFolioName;
    }

    public void setSpendingPortFolioName(String spendingPortFolioName) {
        this.spendingPortFolioName = spendingPortFolioName;
    }
}
