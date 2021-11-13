package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferDTO {

    @Min(value = 1, message = "The field userIdTo is required.")
    private int userIdTo;
    @Positive(message = "The amount should be greater than 0.")
    private BigDecimal amount;

    public int getUserIdTo() {
        return userIdTo;
    }
    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
