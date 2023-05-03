package uswo.inc.uswofinal.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uswo.inc.uswofinal.mapper.WlfrRowMapper;
import uswo.inc.uswofinal.model.Wlfr;
import uswo.inc.uswofinal.repository.WlfrRepository;
import uswo.inc.uswofinal.repository.WlfrRepositoryImpl;

@Controller
@RequestMapping("/wlfr")
public class WlfrController {
    @Autowired
    private WlfrRepository wlfrRepository;
    
    private final JdbcTemplate jdbcTemplate;

    public WlfrController(WlfrRepository wlfrRepository, JdbcTemplate jdbcTemplate) {
        this.wlfrRepository = wlfrRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
      
    @GetMapping("/{lcode}/{did}")
    public String wlfrAccumulatedBalance(@PathVariable String lcode, @PathVariable String did, Model model) {
        Integer xlcode = Integer.parseInt(lcode);
        Integer xdcode = Integer.parseInt(did);
       
        String procSql = "CALL wlfrAccumulatedBalance2(?, ?)";
        jdbcTemplate.update(procSql, xlcode, xdcode);
        
        String selectSql = "SELECT b.locale, wkno, c.district, bankstart,bank_balance,lfstart,lf_balance, cfstart,cf_balance, resumen FROM wlfr_temp a, lokal b, districts c" + 
        " WHERE a.lcode=b.lcode AND a.did=b.did and a.did=c.did;";
        List<Wlfr> wlfrList = jdbcTemplate.query(selectSql, new WlfrRowMapper());
        model.addAttribute("wlfrlist", wlfrList);
        return "wlfrlist";
    }
    
}