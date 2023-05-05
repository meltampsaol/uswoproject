import openpyxl
from sqlalchemy import create_engine, text
import mysql.connector

#establishing the connection
conn = mysql.connector.connect(user='root', password='mbt073233', host='localhost', database='uswo_project2923')

#engine = create_engine('mysql+pymysql://root:mbt073233@localhost/uswo_project2923')
filename = "MICOGN_week17_2023.xlsx"
wb = openpyxl.load_workbook(filename=filename, read_only=True, data_only=True)
ws = wb["MICF4Details"]

# Find the row number that satisfies the condition lcode=62
row_number = None
for row in ws.iter_rows(min_row=5, min_col=28, max_col=28):
    if row[0].value == 62:
        row_number = row[0].row
        break

# Get the values of uswo and cfototal
if row_number is not None:
    uswo = ws[f"T{row_number}"].value or 0.00
    cfototal = ws[f"F{row_number}"].value * 0.15 if ws[f"F{row_number}"].value else 0.00
    cfolocale = ws[f"F{row_number}"].value * 0.10 if ws[f"F{row_number}"].value else 0.00
    cfointl = ws[f"F{row_number}"].value * 0.10 if ws[f"F{row_number}"].value else 0.00
    lcode = ws[f"AB{row_number}"].value
    did = ws[f"AC{row_number}"].value
    wkno = ws[f"AE{row_number}"].value

    #conn = engine.connect()
    #result=conn.execute(text("INSERT INTO remit (lcode, did, wkno, cfremit, lfremit, cfintl) VALUES (:lcode, :did, :wkno, :cfremit, :lfremit, :cfintl)"),
    #             {"lcode": '62', "did": '4', "wkno": '17-2023', "cfremit": float(cfolocale), "lfremit": float(uswo), "cfintl": float(cfointl)})


cursor = conn.cursor()
# Preparing SQL query to INSERT a record into the database.
sql = """INSERT INTO remit(
   lcode, did, wkno, cfremit, lfremit, cfintl)
   VALUES ('62', '4', '17-2023', 1, 1, 1)"""
try:
   # Executing the SQL command
  result=cursor.execute(sql)
  if result.rowcount > 0:
     print(f"{result.rowcount} row(s) inserted")
  else:
        print("Insertion failed")
        print(f"uswo: {uswo}, cfototal: {cfototal:.2f}")
        print(f"cfolocale: {cfolocale:.2f}, cfointl: {cfointl:.2f}")
        print("wkno", wkno)
        print("district code:", did)
        print("locale code:", lcode)
   # Commit your changes in the database
        conn.commit()
except:
   # Rolling back in case of error
   conn.rollback()

# Closing the connection
conn.close()
wb.close()
