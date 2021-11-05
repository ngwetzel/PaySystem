package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdcbAccountsDao implements AccountsDao{

    private JdbcTemplate jdbcTemplate;
    JdcbAccountsDao dao = new JdcbAccountsDao();

    @Override
    public BigDecimal getBalance(Long userID) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        BigDecimal balance = rowSet.getBigDecimal("balance");
        return balance;
    }
    @Override
    public BigDecimal depositToBalance(BigDecimal amountToDeposit, Long accountID) {
        Accounts account = new Accounts();
        BigDecimal newBalance = account.getBalance().add(amountToDeposit);
        String sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, newBalance, accountID);
//        return newBalance;
        return account.getBalance();
    }

    @Override
    public BigDecimal withdrawFromBalance(BigDecimal amountToWithdraw, Long accountID) {
        Accounts account = new Accounts();
        BigDecimal newBalance = account.getBalance().subtract(amountToWithdraw);
        String sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, newBalance, accountID);
//        return newBalance;
        return account.getBalance();
    }

    @Override
    public BigDecimal balanceCheck(Long userFromId) {
    String sql = "SELECT balance FROM accounts " +
            "JOIN transfers on account_from = account_id " +
            "Where account_id = ?;";
    BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class);

        assert balance != null;
      return balance;
    }




    private Accounts mapRowToAccount(SqlRowSet rowSet){
        Accounts account = new Accounts();
        account.setAccountID(rowSet.getLong("account_id"));
        account.setUserID(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

    @Override
    public Accounts findAccountByUsername (String username){
        Accounts account = null;
        String sql = "SELECT account.* FROM accounts " +
                "JOIN users USING(user_id) " +
                "WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,  "%" + username + "%");
        if(rowSet.next()){
            account = mapRowToAccount(rowSet);
        }
        return account;

    }

}