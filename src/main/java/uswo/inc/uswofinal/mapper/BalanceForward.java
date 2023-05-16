package uswo.inc.uswofinal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uswo.inc.uswofinal.model.Wlfr;

public class BalanceForward implements RowMapper<Wlfr> {
    @Override
    public Wlfr mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wlfr wlfr = new Wlfr();
        wlfr.setWkno(rs.getString("wkno"));
        wlfr.setLokal(rs.getString("locale"));
        wlfr.setDistrict(rs.getString("district"));
        wlfr.setF9(rs.getDouble("f9"));
        wlfr.setF9_balance(rs.getDouble("f9_balance"));
        wlfr.setThdistrict(rs.getDouble("thdistrict"));
        wlfr.setThlokal(rs.getDouble("thlokal"));
        wlfr.setDistrict_balance(rs.getDouble("district_balance"));
        wlfr.setLokal_balance(rs.getDouble("lokal_balance"));
        wlfr.setCentral(rs.getDouble("central"));
        wlfr.setCentral_balance(rs.getDouble("central_balance"));
        return wlfr;
    }
}

