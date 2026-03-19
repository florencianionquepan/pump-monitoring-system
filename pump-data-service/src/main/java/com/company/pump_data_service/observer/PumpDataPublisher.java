package com.company.pump_data_service.observer;

import com.company.pump_data_service.model.PumpPayload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PumpDataPublisher {

    // CopyOnWriteArrayList es thread-safe — importante porque
    // MQTT llega en un hilo distinto al de Spring
    private final List<PumpDataObserver> observers = new CopyOnWriteArrayList<>();

    public void register(PumpDataObserver observer) {
        observers.add(observer);
        System.out.println("Observer registrado: " + observer.getClass().getSimpleName());
    }

    public void unregister(PumpDataObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(PumpPayload payload) {
        observers.forEach(obs -> {
            try {
                obs.onPumpData(payload);
            } catch (Exception e) {
                // Un observer que falla no debe romper a los demás
                System.err.println("Error en observer "
                        + obs.getClass().getSimpleName() + ": " + e.getMessage());
            }
        });
    }
}
