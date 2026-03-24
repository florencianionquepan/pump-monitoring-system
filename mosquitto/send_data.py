import pandas as pd
import time
import json
import paho.mqtt.client as mqtt
import os

broker = os.getenv("MQTT_BROKER", "localhost")
port = int(os.getenv("MQTT_PORT", 1883))
topic = os.getenv("MQTT_TOPIC", "scada/pump/data")

df = pd.read_csv("sensor.csv")

def on_connect(client, userdata, flags, rc):
    print("Conectado. Código:", rc)

client = mqtt.Client()
client.on_connect = on_connect

client.connect(broker, port, 60)
client.loop_start()

time.sleep(1)  # esperar conexión

for _, row in df.iterrows():
    payload = {
        "timestamp": row["timestamp"],
        "motor": {
            "speed_rpm": row["sensor_04"],
            "current_a": row["sensor_05"],
            "active_power_kw": row["sensor_06"]
        },
        "pump": {
            "inlet_flow_m3h": row["sensor_34"],
            "discharge_flow_m3h": row["sensor_35"],
            "inlet_pressure_bar": row["sensor_48"],
            "discharge_pressure_bar": row["sensor_50"]
        },
        "condition": {
            "motor_vibration_mm_s": row["sensor_00"],
            "pump_vibration_mm_s": row["sensor_18"],
            "thrust_bearing_temp_c": row["sensor_40"]
        },
        "status": row["machine_status"]
    }

    client.publish(topic, json.dumps(payload))
    time.sleep(1)

client.loop_stop()
client.disconnect()