import java.lang.System as System
import java.lang.Exception as Exception
import jxl.Workbook as Workbook
import jxl.read.biff.BiffException as BiffException
import mysql.connector
import sys

# Establishing the connection
conn = mysql.connector.connect(user='root', password='mbt073233', host='127.0.0.1', database='uswo_project2923')

lcode = sys.argv[1]
xlcode = int(lcode)

# Load Excel data
filename = "MICOGN_week17_2023.xls"
try:
    workbook = Workbook.getWorkbook(java.io.File(filename))
    ws = workbook.getSheet(0)
except BiffException as e:
    System.out.println(e)

# Find the row number that satisfies the condition lcode=args.lcode
row_number = None
for row in range(4, ws.getRows()):
    if int(ws.getCell(27, row).getContents()) == xlcode:
        row_number = row
        break

# Get the values of uswo and cfototal
if row_number is not None:
    uswo = float(ws.getCell(19, row_number).getContents() or 0.00)
    cfototal = float(ws.getCell(5, row_number).getContents() or 0.00) * 0.15
    cfolocale = float(ws.getCell(5, row_number).getContents() or 0.00) * 0.10
    cfointl = float(ws.getCell(5, row_number).getContents() or 0.00) * 0.05
    lcode = ws.getCell(27, row_number).getContents()
    did = ws.getCell(28, row_number).getContents()  
    wkno = ws.getCell(30, row_number).getContents()

cursor = conn.cursor()

# Preparing SQL query to INSERT a record into the database.
sql = """INSERT INTO remit (
                lcode, did, wkno, cfremit, lfremit, cfintl
            ) VALUES (
                %s, %s, %s, %s, %s, %s
            )"""
values = (lcode, did, wkno, cfototal, uswo, cfointl)

try:
    # Executing the SQL command
    result = cursor.execute(sql, values)
    System.out.println("Record inserted successfully")
    conn.commit()
except Exception as e:
    System.out.println(e)
    conn.rollback()

# Closing the connection
conn.close()
workbook.close()
