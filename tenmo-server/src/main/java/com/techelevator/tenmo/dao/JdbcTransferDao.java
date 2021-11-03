package com.techelevator.tenmo.dao;

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


    @Override
    public Transfers tenmoPay(Long accountFrom, Long accountTo, BigDecimal amount) {
        if(accountFrom == accountTo){
            return "Invalid"
        }
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
}
