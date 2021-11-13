package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsersList() {
        List<User> users = new ArrayList<>();

        String sql =    "SELECT user_id, username " +
                        "FROM users;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            users.add(mapRowToUser(results));
        }

        return users;
    }

    @Override
    public Transfer getTransfer(int transferId) {
        Transfer transfer = null;

        String sql =    "SELECT * " +
                        "FROM transfers " +
                        "WHERE transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }

        return transfer;
    }

    @Override
    public void createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                     "VALUES (2, 2, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        getTransfer(newId);
    }

    @Override
    public List<Transfer> getTransfersByAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();

        String sql =    "SELECT * " +
                        "FROM transfers " +
                        "WHERE account_from = ? OR account_to = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }

        return transfers;
    }

    private User mapRowToUser(SqlRowSet rowSet) {
        User user = new User();

        user.setId(rowSet.getLong("user_id"));
        user.setUsername(rowSet.getString("username"));

        return user;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));

        return transfer;
    }

}
