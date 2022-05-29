package com.example.moneydesk.ui.mainfragments;

import java.math.BigDecimal;

public class Operation {
    private String operationCategory;
    private BigDecimal operationAmount;
    private String operationCheck;
    private String operationDate;
    private int Id;
    public Operation(int id, String category, BigDecimal amount,String check,String date) {
        operationCategory = category;
        operationAmount = amount;
        operationCheck = check;
        operationDate = date;
        Id = id;
    }

    public String getCategory() {
        return operationCategory;
    }

    public BigDecimal getAmount() {
        return operationAmount;
    }

    public String getCheck() {
        return operationCheck;
    }

    public String getDate() {
        return operationDate;
    }

    public int getID() {
        return Id;
    }

    public void setCategory(String operationCategory) {
        this.operationCategory = operationCategory;
    }

    public void setAmount(BigDecimal amount) {
        this.operationAmount = amount;
    }

    public void setCheck(String operationCheck) {
        this.operationCheck = operationCheck;
    }

    public void setOperationCategory(String operationDate) {
        this.operationDate = operationDate;
    }

    public void setLastId(int lastId) {
        this.Id = lastId;
    }
}