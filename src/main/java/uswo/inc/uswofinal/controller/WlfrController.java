package uswo.inc.uswofinal.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uswo.inc.uswofinal.app.PythonRunner;
import uswo.inc.uswofinal.mapper.WlfrRowMapper;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.F4Detail;
import uswo.inc.uswofinal.model.F9;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Wlfr;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.F9Repository;
import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.WlfrRepository;

@Controller
@RequestMapping("/wlfr")
public class WlfrController {
    private static final Logger logger = LoggerFactory.getLogger(WlfrController.class);
    @Autowired
    private WlfrRepository wlfrRepository;

    @Autowired
    private F9Repository f9Repository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private DistrictRepository districtRepository;

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

    @PostMapping("/save")
    public String saveTransaction(@ModelAttribute("wlfr") Wlfr wlfr, Model model)
            throws IOException, InterruptedException {
        
        if (wlfr.getAmtwithheld()==null) {
            wlfr.setAmtwithheld(0.00);
        }

        if (wlfr.getBankstart() == null) {
            wlfr.setBankstart(0.00);
        }
        if (wlfr.getCfstart() == null) {
            wlfr.setCfstart(0.00);
        }
        if (wlfr.getLfstart() == null) {
            wlfr.setLfstart(0.00);
        }
        if (wlfr.getBankcharge() == null) {
            wlfr.setBankcharge(0.00);
        }
        if (wlfr.getInterest() == null) {
            wlfr.setInterest(0.00);
        }
        if (wlfr.getCf_balance() == null) {
            wlfr.setCf_balance(0.00);
        }
        if (wlfr.getBank_balance() == null) {
            wlfr.setBank_balance(0.00);
        }
        if (wlfr.getLf_balance() == null) {
            wlfr.setLf_balance(0.00);
        }
        if (wlfr.getInterest() == null) {
            wlfr.setInterest(0.00);
        }
        if (wlfr.getResumen() == null) {
            wlfr.setResumen(0.00);
        }
        if (wlfr.getMiscout() == null) {
            wlfr.setMiscout(0.00);
        }
        if (wlfr.getMiscin() == null) {
            wlfr.setMiscin(0.00);
        }
        if (wlfr.getRetamount() == null) {
            wlfr.setRetamount(0.00);
        }
        if (wlfr.getLfappf10() == null) {
            wlfr.setLfappf10(0.00);
        }
        if (wlfr.getLfmiscout() == null) {
            wlfr.setLfmiscout(0.00);
        }
        if (wlfr.getCfappf10() == null) {
            wlfr.setCfappf10(0.00);
        }
        if (wlfr.getCfmiscout() == null) {
            wlfr.setCfmiscout(0.00);
        }
        wlfrRepository.save(wlfr);
        Integer xlcode = wlfr.getLcode();
        Integer xdcode = wlfr.getDid();

        runPython(xlcode,xdcode,wlfr.getWkno());

        String procSql = "CALL wlfrAccumulatedBalance2(?, ?)";
        jdbcTemplate.update(procSql, xlcode, xdcode);

        String selectSql = "SELECT b.locale, wkno, c.district, bankstart,bank_balance,lfstart,lf_balance, cfstart,cf_balance, resumen FROM wlfr_temp a, lokal b, districts c"
                +
                " WHERE a.lcode=b.lcode AND a.did=b.did and a.did=c.did;";
        List<Wlfr> wlfrList = jdbcTemplate.query(selectSql, new WlfrRowMapper());

        String procSql2 = "CALL showaccumulatedBalance(?, ?)";
        jdbcTemplate.update(procSql2, xlcode, xdcode);
        List<Wlfr> balfwd = jdbcTemplate.query(procSql2, new WlfrRowMapper());
        model.addAttribute("balfwd", balfwd);
        model.addAttribute("wlfrlist", wlfrList);
        return "wlfrlist";

    }

