package uswo.inc.uswofinal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uswo.inc.uswofinal.model.Balances;

public class BalancesRowMapper implements RowMapper<Balances> {

    @Override
    public Balances mapRow(ResultSet rs, int rowNumber) throws SQLException {

        Balances balances = new Balances();
        balances.setLokal(rs.getString("locale"));
        balances.setDistrict(rs.getString("district"));
        balances.setForyear(rs.getInt("foryear"));
        balances.setStartingBalance(rs.getBigDecimal("startingbalance"));
        balances.setCurrentPayment(rs.getBigDecimal("currentpayment"));
        balances.setCurrentBalance(rs.getBigDecimal("currentbalance"));

        return balances;
    }
}
