package com.walter.handyestimate.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class TestModel {

    private String companyName;

    public TestModel(String companyName) {
        this.companyName = companyName;
    }

    // public String getUserId() {
    //     return userId;
    // }

    public String getDisplayName() {
        return companyName;
    }
}