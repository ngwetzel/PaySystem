package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDao implements AccountsDao{
    @Autowired
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
    public BigDecimal getBalanceFromUserName(String username) {
        String sql = "SELECT balance FROM accounts JOIN users USING(user_id) WHERE username = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            BigDecimal balance = rowSet.getBigDecimal("balance");

            assert balance != null;

            return balance;
        } return null;
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
