from click import style
from openpyxl.styles import Font
from openpyxl.styles.colors import Color
from openpyxl.styles import Alignment
import mysql.connector
import pandas as pd
from decimal import Decimal, getcontext
import openpyxl

# Set the decimal precision to 2
ctx = getcontext()
ctx.prec = 2

# Connect to the MySQL database
cnx = mysql.connector.connect(user='root', password='mbt073233',
                              host='localhost', database='uswo_project2923')
cursor = cnx.cursor()

# Define the query to retrieve the data from the f4detail table
query = "SELECT b.locale,uswo,'04-25-23' as deadline,cfolocale, cfointl,rlingap, rcentral,rtotal FROM f4detail a, lokal b WHERE a.lcode=b.lcode and reported = '06-2023' AND a.did = 9"
cursor.execute(query)
result = cursor.fetchall()

# Convert the result set to a pandas DataFrame
df_f4 = pd.DataFrame(result, columns=['locale','uswo','deadline','cfolocale', 'cfointl','rlingap', 'rcentral','rtotal'])

# Load the Excel file and select the worksheet
filename = r'MICOGN_template.xlsx'
book = openpyxl.load_workbook(filename)
sheet = book['OGN_Remittance']

# Assign the values to the specified cells in the Excel worksheet
for i, row in df_f4.iterrows():
    sheet.cell(row=i+5, column=2, value=row['locale'])
    sheet.cell(row=i+5, column=3, value=row['deadline'])
    sheet.cell(row=i+5, column=5, value=row['uswo'])
    sheet.cell(row=i+5, column=6, value=row['cfolocale'])
    sheet.cell(row=i+5, column=7, value=row['cfointl'])
    sheet.cell(row=i+5, column=9, value=row['rlingap'])
    sheet.cell(row=i+5, column=10, value=row['rcentral'])
    sheet.cell(row=i+5, column=11, value=row['rtotal'])
    for j in [4, 8]: # Set the other cells to blank
        sheet.cell(row=i+5, column=j, value='')

# Save the updated Excel file



# Set the value of cell D1
sheet['D1'] = 6
# Font properties
font = Font(color='FFFF0000', bold=True, size=12)
align = Alignment(horizontal='center')
sheet['D1'].font = font
sheet['D1'].alignment = align


book.save(filename)

# Close the database connection
cursor.close()
cnx.close()
