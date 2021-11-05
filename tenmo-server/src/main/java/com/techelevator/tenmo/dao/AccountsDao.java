package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;

public interface AccountsDao {

    BigDecimal getBalance(Long userID);
    BigDecimal depositToBalance(BigDecimal amountToDeposit, Long accountID);
    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long accountID);
    BigDecimal balanceCheck(Long userFromId);
    Accounts findAccountByUsername (String username);



}
