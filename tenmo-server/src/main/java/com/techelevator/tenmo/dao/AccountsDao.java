package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountsDao {

    BigDecimal getBalanceFromUserID(Long userID);

    BigDecimal depositToBalance(BigDecimal amountToDeposit, Long userTo);

    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long userFrom);

    BigDecimal getBalanceFromUserName(String username);

    Long findAccountByUserID(Long userID);
}