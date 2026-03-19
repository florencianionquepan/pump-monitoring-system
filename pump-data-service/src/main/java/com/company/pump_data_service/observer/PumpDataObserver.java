package com.company.pump_data_service.observer;

import com.company.pump_data_service.model.PumpPayload;

public interface PumpDataObserver {
    void onPumpData(PumpPayload payload);
}
