package uswo.inc.uswofinal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uswo.inc.uswofinal.model.F4Detail;


public class F4DetailRowMapper implements RowMapper<F4Detail> {
    @Override
    public F4Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
        F4Detail f4 = new F4Detail();
        f4.setWkno(rs.getString("wkno"));
        f4.setLokal(rs.getString("locale"));
        f4.setDistrict(rs.getString("district"));
        f4.setUswo(rs.getBigDecimal("uswo"));
        f4.setCfolocale(rs.getBigDecimal("cfolocale"));
        f4.setCfointl(rs.getBigDecimal("cfointl"));
        f4.setLingap(rs.getBigDecimal("lingap"));
        f4.setRcentral(rs.getBigDecimal("rcentral"));
        f4.setRtotal(rs.getBigDecimal("rtotal"));
        
        return f4;
    }
}

