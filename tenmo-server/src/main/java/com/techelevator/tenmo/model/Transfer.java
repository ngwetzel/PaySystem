package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    Long transferID;
    BigDecimal amount;
    Long accountID;
    Long accountToID;

    public Long getAccountToID() {
        return accountToID;
    }

    public void setAccountToID(Long accountToID) {
        this.accountToID = accountToID;
    }

    public Long getTranferTypeID() {
        return tranferTypeID;
    }

    public void setTranferTypeID(Long tranferTypeID) {
        this.tranferTypeID = tranferTypeID;
    }

    public Long getTransferStatusID() {
        return transferStatusID;
    }

    public void setTransferStatusID(Long transferStatusID) {
        this.transferStatusID = transferStatusID;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    Long userID;
    Long tranferTypeID;
    Long transferStatusID;
    Long accountFrom;

    public Long getTransferID() {
        return transferID;
    }

    public void setTransferID(Long transferID) {
        this.transferID = transferID;
    }

    public BigDecimal getTransferAmount() {
        return amount;
    }

    public void setTransferAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public Long getDestinationAccountID() {
        return accountToID;
    }

    public void setDestinationAccountID(Long destinationAccountID) {
        accountToID = destinationAccountID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
