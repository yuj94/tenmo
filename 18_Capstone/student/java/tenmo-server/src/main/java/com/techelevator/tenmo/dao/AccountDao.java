package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalanceFromUserId(int userId);

    Account getAccountByUserId(int userId);

    Account getAccountByAccountId(int accountId);

    void subtractFromBalance(BigDecimal subtractAmount, int accountFromId);

    void addToBalance(BigDecimal addAmount, int accountToId);

}
