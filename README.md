# Pump Data Service

Sistema de monitoreo de una bomba industrial que simula el flujo de datos de un equipo con sensores conectados a un PLC. Recibe datos vía MQTT, los procesa mediante el patrón Observer y los expone a través de una API REST.

---

## Descripción general

El proyecto simula el flujo de datos de un PLC real: un script Python lee registros de un CSV y los publica en un broker MQTT local. Un servicio Spring Boot suscripto al broker recibe cada mensaje, lo parsea y notifica a sus observers, que pueden persistir los datos, evaluar alertas o enviar notificaciones externas.

---

## Estructura del repositorio

```
.
├── mosquitto/                  # Script Python y datos de sensores
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

---

## Dataset

Los datos de sensores utilizados en este proyecto provienen del dataset público [Pump Sensor Data](https://www.kaggle.com/datasets/nphantawee/pump-sensor-data/data) disponible en Kaggle. El dataset contiene lecturas de sensores de motor, bomba y condición operativa de una bomba industrial, con etiquetas de estado (`NORMAL`, `BROKEN`, `RECOVERING`).

---

> El script lee `pump_sensor_subset.csv` y publica cada fila como un mensaje JSON en el topic `scada/pump/data`.

---

## Modelo de datos

Cada mensaje publicado tiene la siguiente estructura JSON:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "motor": {
    "rpm": 1450.0,
    "temperatura": 72.3
  },
  "pump": {
    "presion": 4.2,
    "caudal": 38.5
  },
  "condition": {
    "vibracion": 0.12
  },
  "status": "NORMAL"
}
```

---

## Próximos pasos

- [ ] Persistencia en base de datos (PostgreSQL)
- [ ] Notificaciones
- [ ] Dockerización de todos los componentes
