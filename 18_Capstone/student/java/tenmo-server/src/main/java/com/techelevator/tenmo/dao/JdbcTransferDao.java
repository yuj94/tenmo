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

        String sql =    "SELECT transfer_id, transfers.transfer_type_id, transfers.transfer_status_id, account_from, account_to, amount, u1.user_id AS u1_user_id_from, u1.username AS u1_username_from, u2.user_id AS u2_user_id_to, u2.username AS u2_username_to, transfer_type_desc, transfer_status_desc " +
                        "FROM transfers " +
                        "INNER JOIN accounts AS a1 ON transfers.account_from = a1.account_id " +
                        "INNER JOIN users AS u1 ON a1.user_id = u1.user_id " +
                        "INNER JOIN accounts AS a2 ON transfers.account_to = a2.account_id " +
                        "INNER JOIN users AS u2 ON a2.user_id = u2.user_id " +
                        "INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id " +
                        "INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id " +
                        "WHERE transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if (results.next()) {
            transfer = mapRowToTransferWithUsersAndDesc(results);
        }

        return transfer;
    }

    @Override
    public void createTransfer(Transfer transfer) {
        String sql =    "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                        "VALUES (2, 2, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        getTransfer(newId);
    }

    @Override
    public List<Transfer> getTransfersByAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();

        String sql =    "SELECT transfer_id, transfers.transfer_type_id, transfers.transfer_status_id, account_from, account_to, amount, u1.user_id AS u1_user_id_from, u1.username AS u1_username_from, u2.user_id AS u2_user_id_to, u2.username AS u2_username_to, transfer_type_desc, transfer_status_desc " +
                        "FROM transfers " +
                        "INNER JOIN accounts AS a1 ON transfers.account_from = a1.account_id " +
                        "INNER JOIN users AS u1 ON a1.user_id = u1.user_id " +
                        "INNER JOIN accounts AS a2 ON transfers.account_to = a2.account_id " +
                        "INNER JOIN users AS u2 ON a2.user_id = u2.user_id " +
                        "INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id " +
                        "INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id " +
                        "WHERE account_from = ? OR account_to = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        while (results.next()) {
            transfers.add(mapRowToTransferWithUsersAndDesc(results));
        }

        return transfers;
    }

    private User mapRowToUser(SqlRowSet rowSet) {
        User user = new User();

        user.setId(rowSet.getLong("user_id"));
        user.setUsername(rowSet.getString("username"));

        return user;
    }

    private Transfer mapRowToTransferWithUsersAndDesc(SqlRowSet results) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        transfer.setUserIdFrom(results.getInt("u1_user_id_from"));
        transfer.setUserNameFrom(results.getString("u1_username_from"));
        transfer.setUserIdTo(results.getInt("u2_user_id_to"));
        transfer.setUserNameTo(results.getString("u2_username_to"));
        transfer.setTransferTypeDesc(results.getString("transfer_type_desc"));
        transfer.setTransferStatusDesc(results.getString("transfer_status_desc"));

        return transfer;
    }

}
