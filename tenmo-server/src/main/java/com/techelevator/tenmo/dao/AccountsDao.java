package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;

public interface AccountsDao {
    BigDecimal getBalanceFromUserID(Long userID);

    BigDecimal depositToBalance(BigDecimal amountToDeposit, Long userID);

    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long userID);

    BigDecimal getBalanceFromAccountID(Long accountId);

    Accounts findAccountByUsername (String username);

}