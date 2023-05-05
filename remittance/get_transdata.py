import openpyxl
import mysql.connector
import argparse

# Establishing the connection
conn = mysql.connector.connect(user='root', password='mbt073233', host='127.0.0.1', database='uswo_project2923')

# Parse command-line arguments
parser = argparse.ArgumentParser(description='Insert data into the remit table.')
parser.add_argument('lcode', type=int, help='the lcode value to insert')
args = parser.parse_args()

# Load Excel data
filename = "MICOGN_week17_2023.xlsx"
wb = openpyxl.load_workbook(filename=filename, read_only=True, data_only=True)
ws = wb["MICF4Details"]

# Find the row number that satisfies the condition lcode=args.lcode
row_number = None
for row in ws.iter_rows(min_row=5, min_col=28, max_col=28):
    if row[0].value == args.lcode:
        row_number = row[0].row
        break

# Get the values of uswo and cfototal
if row_number is not None:
    uswo = ws[f"T{row_number}"].value or 0.00
    cfototal = ws[f"F{row_number}"].value * 0.15 if ws[f"F{row_number}"].value else 0.00
    cfolocale = ws[f"F{row_number}"].value * 0.10 if ws[f"F{row_number}"].value else 0.00
    cfointl = ws[f"F{row_number}"].value * 0.05 if ws[f"F{row_number}"].value else 0.00
    lcode = ws[f"AB{row_number}"].value
    did = ws[f"AC{row_number}"].value
    wkno = ws[f"AE{row_number}"].value

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
        print("Record inserted successfully")
        print(f"uswo: {uswo}, cfototal: {cfototal:.2f}")
        print(f"cfolocale: {cfolocale:.2f}, cfointl: {cfointl:.2f}")
        print("wkno", wkno)
        print("district code:", did)
        print("locale code:", lcode)
        conn.commit()
    except Exception as e:
        print(f"Error inserting record: {e}")
        conn.rollback()

# Closing the connection
conn.close()
wb.close()
