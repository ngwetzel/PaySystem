package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDAO{

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Transfer> alltransfers(Long userID) {
       List<Transfer> list = new ArrayList<>();
       String sql = "SELECT transfer.* " +
               "FROM transfer " +
               "Join accounts on transfer.accounts_from = accounts.account_id " +
               "WHERE accounts.user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        while (rowSet.next()) {
            Transfer transfer = mapRowToTransfer(rowSet);
            list.add(transfer);
        } return list;
    }

    @Override
    public Transfer transferLookupWithTransferID(Long transferID) {
        return null;
    }

    @Override
    public Transfer transferFunds(Long userID, Long accountID, double transferAmount, Long destinationAccountID) {
        return null;
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
    public Long destinationAccountLookup(String username) {

        String sql = "SELECT account_to FROM transfers " +
                "JOIN accounts on account_to = account_id " +
                "JOIN users USING(user_id) " +
                "WHERE username = ?;";
        Long destinationAccountID = jdbcTemplate.queryForObject(sql, Long.class);
        return destinationAccountID;

    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferID(rowSet.getLong("transfer_id"));
        transfer.setTranferTypeID(rowSet.getLong("transfer_type_id"));
        transfer.setAccountID(transfer.getAccountID());
        transfer.setAccountToID(transfer.getDestinationAccountID());
        transfer.setTransferAmount(transfer.getTransferAmount());
        return transfer;
    }


}
