package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDao {

    Transfers[] allTransfers(String username);

    Transfers transferLookupWithTransferID(Long transferID);

    Object tenmoPay(String userFrom, String UserTo, BigDecimal amount);

    List<String> userList();

}


