import pandas as pd
from sqlalchemy import create_engine, text
import os
from decimal import Decimal, getcontext

# Set the precision context for Decimal objects
getcontext().prec = 2
# Set the decimal context with 2 decimal places
ctx = getcontext()
ctx.prec = 10
ctx.rounding = 'ROUND_HALF_UP'

engine = create_engine('mysql+pymysql://root:mbt073233@localhost/uswo_project2923')

path = "H:/uswofinal/remittance/"

for week in range(6, 13):
    filename = os.path.join(path, f"MICOGN_week{week:02d}_2023.xlsx")
    df_ognf4 = pd.read_excel(filename, sheet_name='OGNF4Details', header=0, usecols="A:AE")
    df_micf4 = pd.read_excel(filename, sheet_name='MICF4Details', header=0, usecols="A:AE")

    # Combine the two DataFrames into one
    df = pd.concat([df_ognf4, df_micf4], axis=0, ignore_index=True)

    # Remove rows where the locale is blank
    df.dropna(subset=['locale'], inplace=True)

    # Replace null values with 0.00 except for lcode, locale, did, wkno, and reported fields
    cols_to_exclude = ['lcode', 'locale', 'did', 'wkno', 'reported']
    df.fillna(value={col: 0.00 for col in df.columns if col not in cols_to_exclude}, inplace=True)

    # Convert all non-excluded columns to Decimal with 2 decimal places
    cols_to_exclude = ['lcode', 'locale', 'did', 'wkno', 'reported']
    for col in df.columns:
      if col not in cols_to_exclude:
        df[col] = df[col].apply(lambda x: Decimal(str(x)).quantize(Decimal('0.01')))


    # Delete rows where rtotal is 0 or 0.00
    #df = df[df['rtotal'] != Decimal('0.00')]

    # Use the pandas DataFrame method "to_sql" to encode the data into a MySQL table
    df.to_sql("f4detail", engine, if_exists="append", index=False, chunksize=1000)

    # Remove rows where the locale is blank
    
    
conn = engine.connect()
query = text(f"DELETE FROM f4detail WHERE locale = ''")
conn.execute(query)
conn.close()


    # Remove rows where rtotal is 0 or 0.00
#conn = engine.connect()
#query = text(f"DELETE FROM f4detail WHERE rtotal = 0")
#conn.execute(query)
#conn.close()