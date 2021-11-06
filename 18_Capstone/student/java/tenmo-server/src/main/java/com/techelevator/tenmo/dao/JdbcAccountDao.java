package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalanceFromUserId(int userId) {
        String sql =    "SELECT balance " +
                        "FROM accounts " +
                        "WHERE user_id = ?;";

        BigDecimal currentBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        return currentBalance;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;

        String sql =    "SELECT account_id, user_id, balance " +
                        "FROM accounts " +
                        "WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        if (results.next()) {
            account = mapRowToAccount(results);
        }

        return account;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        Account account = null;

        String sql =    "SELECT account_id, user_id, balance " +
                        "FROM accounts " +
                        "WHERE account_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        if (results.next()) {
            account = mapRowToAccount(results);
        }

        return account;
    }

    @Override
    public void subtractFromBalance(BigDecimal subtractAmount, int accountFromId) {
        String sql =    "UPDATE accounts " +
                        "SET balance = balance - ? " +
                        "WHERE accounts.account_id = ? AND balance >= ?;";

        jdbcTemplate.update(sql, subtractAmount, accountFromId, subtractAmount);
    }

    @Override
    public void addToBalance(BigDecimal addAmount, int accountToId) {
        String sql =    "UPDATE accounts " +
                        "SET balance = balance + ? " +
                        "WHERE accounts.account_id = ?;";

        jdbcTemplate.update(sql, addAmount, accountToId);
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();

        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));

        return account;
    }

}
