package com.company.pump_data_service.config;

import com.company.pump_data_service.observer.PumpDataObserver;
import com.company.pump_data_service.observer.PumpDataPublisher;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ObserverRegistryConfig {

    // Spring inyecta solo los observers que existan como @Component.
    private final List<PumpDataObserver> observers;
    private final PumpDataPublisher publisher;

    public ObserverRegistryConfig(List<PumpDataObserver> observers,
                                  PumpDataPublisher publisher) {
        this.observers = observers;
        this.publisher = publisher;
    }

    @PostConstruct
    public void registerAll() {
        observers.forEach(publisher::register);
        System.out.println("Observers registrados: " +
                observers.stream()
                        .map(o -> o.getClass().getSimpleName())
                        .toList()
        );
    }
}
