package uswo.inc.uswofinal.app;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PythonRunner {
    public static void insertRecord(int lcode, int did,String wkno2) {

        int xlcode = lcode;
        String wkno = wkno2.replace("-", "_");
        // Establishing the connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uswo_project2923", "root", "mbt073233");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load Excel data
        String filename = "H://uswofinal/remittance/MICOGN_week"+wkno+".xlsx";
        Workbook workbook = null;
        Sheet ws = null;
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            workbook = WorkbookFactory.create(file);
            if(did==4){
            ws = workbook.getSheet("MICF4Details");
            }else{
            ws = workbook.getSheet("OGNF4Details");    
            }
            // Find the row number that satisfies the condition lcode=args.lcode
            int row_number = -1;
            for (Row row : ws) {
                Cell cell = row.getCell(27);
                if (cell != null && cell.getCellType() == CellType.NUMERIC && cell.getNumericCellValue() == xlcode) {
                    row_number = row.getRowNum();
                    break;
                }
            }
            // Get the values of uswo and cfototal
            float uswo = 0.0f;
            float cfototal = 0.0f;
            float cfointl = 0.0f;
            float f9 = 0.0f;
            float lingap = 0.0f;
            float central = 0.0f;
            float district = 0.0f;
            float local = 0.0f;
            String lcode_str = "";
            
            if (row_number != -1) {
                Row row = ws.getRow(row_number);
                Cell cell1 = row.getCell(19);
                Cell cell2 = row.getCell(5);  //thlocale
                Cell cell6 = row.getCell(6);  //thdistrict
                Cell cell7 = row.getCell(7);  //lingap
                Cell cell8 = row.getCell(8);  //thanksgiving
                Cell cell25 = row.getCell(25);  //central

                local = (float) cell2.getNumericCellValue();
                district = (float) cell6.getNumericCellValue();
                lingap = (float) cell7.getNumericCellValue();
                f9 = (float) cell8.getNumericCellValue();
                central = (float) cell25.getNumericCellValue();

                uswo = (float) cell1.getNumericCellValue();
                cfototal = (float) cell2.getNumericCellValue() * 0.15f;
                cfointl = (float) cell2.getNumericCellValue() * 0.05f;
                if (row.getCell(27).getCellType() == CellType.NUMERIC) {
                    lcode_str = String.valueOf((int) row.getCell(27).getNumericCellValue());
                } else {
                    lcode_str = row.getCell(27).getStringCellValue();
                }
                
            }

            // Insert record into the database
            PreparedStatement stmt = null;
            PreparedStatement stmt2 = null;
            try {
                String sql = "INSERT INTO remit (lcode, did, wkno, cfremit, lfremit, cfintl) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, lcode_str);
                stmt.setInt(2, did);
                stmt.setString(3, wkno);
                stmt.setFloat(4, cfototal);
                stmt.setFloat(5, uswo);
                stmt.setFloat(6,cfointl);
                stmt.executeUpdate();

                String sql2 = "INSERT INTO f9 (lcode, dcode, wkno, f9, district, lingap, central, lokal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stmt2 = conn.prepareStatement(sql2);
                stmt2.setString(1, lcode_str);
                stmt2.setInt(2, did);
                stmt2.setString(3, wkno);
                stmt2.setFloat(4, f9);
                stmt2.setFloat(5, district);
                stmt2.setFloat(6,lingap);
                stmt2.setFloat(7,central);
                stmt2.setFloat(8,local);
                stmt2.executeUpdate();
                System.out.println("Record inserted successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                    if (workbook != null)
                        workbook.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
