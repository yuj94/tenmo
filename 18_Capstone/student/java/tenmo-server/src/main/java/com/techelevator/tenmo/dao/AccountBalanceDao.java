package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountBalanceDao {

    BigDecimal getCurrentBalance(int userId);

}
