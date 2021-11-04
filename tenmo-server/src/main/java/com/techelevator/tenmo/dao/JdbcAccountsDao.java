package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountsDao implements AccountsDao{

    JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal getBalance(Long userID) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        BigDecimal balance = rowSet.getBigDecimal("balance");
        return balance;
    }
    @Override
    public BigDecimal depositToBalance(BigDecimal amountToDeposit, Long userID) {
        Accounts account = new Accounts();
        BigDecimal newBalance = account.getBalance().add(amountToDeposit);
        String sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE user_id = ?;";
        jdbcTemplate.queryForRowSet(sql, newBalance, userID);
//        return newBalance;
        return account.getBalance();
    }

    @Override
    public BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long userID) {
        Accounts account = new Accounts();
        BigDecimal newBalance = account.getBalance().subtract(amountToWithdraw);
        String sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE user_id = ?;";
        jdbcTemplate.queryForRowSet(sql, newBalance, userID);
//        return newBalance;
        return account.getBalance();
    }


    private Accounts mapRowToAccount(SqlRowSet rowSet){
        Accounts account = new Accounts();
        account.setAccountID(rowSet.getLong("account_id"));
        account.setUserID(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
