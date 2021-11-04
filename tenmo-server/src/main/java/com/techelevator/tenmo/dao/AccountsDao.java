package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountsDao {
    BigDecimal getBalance(Long userID);

    BigDecimal depositToBalance(BigDecimal amountToDeposit, Long userID);

    BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long userID);

    BigDecimal balanceCheck(Long accountFromId);
}