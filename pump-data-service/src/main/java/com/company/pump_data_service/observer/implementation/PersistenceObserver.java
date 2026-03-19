package com.company.pump_data_service.observer.implementation;

import com.company.pump_data_service.model.PumpPayload;
import com.company.pump_data_service.observer.PumpDataObserver;
import org.springframework.stereotype.Component;

@Component
public class PersistenceObserver implements PumpDataObserver {

    @Override
    public void onPumpData(PumpPayload payload) {
        // TODO: Conectar con repository
        System.out.println("Persistiendo registro: " + payload.getTimestamp()
                + " | status: " + payload.getStatus());
    }
}
