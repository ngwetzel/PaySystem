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
    public BigDecimal getBalanceFromUserID(Long userID) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        BigDecimal balance = rowSet.getBigDecimal("balance");

        assert balance != null;
        return balance;
    }

    @Override
    public BigDecimal getBalanceFromAccountID(Long accountId) {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        BigDecimal balance = rowSet.getBigDecimal("balance");

        assert balance != null;
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



    public Accounts findAccountByUsername (String username){
        Accounts account = null;
        String sql = "SELECT account_id FROM accounts " +
                "JOIN users USING(user_id) " +
                "WHERE username ILIKE ?;";
      SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,  "%" + username + "%");
      if(rowSet.next()){
          account = mapRowToAccount(rowSet);
      }
      return account;

    }
    private Accounts mapRowToAccount(SqlRowSet rowSet){
        Accounts account = new Accounts();
        account.setAccountID(rowSet.getLong("account_id"));
        account.setUserID(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}