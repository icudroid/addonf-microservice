package com.adloveyou.ms.service.dto.rule.restriction;

import java.util.Date;

public class AmountRestrictionDTO extends AdRestrictionDTO {
    private Double amount;
    private Double initialAmount;
    private Date inserted;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }
}
