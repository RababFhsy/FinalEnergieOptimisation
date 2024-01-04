import pandas as pd
import mysql.connector
from datetime import datetime

# MySQL connection configuration
mysql_config = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'feoptimisationenergie'
}

# CSV file path
csv_file_path = 'testset.csv'

# MySQL table name
table_name = 'anomalie'

# Load CSV data into a Pandas DataFrame
df = pd.read_csv(csv_file_path)

# Convert 'datetime_utc' to datetime format
df['datetime_utc'] = pd.to_datetime(df['datetime_utc'], format='%Y%m%d-%H:%M')

# Filter DataFrame for January 2016
df_jan_2016 = df[(df['datetime_utc'].dt.year == 2000) & (df['datetime_utc'].dt.month == 1)]

# Convert 'datetime_utc' to date format
df_jan_2016['date_anomalie'] = df_jan_2016['datetime_utc'].dt.date

# Group by date and calculate the mean temperature
grouped_data = df_jan_2016.groupby('date_anomalie', as_index=False)['tempm'].mean()

# Connect to MySQL
conn = mysql.connector.connect(**mysql_config)
cursor = conn.cursor()

# Insert grouped data into MySQL table with INSERT IGNORE
for _, row in grouped_data.iterrows():
    date_anomalie = row['date_anomalie']
    mean_tempm = row['tempm']

    insert_query = f"INSERT IGNORE INTO {table_name} (date_anomalie, zone_normale_min) VALUES ('{date_anomalie}', {mean_tempm})"
    cursor.execute(insert_query)

# Commit changes and close connections
conn.commit()
cursor.close()
conn.close()

# Vous pouvez également imprimer un message pour indiquer que les données ont été importées avec succès.
print(f'Data from {csv_file_path} for January 2016 has been successfully imported into MySQL table {table_name}.')
