# Pump Data Service

Sistema de monitoreo de una bomba industrial que simula el flujo de datos de un equipo con sensores conectados a un PLC. Recibe datos vía MQTT, los procesa mediante el patrón Observer y los expone a través de una API REST.

---

## Descripción general

El proyecto simula el flujo de datos de un PLC real: un script Python lee registros de un CSV y los publica en un broker MQTT local. Un servicio Spring Boot suscripto al broker recibe cada mensaje, lo parsea y notifica a sus observers, que pueden persistir los datos, evaluar alertas o enviar notificaciones externas.

---

## Estructura del repositorio

```
.
├── Dockerfile.python           # Imagen Docker del publicador Python
├── Dockerfile.springboot       # Imagen Docker del servicio Spring Boot
├── docker-compose.yml          # Orquestación de los tres servicios
├── mosquitto/                  # Script Python y datos de sensores
│   ├── mosquitto.conf          # Configuración del broker MQTT
│   ├── requirements.txt        # Dependencias Python
│   ├── send_data.py            # Publica datos del CSV al broker MQTT
│   ├── create_csv.py           # Genera el subset de datos
│   ├── visualize_data.py       # Visualización exploratoria del dataset
│   ├── pump_sensor_subset.csv  # Dataset reducido usado en la simulación
│   └── sensor.csv              # Dataset completo
│
└── pump-data-service/          # Aplicación Spring Boot
    └── src/main/java/com/company/pump_data_service/
        ├── config/             # Configuración de MQTT y registro de observers
        ├── model/              # POJOs: PumpPayload, MotorData, PumpData, ConditionData
        ├── mqtt/               # Suscriptor MQTT — recibe y parsea mensajes
        ├── observer/           # Interfaz PumpDataObserver y PumpDataPublisher
        │   └── implementation/ # AlertObserver, PersistenceObserver
        ├── service/            # Lógica de negocio (StorageService)
        └── web/                # API REST (PumpDataController)
```

---

## Arquitectura

```
[CSV de sensores]
      │
      ▼
[Script Python]  →  [Broker MQTT]  →  [MqttSubscriberService]
                                               │
                                               ▼
                                       [PumpDataPublisher]
                                               │
                          ┌────────────────────┤
                          ▼                    ▼
               PersistenceObserver       AlertObserver
               (guarda datos)            (evalúa umbrales)
```

El patrón Observer desacopla completamente la recepción de mensajes MQTT de su procesamiento. Agregar un nuevo comportamiento (notificación por Telegram, etc.) implica solo crear una nueva clase que implemente `PumpDataObserver` — sin tocar el código existente.

---

## Tecnologías

| Componente | Tecnología |
|---|---|
| Broker de mensajes | Eclipse Mosquitto (MQTT) |
| Backend | Java 17 + Spring Boot |
| Publicador de datos | Python 3 |
| Comunicación | MQTT (tcp://localhost:1883) |
| Contenedores | Docker + Docker Compose |

---

## Dataset

Los datos de sensores utilizados en este proyecto provienen del dataset público [Pump Sensor Data](https://www.kaggle.com/datasets/nphantawee/pump-sensor-data/data) disponible en Kaggle. El dataset contiene lecturas de sensores de motor, bomba y condición operativa de una bomba industrial, con etiquetas de estado (`NORMAL`, `BROKEN`, `RECOVERING`).

---

## Modelo de datos

Cada mensaje publicado tiene la siguiente estructura JSON:

```json
{
  "timestamp": "2018-04-01 00:00:00",
  "motor": {
    "speed_rpm": 1450.0,
    "current_a": 32.1,
    "active_power_kw": 12.4
  },
  "pump": {
    "inlet_flow_m3h": 98.2,
    "discharge_flow_m3h": 95.7,
    "inlet_pressure_bar": 2.1,
    "discharge_pressure_bar": 4.8
  },
  "condition": {
    "motor_vibration_mm_s": 0.45,
    "pump_vibration_mm_s": 0.38,
    "thrust_bearing_temp_c": 62.3
  },
  "status": "NORMAL"
}
```

---

## Cómo levantar el proyecto

### Con Docker

Requiere Docker y Docker Compose instalados.

```bash
# clonar el repositorio
git clone https://github.com/florencianionquepan/pump-monitoring-system.git
cd pump-monitoring-system

# levantar todos los servicios
docker-compose up --build
```

Los servicios arrancan en orden automáticamente:
1. **mqtt-broker** — Eclipse Mosquitto en el puerto 1883
2. **pump-data-service** — Spring Boot en el puerto 8080
3. **pump-publisher** — script Python que empieza a publicar datos

Para detener:
```bash
docker-compose down
```

---

## Próximos pasos

- [ ] Persistencia en base de datos (PostgreSQL)
- [ ] Notificaciones por Telegram y WhatsApp
- [ ] Dashboard de monitoreo en tiempo real
