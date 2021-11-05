package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDAO {

    private JdbcTemplate jdbcTemplate;
    JdcbAccountsDao accountsDao = new JdcbAccountsDao();

    @Override
    public List<Transfers> allTransfers(Long userID) {
        List<Transfers> list = new ArrayList<>();
        String sql = "SELECT transfers.* " +
                "FROM transfers " +
                "Join accounts on transfers.accounts_from = accounts.account_id " +
                "WHERE accounts.user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        while (rowSet.next()) {
            Transfers transfers = mapRowToTransfer(rowSet);
            list.add(transfers);
        }
        return list;
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





    private Transfers mapRowToTransfer(SqlRowSet rowSet) {
        Transfers transfer = new Transfers();
        transfer.setTransferID(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeID(rowSet.getLong("transfer_type_id"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("balance"));
        return transfer;
    }


    @Override
    public Object tenmoRequest(Long accountFrom, Long accountTo, BigDecimal amount) {
        return null;
    }


    @Override
    public Object tenmoPay(Long accountFrom, Long accountTo, BigDecimal amount) {
        if (accountFrom.equals(accountTo)) {
            return "Invalid transfer, please try again";
        }
        if (accountsDao.balanceCheck(accountFrom).compareTo(amount) < 1) {
            return "Insufficient funds for transfer";
        }

        String sql = "INSERT INTO transfers " +
                "(transfer_type_id, transfer_status_id, ?, ?, ?) " +
                "VALUES (2, 2, ?, ?, ?);";
        jdbcTemplate.queryForRowSet(sql, accountFrom, accountTo, amount);
        accountsDao.depositToBalance(amount, accountTo);
        accountsDao.withdrawFromBalance(amount, accountFrom);
        return "Successful Transfer! Your new balance is " + "$" + (accountsDao.balanceCheck(accountFrom).subtract(amount));
        //  return "Successful Transfer! Your new balance is " + "$" + (accountsDao.balanceCheck(accountFrom);

    }



}