package uswo.inc.uswofinal.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import uswo.inc.uswofinal.model.F4Detail;
import uswo.inc.uswofinal.repository.F4DetailRepository;
import uswo.inc.uswofinal.repository.LocaleDataRepository;

@Controller
@RequestMapping("/f4")
public class LocaleDataController {

   

   

    @Autowired
    LocaleDataRepository localeDataRepository;
    
    @Autowired
    F4DetailRepository f4detailRepository;
    
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
    @GetMapping("/search/lokal/{searchText}")
    public String searchlocalResults(@PathVariable String searchText, Model model, Locale locale) {
        List<F4Detail> ld = f4detailRepository.findByLocale(searchText);
      
        model.addAttribute("f4details", ld);
        return "f4lokal-search-result";
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

    
}

