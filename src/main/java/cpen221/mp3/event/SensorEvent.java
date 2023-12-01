package cpen221.mp3.event;

public class SensorEvent implements Event {
    private double TimeStamp;
    private int ClientId;
    private int EntityId;
    private String EntityType;
    private double Value;

    /**
     * Constructs a new SensorEvent with the given parameters.
     * @param TimeStamp 
     * @param ClientId
     * @param EntityId
     * @param EntityType
     * @param Value
     */

    public SensorEvent(double TimeStamp,
                        int ClientId,
                        int EntityId, 
                        String EntityType, 
                        double Value) {
        this.TimeStamp = TimeStamp;
        this.ClientId = ClientId;
        this.EntityId = EntityId;
        this.EntityType = EntityType;
        this.Value = Value;
    }

    /**
     * Returns the time stamp of this event.
     */
    public double getTimeStamp() {
        return TimeStamp;
    }
    /** 
     * Returns the ID of the client that generated this event.
     */
    public int getClientId() {
        return ClientId;
    }

    /**
     *  Returns the ID of the entity that generated this event.
     */
    public int getEntityId() {
        return EntityId;
    }

    /**
     * Returns the type of the entity that generated this event.
     */

    public String getEntityType() {
        return EntityType;
    }

    /**
     * Returns the value of this event.
     */
    public double getValueDouble() {
        return Value;
    }

    // Sensor events do not have a boolean value
    // no need to implement this method
    public boolean getValueBoolean() {
        return false;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
               "TimeStamp=" + getTimeStamp() +
               ",ClientId=" + getClientId() + 
               ",EntityId=" + getEntityId() +
               ",EntityType=" + getEntityType() + 
               ",Value=" + getValueDouble() + 
               '}';
    }


}
