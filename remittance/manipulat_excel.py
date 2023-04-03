import pandas as pd
from sqlalchemy import create_engine, text
import openpyxl
import os

engine = create_engine('mysql+pymysql://root:mbt073233@localhost/uswo_project2923')

for week in range(6, 12):
    filename = f"Remittance_MICOGN_week{week:02d}_2023.xlsx"
    df_ognf4 = pd.read_excel(filename, sheet_name='OGNF4Details', header=3, skiprows=range(0,3), nrows=25)
    df_micf4 = pd.read_excel(filename, sheet_name='MICF4Details', header=3, skiprows=range(0,3), nrows=25)

    # Add the two new columns "wkno" and "dcode" to the DataFrame
    df_ognf4["wkno"] = f"{week:02d}-2023"
    df_ognf4["dcode"] = 9
    df_micf4["wkno"] = f"{week:02d}-2023"
    df_micf4["dcode"] = 4

    # Combine the two DataFrames into one
    df = pd.concat([df_ognf4, df_micf4], axis=0, ignore_index=True)

    # Remove rows with missing values
    df.dropna(subset=['lcode', 'locale', 'rtotal'], inplace=True)

    # Set the default value of all empty or blank cells to 0
    df.fillna(0, inplace=True)

    # Filter out rows where rtotal is equal to 0
    df = df[df['rtotal'] != 0]

    # Use the pandas DataFrame method "to_sql" to encode the data into a MySQL table
    df.to_sql("remittance", engine, if_exists="append", index=False)

    wb = openpyxl.load_workbook(filename)
    dcode = wb['OGNF4Details']['B1']
    dcode_value = dcode.value

    conn = engine.connect()
    query = text(f"UPDATE remittance SET dcode = {dcode_value} WHERE wkno = '{week:02d}-2023'")
    conn.execute(query)
    conn.close()

    # Remove rows where the locale is blank
    conn = engine.connect()
    query = text(f"DELETE FROM remittance WHERE locale = '' AND wkno = '{week:02d}-2023'")
    conn.execute(query)
    conn.close()
