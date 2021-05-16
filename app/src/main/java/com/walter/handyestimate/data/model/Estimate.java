package com.walter.handyestimate.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Estimate {

    // Company Information
    private String companyName;
    private String companyAddress;

    // Customer Information
    private String customerName;
    private String customerAddress;

    private LocalDate date;
    private UUID estimateId;
    private String estimateDescription;
    private EstimateTable estimateTable;

    public Estimate() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Estimate(String companyName, String companyAddress, String customerName,
                    String customerAddress, UUID estimateId,
                    String estimateDescription, EstimateTable estimateTable) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.date = LocalDate.now();
        this.estimateId = estimateId;
        this.estimateDescription = estimateDescription;
        this.estimateTable = estimateTable;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(UUID estimateId) {
        this.estimateId = estimateId;
    }

    public String getEstimateDescription() {
        return estimateDescription;
    }

    public void setEstimateDescription(String estimateDescription) {
        this.estimateDescription = estimateDescription;
    }

    public EstimateTable getEstimateTable() {
        return estimateTable;
    }

    public void setEstimateTable(EstimateTable estimateTable) {
        this.estimateTable = estimateTable;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estimate)) return false;
        Estimate estimate = (Estimate) o;
        return Objects.equals(getCompanyName(), estimate.getCompanyName()) &&
                Objects.equals(getCompanyAddress(), estimate.getCompanyAddress()) &&
                Objects.equals(getCustomerName(), estimate.getCustomerName()) &&
                Objects.equals(getCustomerAddress(), estimate.getCustomerAddress()) &&
                Objects.equals(getDate(), estimate.getDate()) &&
                Objects.equals(getEstimateId(), estimate.getEstimateId()) &&
                Objects.equals(estimateDescription, estimate.estimateDescription) &&
                Objects.equals(estimateTable, estimate.estimateTable);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName(), getCompanyAddress(), getCustomerName(), getCustomerAddress(), getDate(), getEstimateId(), estimateDescription, estimateTable);
    }
}
