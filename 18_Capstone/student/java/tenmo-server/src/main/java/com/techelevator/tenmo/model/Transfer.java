package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    @Min(value = 1, message = "The field userFrom is required.")
    private int userFrom;
    @Min(value = 1, message = "The field userTo is required.")
    private int userTo;
    @Positive(message = "The amount should be greater than 0.")
    private BigDecimal amount;

    public int getTransferId() {
        return transferId;
    }
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }
    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }
    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

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

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int userFrom, int userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

}
