package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private int userIdFrom;
    private String userNameFrom;
    private int userIdTo;
    private String userNameTo;
    private String transferTypeDesc;
    private String transferStatusDesc;

    public Transfer() {

    }

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

    public int getAccountFrom() {
        return accountFrom;
    }
    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }
    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getUserIdFrom() {
        return userIdFrom;
    }
    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public String getUserNameFrom() {
        return userNameFrom;
    }
    public void setUserNameFrom(String userNameFrom) {
        this.userNameFrom = userNameFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }
    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }

    public String getUserNameTo() {
        return userNameTo;
    }
    public void setUserNameTo(String userNameTo) {
        this.userNameTo = userNameTo;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }
    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }
    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    @Override
    public String toString() {
        return  "\n--------------------------------------------" +
                "\nTransfer Details" +
                "\n--------------------------------------------" +
                "\n Id: " + transferId +
                "\n From: " + accountFrom +
                "\n To: " + accountTo +
                "\n Type: " + transferTypeId +
                "\n Status: " + transferStatusId +
                "\n Amount: $" + amount;
    }

}
