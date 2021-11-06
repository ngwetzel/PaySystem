package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AccountsDao accountsDao;
    private UserDao userDao;

    @Override
    public Transfers[] allTransfers(String username) {
        List<Transfers> list = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM transfers " +
                "Join accounts on transfers.account_from = accounts.account_id " +
                "Join users USING(user_id) " +
                "WHERE users.username = ?;";
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
    public List<String> userList() {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM users;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            usernames.add(rowSet.getString("username"));
        }
        return usernames;
    }

    @Override
    public Object tenmoPay(Long userFromID, Long userToID, BigDecimal amount) {
        if (userFromID.equals(userToID)) {
            return "Invalid transfer, please try again";
        }
        if (accountsDao.getBalanceFromUserID(userFromID).compareTo(amount) < 1) {
            return "Insufficient funds for transfer";
        }
//        String sqlUser = "SELECT user_id FROM users WHERE username = ?;";
//        Long userIdFrom  = jdbcTemplate.queryForObject(sqlUser, Long.class, u);
//        String sqlTO = "SELECT user_id FROM users WHERE username = ?;";
//        Long userIdTo = jdbcTemplate.queryForObject(sqlTO, Long.class, userTo);


        String sql = "INSERT INTO transfers " +
                "(transfer_type_id, transfer_status_id, ?, ?, ?) " +
                "VALUES (2, 2, ?, ?, ?);";
        jdbcTemplate.queryForRowSet(sql, userFromID, userToID, amount);
        accountsDao.depositToBalance(amount, userToID);
        accountsDao.withdrawFromBalance(amount, userFromID);
        return "Successful Transfer! Your new balance is " + "$" + (accountsDao.getBalanceFromUserName(userTo).subtract(amount));
        //  return "Successful Transfer! Your new balance is " + "$" + (accountsDao.balanceCheck(accountFrom);

    }



    private Transfers mapRowToTransfer(SqlRowSet rowSet) {
        Transfers transfer = new Transfers();
        transfer.setTransferID(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeID(rowSet.getLong("transfer_type_id"));
        transfer.setTransferStatusID(rowSet.getLong("transfer_status_id"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAmount(rowSet.getBigDecimal("balance"));
        transfer.setUserFrom(rowSet.getString("userFrom"));
        transfer.setUserTo(rowSet.getString("userTo"));
        return transfer;
    }








}
