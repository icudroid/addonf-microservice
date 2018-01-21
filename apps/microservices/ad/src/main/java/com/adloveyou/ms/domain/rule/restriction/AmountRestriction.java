package com.adloveyou.ms.domain.rule.restriction;

import com.adloveyou.ms.domain.IMetaData;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.AMOUNT_RULE)
public class AmountRestriction extends AdRestriction {
    @Column(name = IMetaData.ColumnMetadata.AdRule.AMOUNT)
    private Double amount;
    @Column(name = IMetaData.ColumnMetadata.AdRule.INITIAL_AMOUNT)
    private Double initialAmount;
    @Column(name = IMetaData.ColumnMetadata.AdRule.INSERTED)
    @Temporal(TemporalType.TIMESTAMP)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmountRestriction)) return false;
        if (!super.equals(o)) return false;

        AmountRestriction that = (AmountRestriction) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AmountRule{" +
            "amount=" + amount +
            '}';
    }
}
