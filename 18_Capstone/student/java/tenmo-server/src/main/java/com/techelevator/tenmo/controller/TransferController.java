package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listCurrentUsers() {
        return transferDao.getUsersList();
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer createTransfer(Principal principal, @Valid @RequestBody Transfer transfer ) {
        Account fromAccount = accountDao.getAccount(userDao.findIdByUsername(principal.getName()));
        accountDao.subtractFromBalance(transfer.getAmount(), fromAccount.getAccountId());

        Account toAccount = accountDao.getAccount(transfer.getAccountTo());
        accountDao.addToBalance(transfer.getAmount(), toAccount.getAccountId());

        return transferDao.createTransfer(transfer);
    }

}
