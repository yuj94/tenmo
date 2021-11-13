package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private UserDao userDao;
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(UserDao userDao, TransferDao transferDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/getUsers", method = RequestMethod.GET)
    public List<User> getUsers() {
        //return transferDao.getUsersList();
        return userDao.findAll();
    }

    @RequestMapping(path = "/createTransfer", method = RequestMethod.POST)
    public void createTransfer(Principal principal, @Valid @RequestBody TransferDTO transferDTO ) throws Exception {
        int fromAccountId = accountDao.getAccountIdByUserId(userDao.findIdByUsername(principal.getName()));
        int toAccountIdFromUserId = accountDao.getAccountIdByUserId(transferDTO.getUserIdTo());

        Transfer transfer = new Transfer();
        transfer.setAccountFrom(fromAccountId);
        transfer.setAccountTo(toAccountIdFromUserId);
        transfer.setAmount(transferDTO.getAmount());

        Account toAccount = accountDao.getAccountByAccountId(transfer.getAccountTo());
        Account fromAccount = accountDao.getAccountByAccountId(fromAccountId);

        if (toAccount == null) {
            throw new Exception("The account you are trying to send money to does not exist. Try again.");
        }

        if (fromAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new Exception("You do not have enough funds for this transfer. Try again.");
        }

        transferDao.createTransfer(transfer);
        accountDao.subtractFromBalance(transfer.getAmount(), fromAccount.getAccountId());
        accountDao.addToBalance(transfer.getAmount(), toAccount.getAccountId());
    }

    @RequestMapping(path = "/getTransfers", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        int accountId = accountDao.getAccountIdByUserId(userId);

        return transferDao.getTransfersByAccountId(accountId);
    }

    @RequestMapping(path = "/getTransfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfers(@PathVariable int transferId) {
        return transferDao.getTransfer(transferId);
    }

}
