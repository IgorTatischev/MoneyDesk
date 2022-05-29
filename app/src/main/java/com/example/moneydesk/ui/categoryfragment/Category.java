package com.example.moneydesk.ui.categoryfragment;

public class Category {
    private String categoryName;
    private int Id;
    public Category(int id,String name) {
        categoryName = name;
        Id = id;
    }

    public String getName() {
        return categoryName;
    }

    public int getID() {
        return Id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setLastId(int lastId) {
        this.Id = lastId;
    }
}