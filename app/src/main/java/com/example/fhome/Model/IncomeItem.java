package com.example.fhome.Model;

public class IncomeItem {
    private int id;
    private String incomeName;
    private String money;
    private String dateIncome;

    public IncomeItem(int id, String incomeName, String money, String dateIncome) {
        this.id = id;
        this.incomeName = incomeName;
        this.money = money;
        this.dateIncome = dateIncome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDateIncome() {
        return dateIncome;
    }

    public void setDateIncome(String dateIncome) {
        this.dateIncome = dateIncome;
    }
}
