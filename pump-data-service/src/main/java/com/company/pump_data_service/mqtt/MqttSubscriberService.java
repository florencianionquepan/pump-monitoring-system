package com.company.pump_data_service.mqtt;

import com.company.pump_data_service.model.PumpPayload;
import com.company.pump_data_service.observer.PumpDataPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttSubscriberService implements InitializingBean, DisposableBean {

    @Value("${mqtt.topic:scada/pump/data}")
    private String topic;

    private final MqttClient mqttClient;
    private final MqttConnectOptions connectOptions;
    private final PumpDataPublisher publisher;

    private final ObjectMapper mapper = new ObjectMapper();

    public MqttSubscriberService(MqttClient mqttClient,
                                 MqttConnectOptions connectOptions,
                                 PumpDataPublisher publisher) {
        this.mqttClient = mqttClient;
        this.connectOptions = connectOptions;
        this.publisher = publisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mqttClient.connect(connectOptions);
        mqttClient.subscribe(topic, this::messageArrived);
    }

    private void messageArrived(String topic, MqttMessage message) {
        try {
            PumpPayload p = mapper.readValue(
                    new String(message.getPayload()), PumpPayload.class
            );
            publisher.notifyObservers(p);
        } catch (Exception e) {
            System.err.println("Error parseando payload: " + e.getMessage());
        }
    }

    @Override
    public void destroy() throws Exception {
        if (mqttClient.isConnected()) mqttClient.disconnect();
    }
}
