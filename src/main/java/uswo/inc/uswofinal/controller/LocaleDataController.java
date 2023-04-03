package uswo.inc.uswofinal.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uswo.inc.uswofinal.app.LocaleDataProcessor;
import uswo.inc.uswofinal.model.LocaleData;
import uswo.inc.uswofinal.repository.LocaleDataRepository;

@Controller
public class LocaleDataController {

    private final LocaleDataProcessor localeDataProcessor;

    @Autowired
    public LocaleDataController(LocaleDataProcessor localeDataProcessor) {
        this.localeDataProcessor = localeDataProcessor;
    }

    @Autowired
    LocaleDataRepository localeDataRepository;

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
        List<LocaleData> lc = localeDataRepository.findAll();
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

