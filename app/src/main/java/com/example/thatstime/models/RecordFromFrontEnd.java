package com.example.thatstime.models;

public class RecordFromFrontEnd {
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private boolean showGroupList;
    private boolean yourSelf;
    private String creator;
    private String selectedObject;
    private int importance;
    private int hour;
    private int minute;
    private String recordName;
    private String recordContent;

    // Getters and setters
    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public boolean isShowGroupList() {
        return showGroupList;
    }

    public void setShowGroupList(boolean showGroupList) {
        this.showGroupList = showGroupList;
    }

    public boolean isYourSelf() {
        return yourSelf;
    }

    public void setYourSelf(boolean yourSelf) {
        this.yourSelf = yourSelf;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(String selectedObject) {
        this.selectedObject = selectedObject;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }
}

