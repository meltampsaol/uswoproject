package uswo.inc.uswofinal.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
import uswo.inc.uswofinal.model.LocaleData;
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
    @GetMapping("/excel-export")
    public void exportExcel(Model model, HttpServletResponse response) throws IOException {
    
    String fileName = "MICOGN_week13_2023.xlsx";
    String filePath = "H:/uswofinal/remittance/" + fileName;
    try (FileInputStream file = new FileInputStream(filePath)) {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet worksheet = workbook.getSheet("OGNF4Details");
        List<F4Detail> f4Details = f4detailRepository.findByWknoAndDistrict("12-2023","MICRONESIA");
        for (F4Detail f4Detail : f4Details) {
            int lcode = f4Detail.getLcode();
            XSSFRow row = worksheet.getRow(lcode);
            if (row != null) {
                row.createCell(0).setCellValue(f4Detail.getLocale());
                row.createCell(27).setCellValue(f4Detail.getDistrict().getDid());
                row.createCell(28).setCellValue(f4Detail.getWkno());
                row.createCell(29).setCellValue(f4Detail.getReported());
                row.getCell(27).setCellType(CellType.NUMERIC);
                List<BigDecimal> dataList = Arrays.asList(f4Detail.getThursday(), f4Detail.getSunday(),
                        f4Detail.getCws(), f4Detail.getTotaloffering(), f4Detail.getThlocale(),
                        f4Detail.getThdistrict(), f4Detail.getLingap(), f4Detail.getThanksgiving(),
                        f4Detail.getReflocale(), f4Detail.getRefdistrict(), f4Detail.getRefcentral(),
                        f4Detail.getOffrefund(), f4Detail.getAlms(), f4Detail.getExplocale(),
                        f4Detail.getExpdistrict(), f4Detail.getExpcentral(), f4Detail.getExptotal(),
                        f4Detail.getRemainder(), f4Detail.getUswo(), f4Detail.getCfototal(),
                        f4Detail.getCfolocale(), f4Detail.getCfointl(), f4Detail.getRdistrict(),
                        f4Detail.getRlingap(), f4Detail.getRcentral(), f4Detail.getRtotal());
                for (int i = 1; i <= 26; i++) {
                    if (i != 27 && i != 28 && i != 29) {
                        if (dataList.get(i - 1) != null) {
                            row.createCell(i).setCellValue(dataList.get(i - 1).doubleValue());
                            row.getCell(i).setCellType(CellType.NUMERIC);
                        } else {
                            row.createCell(i).setCellValue(0.00);
                            row.getCell(i).setCellType(CellType.NUMERIC);
                        }
                    }
                }
            }
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    } catch (IOException e) {
        e.printStackTrace();
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

    @GetMapping("/search/lokal/{searchText}")
    public String searchlocalResults(@PathVariable String searchText, Model model) {
        List<F4Detail> ld = f4detailRepository.findByLocale(searchText);
        model.addAttribute("f4details", ld);
        return "f4lokal-search-result";
    }
}

