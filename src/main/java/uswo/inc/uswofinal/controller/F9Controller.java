package uswo.inc.uswofinal.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uswo.inc.uswofinal.mapper.WlfrRowMapper;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Wlfr;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.F9Repository;
import uswo.inc.uswofinal.repository.LokalRepository;

@Controller
@RequestMapping("/f9")
public class F9Controller {
    private static final Logger logger = LoggerFactory.getLogger(WlfrController.class);
    @Autowired
    private F9Repository f9Repository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public F9Controller(F9Repository f9Repository, JdbcTemplate jdbcTemplate) {
        this.f9Repository = f9Repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/{lcode}/{did}")
    public String wlfrAccumulatedBalance(@PathVariable String lcode, @PathVariable String did, Model model) {
        Integer xlcode = Integer.parseInt(lcode);
        Integer xdcode = Integer.parseInt(did);

        String procSql = "CALL wlfrAccumulatedBalance2(?, ?)";
        jdbcTemplate.update(procSql, xlcode, xdcode);

        String selectSql = "SELECT b.locale, wkno, c.district, bankstart,bank_balance,lfstart,lf_balance, cfstart,cf_balance, resumen FROM wlfr_temp a, lokal b, districts c"
                +
                " WHERE a.lcode=b.lcode AND a.did=b.did and a.did=c.did;";
        List<Wlfr> wlfrList = jdbcTemplate.query(selectSql, new WlfrRowMapper());
        model.addAttribute("wlfrlist", wlfrList);
        return "wlfrlist";
    }

    @GetMapping("/add")
    public String addTrans(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("wlfr", new Wlfr());
        return "wlfr_add";
    }

   

    @GetMapping("/search")
    public String searchWlfr(Model model) {
        return "wlfrl_search";
    }

    @GetMapping("/search/lokal/{lokal}")
    public String searchWlfrLocale(@PathVariable String lokal, Model model) {
        return "wlfrl_search_result";
    }

    @GetMapping("/addlate")
    public String addLatetrans(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("wlfr", new Wlfr());
        return "wlfr_addlate";
    }




}
