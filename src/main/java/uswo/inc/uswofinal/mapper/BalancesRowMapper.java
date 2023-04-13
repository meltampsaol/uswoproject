package uswo.inc.uswofinal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uswo.inc.uswofinal.model.Balances;

public class BalancesRowMapper implements RowMapper<Balances> {

    @Override
    public Balances mapRow(ResultSet rs, int rowNumber) throws SQLException {

        Balances balance = new Balances();

        balance.setLokal(rs.getString("lcode"));
        balance.setForyear(rs.getInt("foryear"));
        balance.setStartingBalance(rs.getBigDecimal("total_starting_balance"));
        balance.setCurrentPayment(rs.getBigDecimal("total_payment"));
        balance.setCurrentBalance(rs.getBigDecimal("total_balance"));

        return balance;
    }
}
