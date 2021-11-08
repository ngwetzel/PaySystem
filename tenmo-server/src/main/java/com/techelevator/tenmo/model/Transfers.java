package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers {
    private Long transferId;
    private Long transferTypeId;
    private Long transferStatusId;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal amount;


    public void setTransferId(Long transferID) {
        this.transferId = transferID;
    }

    public void setTransferTypeId(Long transferTypeID) {
        this.transferTypeId = transferTypeID;
    }

    public void setTransferStatusId(Long transferStatusID) {
        this.transferStatusId = transferStatusID;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }



    public Long getTransferId() {
        return transferId;
    }
    public void setTransferId(long transferID) {
        this.transferId = transferID;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeID) {
        this.transferTypeId = transferTypeID;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusID){
        this.transferStatusId = transferStatusID;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }
    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }
}
