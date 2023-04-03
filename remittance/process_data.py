import pandas as pd
from sqlalchemy import create_engine, text
import openpyxl
import os

engine = create_engine('mysql+pymysql://root:mbt073233@localhost/uswo_project2923')

# Set the path of the Excel files
path = "H:/uswofinal/remittance/"

for week in range(6, 13):
    filename = os.path.join(path, f"MICOGN_week{week:02d}_2023.xlsx")
    df_ognf4 = pd.read_excel(filename, sheet_name='OGNF4Details', header=3, usecols="A:AE", nrows=25)
    df_micf4 = pd.read_excel(filename, sheet_name='MICF4Details', header=3, usecols="A:AE", nrows=25)

    # Combine the two DataFrames into one
    df = pd.concat([df_ognf4, df_micf4], axis=0, ignore_index=True)

    # Set the default value of all empty or blank cells to 0
    df.fillna(0, inplace=True)

    # Remove rows where Sunday and Thursday are 0 or null
    df.drop(df[(df['sunday'] == 0) & (df['thursday'] == 0)].index, inplace=True)

    # Convert all double columns to decimal(10,2)
    for col in df.select_dtypes(include=['float64']).columns:
        df[col] = df[col].astype('float32').round(2)

    # Use the pandas DataFrame method "to_sql" to encode the data into a MySQL table
    df.to_sql("f4detail", engine, if_exists="replace", index=False, chunksize=1000)

    # Remove rows where the locale is blank
    conn = engine.connect()
    query = text(f"DELETE FROM f4detail WHERE locale = ''")
    conn.execute(query)
    conn.close()
