package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao {
@Autowired
    private JdbcTemplate jdbcTemplate;
@Autowired
    private AccountsDao accountsDao;
//    private UserDao userDao;



    @Override
    public Transfers[] allTransfers(String username) {
        List<Transfers> list = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM transfers " +
                "Join accounts on transfers.account_from = accounts.account_id " +
                "Join users USING(user_id) " +
                "WHERE users.username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        while (rowSet.next()) {
            Transfers transfers = mapRowToTransfer(rowSet);
            list.add(transfers);
        }
        Transfers[] allTransfers = new Transfers[list.size()];
        for (int i = 0; i < list.size(); i++) {
            allTransfers[i] = (list.get(i));
        }

        return allTransfers;
    }

    @Override
    public Transfers transferLookupWithTransferID(Long transferID) {
        Transfers transfers;
        String sql = "SELECT * " +
                "FROM transfers WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferID);
        transfers = mapRowToTransfer(rowSet);

        return transfers;
    }




    @Override
    public String[] userList() {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM users;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            usernames.add(rowSet.getString("username"));
        }
        String[] names = new String[usernames.size()];
        for (int i = 0; i < usernames.size(); i++) {
            names[i] = usernames.get(i);
        }
        return names;
    }




    private Transfers mapRowToTransfer(SqlRowSet rowSet) {
        Transfers transfer = new Transfers();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeId(rowSet.getLong("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getLong("transfer_status_id"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));

        return transfer;
    }




    @Override
    public void tenmoPay(Long accountFromId, Long accountToId, BigDecimal amount) {
//        if (accountFromId.equals(accountToId)) {
//            System.out.println("Invalid transfer, please try again");
//        }
//        if (accountsDao.getBalanceFromAccountId(accountFromId).compareTo(amount) < 1) {
//            System.out.println("Insufficient funds for transfer");
//        }
//        String sqlUser = "SELECT account_id FROM users WHERE username ILIKE ?;";
//        Long userIdFrom  = jdbcTemplate.queryForObject(sqlUser, Long.class, username);
//        String sqlTO = "SELECT account_id FROM users WHERE username ILIKE ?;";
//        Long userIdTo = jdbcTemplate.queryForObject(sqlTO, Long.class, userTo);


        String sql = "INSERT INTO transfers " +
                "(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (2, 2, ?, ?, ?);";
        jdbcTemplate.update(sql, accountFromId, accountToId, amount);

        accountsDao.depositToBalance(amount, accountToId);
        accountsDao.withdrawFromBalance(amount, accountFromId);
        System.out.println("Successful Transfer! Your new balance is " + "$" + (accountsDao.getBalanceFromAccountId(accountFromId).subtract(amount)));
        //  return "Successful Transfer! Your new balance is " + "$" + (accountsDao.balanceCheck(accountFrom);

    }



}
