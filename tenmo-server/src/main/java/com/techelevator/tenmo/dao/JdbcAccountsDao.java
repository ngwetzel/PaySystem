package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
@Component
public class JdbcAccountsDao implements AccountsDao{
@Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public BigDecimal getBalanceFromUserID(Long userID) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        BigDecimal balance = rowSet.getBigDecimal("balance");

        assert balance != null;
        return balance;
    }

    @Override
    public BigDecimal getBalanceFromAccountId(Long accountId) {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        if (rowSet.next()) {
            BigDecimal balance = rowSet.getBigDecimal("balance");
            assert balance != null;
            return balance;
        }
     return null;
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
    public BigDecimal depositToBalance(BigDecimal amountToDeposit, Long accountId) {
//
        String sql = "UPDATE accounts " +
                "SET balance = balance + ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql, amountToDeposit, accountId);
//        return newBalance;
        return getBalanceFromAccountId(accountId);
    }


    @Override
    public BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long accountId) {
//
        String sql = "UPDATE accounts " +
                "SET balance = balance - ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql, amountToWithdraw, accountId);
//        return newBalance;
        return getBalanceFromAccountId(accountId);
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

    @Override
    public String getUsernameFromAccountId(Long accountId) {
        String sql = "SELECT username FROM users " +
                "JOIN accounts USING(user_id) " +
                "WHERE account_id = ?;";
        return jdbcTemplate.queryForObject(sql, String.class, accountId);

    }

    private Accounts mapRowToAccount(SqlRowSet rowSet){
        Accounts account = new Accounts();
        account.setAccountID(rowSet.getLong("account_id"));
        account.setUserID(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }


}
