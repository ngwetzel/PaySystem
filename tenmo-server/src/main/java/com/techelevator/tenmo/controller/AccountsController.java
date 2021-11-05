package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("iaAuthenticated()")
public class AccountsController {
    private TransferDAO transferDao;
    private AccountsDao accountsDao;
    private UserDao userDao;


    @RequestMapping(path = "balance/{userID}", method = RequestMethod.GET)
    public BigDecimal getBalanceFromUserID(@PathVariable Long userID){
        return accountsDao.getBalanceFromUserID(userID);
    }

    @RequestMapping(path = "balance/{accountID}", method = RequestMethod.GET)
    public BigDecimal allTransfersByTransferID(@PathVariable Long accountID){
        return accountsDao.getBalanceFromAccountID(accountID);
    }

}
