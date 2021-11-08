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
}
