package com.example.moneydesk.ui.items;

import java.math.BigDecimal;

public class Operation {
    private String operationCategory;
    private BigDecimal operationAmount;
    private String operationCheck;
    private String operationDate;
    private int Id;
    private int id_check;
    public Operation(int id, String category, BigDecimal amount,int check_id, String check,String date) {
        operationCategory = category;
        operationAmount = amount;
        operationCheck = check;
        operationDate = date;
        id_check = check_id;
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

    public int getCheckID() {
        return id_check;
    }

    public void setCategory(String operationCategory) {
        this.operationCategory = operationCategory;
    }

    public void setLastId(int lastId) {
        this.Id = lastId;
    }
}