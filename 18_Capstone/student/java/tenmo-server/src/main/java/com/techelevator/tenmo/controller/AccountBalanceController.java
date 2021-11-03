package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountBalanceDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
public class AccountBalanceController {

    private AccountBalanceDao accountBalanceDao;
    private UserDao userDao;

    public AccountBalanceController(AccountBalanceDao accountBalanceDao, UserDao userDao) {
        this.accountBalanceDao = accountBalanceDao;
        this.userDao = userDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal) {

        int userId = userDao.findIdByUsername(principal.getName());

        return accountBalanceDao.getCurrentBalance(userId);

    }

}
