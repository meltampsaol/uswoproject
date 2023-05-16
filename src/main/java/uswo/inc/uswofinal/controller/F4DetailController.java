package uswo.inc.uswofinal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uswo.inc.uswofinal.mapper.F4DetailRowMapper;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.F4Detail;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.F4DetailRepository;
import uswo.inc.uswofinal.repository.LocaleDataRepository;
import uswo.inc.uswofinal.repository.LokalRepository;

@Controller
@RequestMapping("/f4")
public class F4DetailController {

   

   

    @Autowired
    LocaleDataRepository localeDataRepository;
    
    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private DistrictRepository districtRepository;
    
    @Autowired
    F4DetailRepository f4detailRepository;
    
    private final JdbcTemplate jdbcTemplate;
    public F4DetailController(F4DetailRepository f4detailRepository, JdbcTemplate jdbcTemplate) {
        this.f4detailRepository = f4detailRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/excel-import")
    public String importLocaleData(Model model) {
    
       
try {
    String pythonScriptPath = "H:/uswofinal/remittance/process_data.py";

    ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
    
    Process process = processBuilder.start();
    
    // Wait for the process to complete
    int exitCode = process.waitFor();
    
    if (exitCode == 0) {
        System.out.println("Python script executed successfully.");
        List<F4Detail> lc = f4detailRepository.findAll();
        model.addAttribute("localeDataList", lc);
        return "imported-excel";
    } else {
         // Get the error stream from the process
    InputStream errorStream = process.getErrorStream();
    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
    String errorLine = null;
    StringBuilder errorMessage = new StringBuilder();
    while ((errorLine = errorReader.readLine()) != null) {
        errorMessage.append(errorLine);
    }
    System.err.println("Error executing Python script. Error message: " + errorMessage.toString());
    model.addAttribute("errorMessage", errorMessage.toString());
    return "error";
    }
} catch (Exception e) {
    e.printStackTrace();
    return "error";
}
    }
    
@GetMapping("/excel-import-perweek")
    public String importF4Data(Model model) {
    
       
try {
    String filename = "MICOGN_week13_2023.xlsx";
    String pythonScriptPath = "H:/uswofinal/remittance/process_data_perweek.py";

    ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath,filename);
    
    Process process = processBuilder.start();
    
    // Wait for the process to complete
    int exitCode = process.waitFor();
    
    if (exitCode == 0) {
        System.out.println("Python script executed successfully.");
        List<F4Detail> lc = f4detailRepository.findAll();
        model.addAttribute("localeDataList", lc);
        return "imported-excel";
    } else {
         // Get the error stream from the process
    InputStream errorStream = process.getErrorStream();
    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
    String errorLine = null;
    StringBuilder errorMessage = new StringBuilder();
    while ((errorLine = errorReader.readLine()) != null) {
        errorMessage.append(errorLine);
    }
    System.err.println("Error executing Python script. Error message: " + errorMessage.toString());
    model.addAttribute("errorMessage", errorMessage.toString());
    return "error";
    }
} catch (Exception e) {
    e.printStackTrace();
    return "error";
}
    }

    @GetMapping("/imported-excel")
    public String showImported(Model model){
        List<F4Detail> lc = f4detailRepository.findAll();
        model.addAttribute("localeDataList", lc);
        return "imported-excel";

    }
    @GetMapping("/search/lokal")
    public String searchLocal(Model model){
        
        return "f4_search_lokal";

    }
    @GetMapping("/search/district")
    public String searchDistrict(Model model){
        
        return "f4_search_district";

    }
    @GetMapping("/search/wkno")
    public String searchWeeknumber(Model model){
        
        return "f4_search_wkno";

    }
    @GetMapping("/search/districtwkno")
    public String searchDistrictWeeknumber(Model model){
        List<District> districts = districtRepository.findAll();
        model.addAttribute("districts", districts);
        return "f4_search_wknodistrict";

    }
    @GetMapping("/search/lokal/{searchText}")
    public String searchlocalResults(@PathVariable String searchText, Model model, Locale locale) {
        List<F4Detail> ld = f4detailRepository.findByLocale(searchText);
      
        model.addAttribute("f4details", ld);
        return "f4lokal-search-result";
    }
    @GetMapping("/search/districtwkno/{searchText}/{searchDistrict}")
    public String searchdistwknoResults(@PathVariable String searchText,@PathVariable String searchDistrict,Model model, Locale locale) {
        List<F4Detail> ld = f4detailRepository.findByDistrictReported(searchDistrict,searchText);
        model.addAttribute("f4details", ld);
        return "f4wknodistrict-search-result";
    }
    @GetMapping("/search/district/{searchText}")
    public String searchdistrictResults(@PathVariable String searchText, Model model, Locale locale) {
        List<F4Detail> ld = f4detailRepository.findByDistrict(searchText);
      
        model.addAttribute("f4details", ld);
        return "f4district-search-result";
    }
    @GetMapping("/search/wkno/{searchText}")
    public String searchwknoResults(@PathVariable String searchText, Model model, Locale locale) {
        List<F4Detail> ld = f4detailRepository.findByReported(searchText);
      
        model.addAttribute("f4details", ld);
        return "f4wkno-search-result";
    }
    @GetMapping("/import/perweek")
    public String importWeek(Model model){
        
        return "f4_import_wkno";

    }

    @GetMapping("/import/perweek/{file}")
    public String importF4Data(Model model, @PathVariable ("file") String file) {
        String weekNumber = "";
        Pattern pattern = Pattern.compile("week(\\d+)");
        Matcher matcher = pattern.matcher(file);
        
        if (matcher.find()) {
            weekNumber = matcher.group(1);
        }
        
        // format the week number as "XX-YYYY"
        String formattedWeekNumber = weekNumber + "-2023";
       
try {
    
    String pythonScriptPath = "H:/uswofinal/remittance/process_data_perweek.py";

    ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath,file);
    
    Process process = processBuilder.start();
    
    // Wait for the process to complete
    int exitCode = process.waitFor();
    
    if (exitCode == 0) {
        System.out.println("Python script executed successfully.");
        List<F4Detail> lc = f4detailRepository.findByReported(formattedWeekNumber);
        model.addAttribute("localeDataList", lc);
        return "imported-excel";
    } else {
         // Get the error stream from the process
    InputStream errorStream = process.getErrorStream();
    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
    String errorLine = null;
    StringBuilder errorMessage = new StringBuilder();
    while ((errorLine = errorReader.readLine()) != null) {
        errorMessage.append(errorLine);
    }
    System.err.println("Error executing Python script. Error message: " + errorMessage.toString());
    model.addAttribute("errorMessage", errorMessage.toString());
    return "error";
    }
} catch (Exception e) {
    e.printStackTrace();
    return "error";
}
    }

    @GetMapping("/addrecord")
    public String addTransaction(Model model){
        model.addAttribute("f4detail", new F4Detail());
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);
        return "f4detail-add";

    } 
    @PostMapping("/save")
    public String saveRemittance(@ModelAttribute("f4detail") F4Detail f4detail, Model model)
            {
        
        f4detailRepository.save(f4detail);
        String selectSql = "SELECT b.locale, wkno, c.district,uswo,cfolocale,cfointl,lingap,rcentral,rtotal  FROM f4detail a, lokal b, districts c"
                +
                " WHERE a.lcode=b.lcode AND a.did=b.did and a.did=c.did;";
        List<F4Detail> f4List = jdbcTemplate.query(selectSql, new F4DetailRowMapper());
        model.addAttribute("f4list", f4List);
        return "F4list";

    }   
}

