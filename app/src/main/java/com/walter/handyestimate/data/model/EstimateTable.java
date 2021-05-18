package com.walter.handyestimate.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.walter.handyestimate.data.model.EstimateMathContext.MATH_CONTEXT;

public class EstimateTable {

    private List<EstimateLineItem> estimateLineItemList;
    private BigDecimal totalCost;

    public EstimateTable() {
        this.totalCost = BigDecimal.ZERO;
    }

    public EstimateTable(List<EstimateLineItem> estimateLineItemList) {
        this.estimateLineItemList = estimateLineItemList;
        updateTotalCost();
    }

    public EstimateTable(List<EstimateLineItem> estimateLineItemList, BigDecimal totalCost) {
        this.estimateLineItemList = estimateLineItemList;
        this.totalCost = totalCost;
    }

    public List<EstimateLineItem> getEstimateLineItemList() {
        return estimateLineItemList;
    }

    public void setEstimateLineItemList(List<EstimateLineItem> estimateLineItemList) {
        this.estimateLineItemList = estimateLineItemList;
        updateTotalCost();
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    private void updateTotalCost() {
        this.totalCost = BigDecimal.ZERO;
        for (EstimateLineItem item : estimateLineItemList) {
            this.totalCost = totalCost.add(item.getCost(), MATH_CONTEXT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstimateTable)) return false;
        EstimateTable that = (EstimateTable) o;
        return Objects.equals(getEstimateLineItemList(), that.getEstimateLineItemList()) &&
                Objects.equals(getTotalCost(), that.getTotalCost());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getEstimateLineItemList(), getTotalCost());
    }
}
