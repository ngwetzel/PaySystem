package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountsDao {

    BigDecimal getBalanceFromUserID(Long userID);

    BigDecimal depositToBalance(BigDecimal amountToDeposit, Long userID);

    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long userID);

    BigDecimal getBalanceFromUserName(String username);

    Accounts findAccountByUsername(String username);
}
