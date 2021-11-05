package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<User> getUsersList();

    Transfer getTransfer(int transferId);

    Transfer createTransfer(Transfer transfer);

    Transfer updateBalancesAfterTransfer(int userFrom, int userTo, BigDecimal amount);

}
