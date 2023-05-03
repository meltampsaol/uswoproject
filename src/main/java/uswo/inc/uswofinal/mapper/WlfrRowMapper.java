package uswo.inc.uswofinal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uswo.inc.uswofinal.model.Wlfr;

public class WlfrRowMapper implements RowMapper<Wlfr> {
    @Override
    public Wlfr mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wlfr wlfr = new Wlfr();
        wlfr.setWkno(rs.getString("wkno"));
        wlfr.setLokal(rs.getString("locale"));
        wlfr.setDistrict(rs.getString("district"));
        wlfr.setBankstart(rs.getDouble("bankstart"));
        wlfr.setBank_balance(rs.getDouble("bank_balance"));
        wlfr.setLfstart(rs.getDouble("lfstart"));
        wlfr.setLf_balance(rs.getDouble("lf_balance"));
        wlfr.setCfstart(rs.getDouble("cfstart"));
        wlfr.setCf_balance(rs.getDouble("cf_balance"));
        wlfr.setResumen(rs.getDouble("resumen"));
        return wlfr;
    }
}

