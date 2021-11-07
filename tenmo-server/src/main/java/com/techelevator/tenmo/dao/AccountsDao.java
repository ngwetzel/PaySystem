package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountsDao {

    BigDecimal getBalanceFromUserID(Long userID);
    BigDecimal depositToBalance(BigDecimal amountToDeposit, Long accountToId);
    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long accountFromId);
  BigDecimal getBalanceFromUserName(String username);
    Accounts findAccountByUsername (String username);
    String getUsernameFromAccountId (Long accountId);
BigDecimal getBalanceFromAccountId(Long accountId);


}
