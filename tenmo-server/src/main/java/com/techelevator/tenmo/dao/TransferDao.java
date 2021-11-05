package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfers> allTransfers(Long userID);
    Transfers transferLookupWithTransferID(Long transferID);
    Object tenmoPay(Long userFrom, Long UserTo, BigDecimal amount);
    List<String> userList();//shouldn't it be List<User>




}