    public void runPython(Integer lcode, Integer did, String wkno) {
        PythonRunner.insertRecord(lcode,did,wkno);
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

    @PostMapping("/savelate")
    public String saveLateTransaction(@ModelAttribute("wlfr") Wlfr wlfr, Model model)
            throws IOException, InterruptedException {
        
        if (wlfr.getAmtwithheld()==null) {
            wlfr.setAmtwithheld(0.00);
        }

        if (wlfr.getBankstart() == null) {
            wlfr.setBankstart(0.00);
        }
        if (wlfr.getCfstart() == null) {
            wlfr.setCfstart(0.00);
        }
        if (wlfr.getLfstart() == null) {
            wlfr.setLfstart(0.00);
        }
        if (wlfr.getBankcharge() == null) {
            wlfr.setBankcharge(0.00);
        }
        if (wlfr.getInterest() == null) {
            wlfr.setInterest(0.00);
        }
        if (wlfr.getCf_balance() == null) {
            wlfr.setCf_balance(0.00);
        }
        if (wlfr.getBank_balance() == null) {
            wlfr.setBank_balance(0.00);
        }
        if (wlfr.getLf_balance() == null) {
            wlfr.setLf_balance(0.00);
        }
        if (wlfr.getInterest() == null) {
            wlfr.setInterest(0.00);
        }
        if (wlfr.getResumen() == null) {
            wlfr.setResumen(0.00);
        }
        if (wlfr.getMiscout() == null) {
            wlfr.setMiscout(0.00);
        }
        if (wlfr.getMiscin() == null) {
            wlfr.setMiscin(0.00);
        }
        if (wlfr.getRetamount() == null) {
            wlfr.setRetamount(0.00);
        }
        if (wlfr.getLfappf10() == null) {
            wlfr.setLfappf10(0.00);
        }
        if (wlfr.getLfmiscout() == null) {
            wlfr.setLfmiscout(0.00);
        }
        if (wlfr.getCfappf10() == null) {
            wlfr.setCfappf10(0.00);
        }
        if (wlfr.getCfmiscout() == null) {
            wlfr.setCfmiscout(0.00);
        }

        //For Balance forward remittance entries (f9,district,lokal,central,lingap)
        if (wlfr.getF9() == null) {
            wlfr.setF9(0.00);
        }
        if (wlfr.getLingap() == null) {
            wlfr.setLingap(0.00);
        }
        if (wlfr.getCentral() == null) {
            wlfr.setCentral(0.00);
        }
        if (wlfr.getThdistrict() == null) {
            wlfr.setThdistrict(0.00);
        }
        if (wlfr.getThlokal() == null) {
            wlfr.setThlokal(0.00);
        }
        F9 f9 = new F9();
        f9.setCentral(wlfr.getCentral());
        f9.setF9(wlfr.getF9());
        f9.setLingap(wlfr.getLingap());
        f9.setThdistrict(wlfr.getThdistrict());
        f9.setThlokal(wlfr.getThlokal());
        f9Repository.save(f9);
        wlfrRepository.save(wlfr);

        Integer xlcode = wlfr.getLcode();
        Integer xdcode = wlfr.getDid();

        

        String procSql = "CALL wlfrAccumulatedBalance2(?, ?)";
        jdbcTemplate.update(procSql, xlcode, xdcode);

        String selectSql = "SELECT b.locale, wkno, c.district, bankstart,bank_balance,lfstart,lf_balance, cfstart,cf_balance, resumen FROM wlfr_temp a, lokal b, districts c"
                +
                " WHERE a.lcode=b.lcode AND a.did=b.did and a.did=c.did;";
        List<Wlfr> wlfrList = jdbcTemplate.query(selectSql, new WlfrRowMapper());

        String procSql2 = "CALL showaccumulatedBalance(?, ?)";
        jdbcTemplate.update(procSql2, xlcode, xdcode);
        List<Wlfr> balfwd = jdbcTemplate.query(procSql2, new WlfrRowMapper());
        model.addAttribute("balfwd", balfwd);
        model.addAttribute("wlfrlist", wlfrList);
        return "wlfrlist";

    }



}
