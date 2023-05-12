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
query = "SELECT b.locale,'04-25-23' as deadline,uswo,cfolocale, cfointl,rlingap, rcentral FROM f4detail a, lokal b WHERE a.lcode=b.lcode and reported = '16-2023' AND a.did = 9"
cursor.execute(query)
result = cursor.fetchall()

# Convert the result set to a pandas DataFrame
df_f4 = pd.DataFrame(result, columns=['locale','deadline','uswo','cfolocale', 'cfointl','rlingap', 'rcentral'])

# Load the Excel file and select the worksheet
filename = r'MICOGN_template.xlsx'
book = openpyxl.load_workbook(filename)
sheet = book['MIC_Remittance']

# Assign the values to the specified cells in the Excel worksheet
headers = ['','locale','deadline','','uswo', 'cfolocale', 'cfointl','','rlingap', 'rcentral']
for i, row in enumerate(df_f4.to_dict('records')):
    for j, header in enumerate(headers):
        cell = sheet.cell(row=i+5, column=j+1)
        if header == '':
            cell.value = ''
            continue
        if cell.coordinate in sheet.merged_cells:
            continue
        cell.value = row[header]
        


# Save the updated Excel file



# Set the value of cell D1
sheet['D1'] = 16
# Font properties
font = Font(color='FFFF0000', bold=True, size=12)
align = Alignment(horizontal='center')
sheet['D1'].font = font
sheet['D1'].alignment = align


book.save(filename)

# Close the database connection
cursor.close()
cnx.close()
