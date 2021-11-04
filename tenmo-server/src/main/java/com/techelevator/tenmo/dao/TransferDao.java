package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO {

    List<Transfers> allTransfers(Long userID);
    Transfers transferLookupWithTransferID(Long transferID);
    Object tenmoPay(Long accountFrom, Long accountTo, BigDecimal amount);
    List<String> userList();






    Long destinationAccountLookup(String username);




}
