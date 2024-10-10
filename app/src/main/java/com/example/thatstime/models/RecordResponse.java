package com.example.thatstime.models;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class RecordResponse {
    private boolean success;
    private String message;
    private List<RecordFromFrontEnd> records;

    // Getter and Setter for 'success'
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter and Setter for 'message'
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for 'records'
    public List<RecordFromFrontEnd> getRecords() {
        return records;
    }

    public void setRecords(List<RecordFromFrontEnd> records) {
        this.records = records;
    }
}

