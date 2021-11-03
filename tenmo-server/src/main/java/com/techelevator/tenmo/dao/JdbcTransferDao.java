package com.techelevator.tenmo.dao;

<<<<<<< HEAD
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


=======
import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Transfers> allTransfers(Long userID) {
    List<Transfers> list = new ArrayList<>();
    String sql = "SELECT TRANSFER.* " +
            "FROM transfers " +
            "JOIN accounts ON transfer.accounts_from = accounts.account_id " +
            "WHERE accounts.user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userID);
        while (rowSet.next()){
            Transfers transfers = mapRowToTransfer(rowSet);
            list.add(transfers);
        }
        return list;
    }

    @Override
    public Transfers transferLookupByTransferID(Long transferID) {
        Transfers transfers = new Transfers();
        String sql = "SELECT * " +
                "FROM transfers " +
                "WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferID);
        if (rowSet.next()){
            transfers = mapRowToTransfer(rowSet);
        }
        return transfers;
    }

//    @Override
//    public Object tenmoRequest(Long accountFrom, Long accountTo, BigDecimal amount) {
//        String sql = "INSERT INTO transfers" +
//                "()" +
//                "VALUES (1, 1, ?, ?, ?);";
//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountFrom, accountTo, amount);
//    }

    @Override
    public Transfers tenmoPay(Long accountFrom, Long accountTo, BigDecimal amount) {
        String sql = "INSERT INTO transfers" +
                "(transfer_type_id, transfer_status_id, ?, ?, ?)" +
                "VALUES (2, 2, ?, ?, ?);";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountFrom, accountTo, amount);

    }


    private Transfers mapRowToTransfer(SqlRowSet rowSet){
        Transfers transfers = new Transfers();
        transfers.setTransferID(rowSet.getLong("transfer_id"));
        transfers.setTransferTypeID(rowSet.getLong("transfer_type_id"));
        transfers.setTransferStatusID(rowSet.getLong("transfer_status_id"));
        transfers.setAccountFrom(rowSet.getLong("account_from"));
        transfers.setAccountTo(rowSet.getLong("account_to"));
        transfers.setAmount(rowSet.getBigDecimal("amount"));

        return transfers;
    }
>>>>>>> 322f47a54208d3fe3ba9a91ca062cfb350edf6fe
}
