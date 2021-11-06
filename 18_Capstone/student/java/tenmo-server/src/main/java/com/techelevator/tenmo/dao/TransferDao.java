package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

    List<User> getUsersList();

    Transfer getTransfer(int transferId);

    Transfer createTransfer(Transfer transfer);

    List<Transfer> getTransfersByAccountId(int accountId);

}
