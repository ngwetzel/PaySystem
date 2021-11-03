package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
     List<Transfers> allTransfers(Long userID);
     Transfers transferLookupByTransferID(Long transferID);
     Object tenmoPay(Long accountFrom, Long accountTo, BigDecimal amount);

}
