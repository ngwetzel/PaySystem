package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDAO{

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Transfers> allTransfers(Long userID) {
       List<Transfers> list = new ArrayList<>();
       String sql = "SELECT transfers.* " +
               "FROM transfers " +
               "Join accounts on transfers.accounts_from = accounts.account_id " +
               "WHERE accounts.user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        while (rowSet.next()) {
            Transfers transfer = mapRowToTransfer(rowSet);
            list.add(transfer);
        } return list;
    }


    @Override
    public Transfers transferLookupWithTransferID(Long transferID) {
        Transfers transfers = new Transfers();
        String sql = "SELECT transfers.* " +
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
    public Object tenmoPay(Long userFrom, Long userTo, BigDecimal amount) {
        if (userFrom == userTo) {
            return "Invalid transfer, please send to a different account ";
        }
//        if(amount < balance){
//            return "Payment denied. Not enough money in account";
//        } not quite sure how to apply this if statement
        else {
            String sql = "INSERT INTO transfers" +
                    "(transfer_type_id, transfer_status_id, ?, ?, ?)" +
                    "VALUES (2, 2, ?, ?, ?);";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userFrom, userTo, amount);

            return "Success!";
        }
    }
    public Long destinationAccountLookup (String username){

        String sql = "SELECT account_to FROM transfers " +
                "JOIN accounts on account_to = account_id " +
                "JOIN users USING(user_id) " +
                "WHERE username = ?;";
        Long destinationAccountID = jdbcTemplate.queryForObject(sql, Long.class);
        return destinationAccountID;

    }

    private Transfers mapRowToTransfer (SqlRowSet rowSet){
        Transfers transfer = new Transfers();
        transfer.setTransferID(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeID(rowSet.getLong("transfer_type_id"));
        transfer.setTransferStatusID(rowSet.getLong("transfer_status_id"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }

}
