package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.UserDao;
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
@Autowired
private UserDao userDao;


    @RequestMapping(path = "balance", method = RequestMethod.GET)
    public BigDecimal getBalanceFromUserName(Principal principal){
        return accountsDao.getBalanceFromUserName(principal.getName());
    }

    @RequestMapping(path = "accounts", method = RequestMethod.GET)
    public Long getID(String user) {
        return userDao.getId(user);
    }

//    @RequestMapping(path = "users", method = RequestMethod.GET)
//    public String getUsernameFromId(Long accountId) {
//        return accountsDao.getUsernameFromAccountId(accountId);
//    }


//    @RequestMapping(path = "balance/{accountID}", method = RequestMethod.GET)
//    public BigDecimal allTransfersByTransferID(@PathVariable Long accountID){
//        return accountsDao.getBalanceFromAccountID(accountID);
//    }

}
