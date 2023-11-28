package cpen221.mp3.event;

public class ActuatorEvent implements Event {

    private double TimeStamp;
    private int ClientId;
    private int EntityId;
    private String EntityType;
    private boolean Value;

    public ActuatorEvent(double TimeStamp, 
                        int ClientId,
                        int EntityId, 
                        String EntityType, 
                        boolean Value) {
        this.TimeStamp = TimeStamp;
        this.ClientId = ClientId;
        this.EntityId = EntityId;
        this.EntityType = EntityType;
        this.Value = Value;
    }

    public double getTimeStamp() {
        return TimeStamp;
    }

    public int getClientId() {
        return ClientId;
    }

    public int getEntityId() {
        return EntityId;
    }

    public String getEntityType() {
        return EntityType;
    }

    public boolean getValueBoolean() {
        return Value;
    }

    // Actuator events do not have a double value
    // no need to implement this method
    public double getValueDouble() {
        return -1;
    }

    @Override
    public String toString() {
        return "ActuatorEvent{" +
                "TimeStamp=" + getTimeStamp() +
                ",ClientId=" + getClientId() +
                ",EntityId=" + getEntityId() +
                ",EntityType=" + getEntityType() +
                ",Value=" + getValueBoolean() +
                '}';
    }
}
