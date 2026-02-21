import pandas as pd
import matplotlib.pyplot as plt


df = pd.read_csv("sensor.csv")
df['timestamp'] = pd.to_datetime(df['timestamp'])

sensors = {
    "motor": ["sensor_04", "sensor_05", "sensor_06"],
    "pump": ["sensor_34", "sensor_35", "sensor_48", "sensor_50"],
    "condition": ["sensor_00", "sensor_18", "sensor_40"]
}

for group, cols in sensors.items():
    plt.figure(figsize=(12,6))
    for col in cols:
        plt.plot(df['timestamp'], df[col], label=col)
    plt.title(f"{group.capitalize()} sensors over time")
    plt.xlabel("Timestamp")
    plt.ylabel("Value")
    plt.legend()
    plt.tight_layout()
    # Guardar como imagen
    plt.savefig(f"{group}_sensors.png")
    plt.close()