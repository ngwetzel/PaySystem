package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountsDao {

    BigDecimal getBalanceFromUserID(Long userID);
    BigDecimal depositToBalance(BigDecimal amountToDeposit, String username);
    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, String username);
  BigDecimal getBalanceFromUserName(String username);
    Accounts findAccountByUsername (String username);



}
