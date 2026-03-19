package com.company.pump_data_service.model;

public class MotorData {
    private Double speed_rpm;
    private Double current_a;
    private Double active_power_kw;

    public Double getSpeed_rpm() {
        return speed_rpm;
    }

    public void setSpeed_rpm(Double speed_rpm) {
        this.speed_rpm = speed_rpm;
    }

    public Double getCurrent_a() {
        return current_a;
    }

    public void setCurrent_a(Double current_a) {
        this.current_a = current_a;
    }

    public Double getActive_power_kw() {
        return active_power_kw;
    }

    public void setActive_power_kw(Double active_power_kw) {
        this.active_power_kw = active_power_kw;
    }
}
