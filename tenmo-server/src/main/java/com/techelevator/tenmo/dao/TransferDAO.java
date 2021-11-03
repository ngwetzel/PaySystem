package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDAO {

    List<Transfer> alltransfers(Long userID);
    Transfer transferLookupWithTransferID(Long transferID);
    Transfer transferFunds(Long userID, Long accountID, double transferAmount, Long destinationAccountID);
    List<String> userList();

    Long destinationAccountLookup(String username);




}
