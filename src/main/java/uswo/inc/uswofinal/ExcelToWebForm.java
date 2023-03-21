package uswo.inc.uswofinal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelToWebForm {
    
    public static void main(String[] args) throws IOException {
        // Load the Excel file
        String filePath = "examples.xlsx";
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet("MIC_Remittance");
        
        // Create a formula evaluator to evaluate the formulas in the sheet
        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        DataFormatter dataFormatter = new DataFormatter();
        
        // Create a list of maps to hold the form data
        List<Map<String, Object>> formDataList = new ArrayList<>();
        
        // Iterate through each row in the sheet
        for (Row row : sheet) {
            Map<String, Object> formData = new HashMap<>();
            
            // Iterate through each cell in the row
            for (Cell cell : row) {
                // Evaluate the cell formula and store the result in the form data map
                String cellValue = dataFormatter.formatCellValue(cell, formulaEvaluator);
                formData.put(getColumnName(cell.getColumnIndex()), cellValue);
            }
            
            // Add the form data map to the list
            formDataList.add(formData);
        }
        
        // Generate the web form HTML using the form data list
        String formHtml = generateWebFormHtml(formDataList);
        
        // Print the form HTML to the console
        System.out.println(formHtml);
    }
    
    // Helper method to convert column index to column name (e.g. 0 -> A, 1 -> B, etc.)
    private static String getColumnName(int index) {
        StringBuilder sb = new StringBuilder();
        while (index >= 0) {
            int remainder = index % 26;
            sb.insert(0, (char) ('A' + remainder));
            index = (index / 26) - 1;
        }
        return sb.toString();
    }
    
    // Helper method to generate the web form HTML using the form data list
    private static String generateWebFormHtml(List<Map<String, Object>> formDataList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Web Form</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<form>");
        for (Map<String, Object> formData : formDataList) {
            for (Map.Entry<String, Object> entry : formData.entrySet()) {
                sb.append("<label>").append(entry.getKey()).append(": </label>");
                sb.append("<input type='text' name='").append(entry.getKey()).append("' value='").append(entry.getValue()).append("'><br>");
            }
        }
        sb.append("</form>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}
