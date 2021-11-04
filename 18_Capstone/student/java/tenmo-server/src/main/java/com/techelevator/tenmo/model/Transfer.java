package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {

    @Min(value = 1, message = "The field userFrom is required.")
    private int userFrom;

    @Min(value = 1, message = "The field userTo is required.")
    private int userTo;

    @Positive(message = "The amount should be greater than 0.")
    private BigDecimal amount;


    public int getUserFrom() {
        return userFrom;
    }
    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserTo() {
        return userTo;
    }
    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transfer(int userFrom, int userTo, BigDecimal amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

}
