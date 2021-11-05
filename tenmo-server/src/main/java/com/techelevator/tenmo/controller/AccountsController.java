package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountsController {
    @Autowired
    private AccountsDao accountsDao;


    @RequestMapping(path = "balance", method = RequestMethod.GET)
    public BigDecimal getBalanceFromUserName(Principal principal){
        return accountsDao.getBalanceFromUserName(principal.getName());
    }

//    @RequestMapping(path = "balance/{accountID}", method = RequestMethod.GET)
//    public BigDecimal allTransfersByTransferID(@PathVariable Long accountID){
//        return accountsDao.getBalanceFromAccountID(accountID);
//    }

}
