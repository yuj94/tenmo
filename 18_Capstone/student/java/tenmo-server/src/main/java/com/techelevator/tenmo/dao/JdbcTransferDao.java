package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private int TRANSFER_TYPE_SEND = 2;
    private int TRANSFER_STATUS_APPROVED = 2;

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
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public Transfer getTransfer(int transferId) {
        String sql =    "SELECT transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                        "FROM transfers " +
                        "WHERE transfer_id = ?;";

        Transfer transfer = jdbcTemplate.queryForObject(sql, Transfer.class, transferId);

        return transfer;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, TRANSFER_TYPE_SEND, TRANSFER_STATUS_APPROVED,
                transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount());

        return getTransfer(newId);
    }

    @Override
    public Transfer addToBalance(int transferId) {
        String sql =    "UPDATE transfers " +
                        "SET ";
        return null;
    }

    @Override
    public Transfer subtractFromBalance(int transferId) {
        return null;
    }

    private User mapRowToUser(SqlRowSet rowSet) {
        User user = new User();
        user.setId(rowSet.getLong("user_id"));
        user.setUsername(rowSet.getString("username"));
        return user;
    }

}
