package com.company.pump_data_service.observer.implementation;

import com.company.pump_data_service.model.PumpPayload;
import com.company.pump_data_service.observer.PumpDataObserver;
import org.springframework.stereotype.Component;

@Component
public class AlertObserver implements PumpDataObserver {

    @Override
    public void onPumpData(PumpPayload payload) {
        if ("BROKEN".equals(payload.getStatus())) {
            System.out.println("ALERTA en: " + payload.getTimestamp()
                    + " → notificar Telegram");
        }
    }
}
