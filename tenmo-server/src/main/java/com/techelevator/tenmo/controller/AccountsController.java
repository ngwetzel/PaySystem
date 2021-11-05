package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@PreAuthorize("iaAuthenticated()")
public class AccountsController {
    private AccountsDao accountsDao;


    @RequestMapping(path = "balance/{userID}", method = RequestMethod.GET)
    public BigDecimal getBalanceFromUserID(@PathVariable Long userID){
        return accountsDao.getBalanceFromUserID(userID);
    }

    @RequestMapping(path = "balance/{accountID}", method = RequestMethod.GET)
    public BigDecimal allTransfersByTransferID(@PathVariable Long accountID){
        return accountsDao.getBalanceFromAccountID(accountID);
    }

}
