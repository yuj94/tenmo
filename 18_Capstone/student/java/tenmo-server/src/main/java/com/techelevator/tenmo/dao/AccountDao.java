package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalanceFromId(int userId);

    Account getAccount(int userId);

    int subtractFromBalance(BigDecimal subtractAmount, int accountFromId);

    void addToBalance(BigDecimal addAmount, int accountFromId);

}
