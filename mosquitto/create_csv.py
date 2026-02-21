import pandas as pd

# Cargar CSV completo
df = pd.read_csv("sensor.csv")
df['timestamp'] = pd.to_datetime(df['timestamp'])

# sensores clave para detectar picos
key_sensors = ["sensor_04", "sensor_05", "sensor_06", "sensor_34", "sensor_35",
               "sensor_18", "sensor_40", "sensor_48", "sensor_50"]

# Función para tomar registros extremos (picos) de cada sensor
def select_peaks(df, sensors, quantile_low=0.05, quantile_high=0.95):
    peak_rows = pd.DataFrame()
    for sensor in sensors:
        low = df[sensor].quantile(quantile_low)
        high = df[sensor].quantile(quantile_high)
        peaks = df[(df[sensor] <= low) | (df[sensor] >= high)]
        peak_rows = pd.concat([peak_rows, peaks])
    # Quitar duplicados
    peak_rows = peak_rows.drop_duplicates()
    return peak_rows

# Seleccionar picos
peaks_df = select_peaks(df, key_sensors)

# Muestreo estratificado por machine_status para asegurar representación
normal_n = min(100, len(df[df['machine_status']=='NORMAL']))
broken_n = min(50, len(df[df['machine_status']=='BROKEN']))
recovery_n = min(50, len(df[df['machine_status']=='RECOVERY']))

subset_df = pd.concat([
    df[df['machine_status'] == 'NORMAL'].sample(n=normal_n, random_state=42),
    df[df['machine_status'] == 'BROKEN'].sample(n=broken_n, random_state=42),
    df[df['machine_status'] == 'RECOVERY'].sample(n=recovery_n, random_state=42),
    peaks_df
])

# Quitar duplicados y ordenar por timestamp
subset_df = subset_df.drop_duplicates().sort_values('timestamp')

# Guardar el subset en CSV
subset_df.to_csv("pump_sensor_subset.csv", index=False)
print(f"Subset creado con {len(subset_df)} registros.")