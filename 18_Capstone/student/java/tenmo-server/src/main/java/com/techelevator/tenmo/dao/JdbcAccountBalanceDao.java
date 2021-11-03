package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountBalanceDao implements AccountBalanceDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountBalanceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getCurrentBalance(int userId) {
        String sql = "SELECT balance " +
                     "FROM accounts " +
                     "WHERE user_id = ?;";

        BigDecimal currentBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        return currentBalance;
    }

}
