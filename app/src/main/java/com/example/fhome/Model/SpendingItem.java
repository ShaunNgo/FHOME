package com.example.fhome.Model;

public class SpendingItem {
    private int id;
    private String spendName;
    private String money;
    private String dateSpend;

    public SpendingItem(int id, String spendName, String money, String dateSpend) {
        this.id = id;
        this.spendName = spendName;
        this.money = money;
        this.dateSpend = dateSpend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpendName() {
        return spendName;
    }

    public void setSpendName(String spendName) {
        this.spendName = spendName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDateSpend() {
        return dateSpend;
    }

    public void setDateSpend(String dateSpend) {
        this.dateSpend = dateSpend;
    }
}
