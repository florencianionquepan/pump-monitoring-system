package com.company.pump_data_service.model;

public class PumpPayload {
    private String timestamp;
    private MotorData motor;
    private PumpData pump;
    private ConditionData condition;
    private String status;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public MotorData getMotor() {
        return motor;
    }

    public void setMotor(MotorData motor) {
        this.motor = motor;
    }

    public PumpData getPump() {
        return pump;
    }

    public void setPump(PumpData pump) {
        this.pump = pump;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ConditionData getCondition() {
        return condition;
    }

    public void setCondition(ConditionData condition) {
        this.condition = condition;
    }
}
