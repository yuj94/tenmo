package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
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
        return transferDao.getUsersList();
    }

    @RequestMapping(path = "/createTransfer", method = RequestMethod.POST)
    public void createTransfer(Principal principal, @Valid @RequestBody Transfer transfer ) throws Exception {
        //Account fromAccount = accountDao.getAccountByUserId(userDao.findIdByUsername(principal.getName()));
        Account fromAccount = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account toAccount = accountDao.getAccountByAccountId(transfer.getAccountTo());

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

        Account account = accountDao.getAccountByUserId(userId);

        return transferDao.getTransfersByAccountId(account.getAccountId());
    }

    @RequestMapping(path = "/getTransfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfers(@PathVariable int transferId) {
        return transferDao.getTransfer(transferId);
    }

}
