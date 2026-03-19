package com.company.pump_data_service.model;

public class PumpData {
    private Double inlet_flow_m3h;
    private Double discharge_flow_m3h;
    private Double inlet_pressure_bar;
    private Double discharge_pressure_bar;

    public Double getInlet_flow_m3h() {
        return inlet_flow_m3h;
    }

    public void setInlet_flow_m3h(Double inlet_flow_m3h) {
        this.inlet_flow_m3h = inlet_flow_m3h;
    }

    public Double getDischarge_flow_m3h() {
        return discharge_flow_m3h;
    }

    public void setDischarge_flow_m3h(Double discharge_flow_m3h) {
        this.discharge_flow_m3h = discharge_flow_m3h;
    }

    public Double getInlet_pressure_bar() {
        return inlet_pressure_bar;
    }

    public void setInlet_pressure_bar(Double inlet_pressure_bar) {
        this.inlet_pressure_bar = inlet_pressure_bar;
    }

    public Double getDischarge_pressure_bar() {
        return discharge_pressure_bar;
    }

    public void setDischarge_pressure_bar(Double discharge_pressure_bar) {
        this.discharge_pressure_bar = discharge_pressure_bar;
    }
}
