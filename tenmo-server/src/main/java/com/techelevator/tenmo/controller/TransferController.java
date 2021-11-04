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
public class TransferController {
    private TransferDAO transferDao;
    private AccountsDao accountsDao;
    private UserDao userDao;


    //don't know if I should do user or account id
    @RequestMapping(path = "account/{userID}/transfers", method = RequestMethod.GET)
    public List<Transfers>listAllTransfersForAccount(@PathVariable Long userID){
        return transferDao.allTransfers(userID);
    }

    @RequestMapping(path = "transfers/{transferID}", method = RequestMethod.GET)
    public Transfers transferLookupWithTransferID(@PathVariable Long transferID){
        return transferDao.transferLookupWithTransferID(transferID);
    }

    @RequestMapping(path = "pay" , method = RequestMethod.POST)
    public Object tenmoPay(@RequestBody Transfers transfer){

        return transferDao.tenmoPay(transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
    }
//    List<String> userList();

}
