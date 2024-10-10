package com.example.thatstime.models;

public class CertainRecord {
    private String relatedObject;

    private boolean forYourSelf;

    private boolean isGroup;

    private int year;

    private int month;

    private int day;

    // Getter and Setter for 'relatedObject'
    public String getRelatedObject() {
        return relatedObject;
    }

    public void setRelatedObject(String relatedObject) {
        this.relatedObject = relatedObject;
    }

    // Getter and Setter for 'forYourSelf'
    public boolean isForYourSelf() {
        return forYourSelf;
    }

    public void setForYourSelf(boolean forYourSelf) {
        this.forYourSelf = forYourSelf;
    }

    // Getter and Setter for 'isGroup'
    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    // Getter and Setter for 'year'
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Getter and Setter for 'month'
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    // Getter and Setter for 'day'
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
