package com.walter.handyestimate.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.util.Objects;

import static com.walter.handyestimate.data.model.EstimateMathContext.MATH_CONTEXT;

public class EstimateLineItem {

    private String description;
    private int quantity;
    private BigDecimal rate;
    private BigDecimal cost;

    public EstimateLineItem() {
        this.rate = BigDecimal.ZERO;
        this.cost = BigDecimal.ZERO;
    }

    public EstimateLineItem(String description, int quantity, BigDecimal rate) {
        this.description = description;
        this.quantity = quantity;
        this.rate = rate;
        this.cost = updateCost();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    private BigDecimal updateCost() {
        return rate.multiply(BigDecimal.valueOf(quantity), MATH_CONTEXT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstimateLineItem)) return false;
        EstimateLineItem that = (EstimateLineItem) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getRate(), that.getRate()) &&
                Objects.equals(getCost(), that.getCost());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getQuantity(), getRate(), getCost());
    }
}
