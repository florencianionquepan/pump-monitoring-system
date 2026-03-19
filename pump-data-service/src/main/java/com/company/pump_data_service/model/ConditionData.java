package com.company.pump_data_service.model;

public class ConditionData {
    private Double motor_vibration_mm_s;
    private Double pump_vibration_mm_s;
    private Double thrust_bearing_temp_c;

    public Double getMotor_vibration_mm_s() {
        return motor_vibration_mm_s;
    }

    public void setMotor_vibration_mm_s(Double motor_vibration_mm_s) {
        this.motor_vibration_mm_s = motor_vibration_mm_s;
    }

    public Double getPump_vibration_mm_s() {
        return pump_vibration_mm_s;
    }

    public void setPump_vibration_mm_s(Double pump_vibration_mm_s) {
        this.pump_vibration_mm_s = pump_vibration_mm_s;
    }

    public Double getThrust_bearing_temp_c() {
        return thrust_bearing_temp_c;
    }

    public void setThrust_bearing_temp_c(Double thrust_bearing_temp_c) {
        this.thrust_bearing_temp_c = thrust_bearing_temp_c;
    }
}
