package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {

    private int userIdTo;
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
