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
    public List<Transfers> allTransfers(String username) {
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
        return list;
    }



    @Override
    public Transfers transferLookupWithTransferID(Long transferID) {
        Transfers transfer;
        String sql = "SELECT * " +
                "FROM transfers WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferID);
        transfer = mapRowToTransfer(rowSet);
        if(rowSet.next()){
            transfer = mapRowToTransfer(rowSet);
        }
        return transfer;
    }

    @Override
    public Object tenmoPay(Long userFrom, Long userTo, BigDecimal amount) {
        if (userFrom.equals(userTo)) {
            return "Invalid transfer, please try again";
        }
        if (amount.compareTo(accountsDao.getBalanceFromUserID(userFrom)) == -1) {
            String sql = "INSERT INTO transfers " +
                    "(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (2, 2, ?, ?, ?);";
            jdbcTemplate.update(sql, accountsDao.findAccountByUserID(userFrom), accountsDao.findAccountByUserID(userTo), amount);

            accountsDao.depositToBalance(amount, accountsDao.findAccountByUserID(userTo));
            accountsDao.withdrawFromBalance(amount, accountsDao.findAccountByUserID(userFrom));
            //System.out.println("Successful Transfer! Your new balance is " + "$" + (accountsDao.getBalanceFromUserID(userTo).subtract(amount)));
            return "Successful Transfer! Your new balance is " + "$" + (accountsDao.getBalanceFromUserID(userTo));
        }else{
           return "Insufficient funds for transfer";
        }
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
