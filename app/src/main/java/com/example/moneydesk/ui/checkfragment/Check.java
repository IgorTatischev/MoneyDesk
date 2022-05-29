package com.example.moneydesk.ui.checkfragment;

import java.math.BigDecimal;

public class Check {
    private String checkName;
    private BigDecimal checkAmount;
    private int Id;
    public Check(String name, BigDecimal amount, int id) {
        checkName = name;
        checkAmount = amount;
        Id = id;
    }

    public String getName() {
        return checkName;
    }

    public BigDecimal getAmount() {
        return checkAmount;
    }

    public int getID() {
        return Id;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public void setCheckAmount(BigDecimal amount) {
        this.checkAmount = amount;
    }

    public void setLastId(int lastId) {
        this.Id = lastId;
    }
}
