package cpen221.mp3.entity;

import cpen221.mp3.event.ActuatorEvent;
import cpen221.mp3.event.Event;
import cpen221.mp3.event.SensorEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;

public class Sensor implements Entity {
    private final int id;
    private int clientId;
    private final String type;
    private String serverIP = null;
    private int serverPort = 0;
    private double eventGenerationFrequency = 0.2; // default value in Hz (1/s)

    // the following specifies the socket that the actuator should be able to receive commands on from server
    private Socket eventSocket;

    public Sensor(int id, String type) {
        this.id = id;
        this.clientId = -1;         // remains unregistered
        this.type = type;
    }

    public Sensor(int id, int clientId, String type) {
        this.id = id;
        this.clientId = clientId;   // registered for the client
        this.type = type;

        try{
            eventSocket = new Socket();
        }
        catch (Exception e){
            System.out.println("ERROR setting Entity Constructor Endpoint: "+e);
        }

    }

    public Sensor(int id, String type, String serverIP, int serverPort) {
        this.id = id;
        this.clientId = -1;   // remains unregistered
        this.type = type;
        this.serverIP = serverIP;
        this.serverPort = serverPort;

        try{
            eventSocket = new Socket(serverIP, serverPort);
        }
        catch (Exception e){
            System.out.println("ERROR setting Entity Constructor Endpoint: "+e);
        }
    }

    public Sensor(int id, int clientId, String type, String serverIP, int serverPort) {
        this.id = id;
        this.clientId = clientId;   // registered for the client
        this.type = type;
        this.serverIP = serverIP;
        this.serverPort = serverPort;

        try{
            eventSocket = new Socket(serverIP, serverPort);
        }
        catch (Exception e){
            System.out.println("ERROR setting Entity Constructor Endpoint: "+e);
        }
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public String getType() {
        return type;
    }

    public boolean isActuator() {
        return false;
    }

    /**
     * Registers the sensor for the given client
     *
     * @return true if the sensor is new (clientID is -1 already) and gets successfully registered or if it is already registered for clientId, else false
     */
    public boolean registerForClient(int clientId) {
        if (this.clientId == -1) {
            this.clientId = clientId;
            return true;
        } else if (this.clientId == clientId) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets or updates the http endpoint that 
     * the sensor should send events to
     *
     * @param serverIP the IP address of the endpoint
     * @param serverPort the port number of the endpoint
     */
    public void setEndpoint(String serverIP, int serverPort){
        try{
            this.serverIP = serverIP;
            this.serverPort = serverPort;

            eventSocket = new Socket(serverIP, serverPort);

        }catch (IOException e){
            System.out.println("ERROR setting Entity Constructor Endpoint: "+e);
        }

    }

    /**
     * Sets the frequency of event generation
     *
     * @param frequency the frequency of event generation in Hz (1/s)
     */
    public void setEventGenerationFrequency(double frequency){

        int tryCount = 0;

        this.eventGenerationFrequency = frequency;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        double commandTime = timestamp.getTime();

        while (timestamp.getTime() < commandTime + 1000/frequency && this.clientId != -1) {
            if (tryCount >= 5){
                try {
                    tryCount = 0;
                    Thread.sleep(5000);
                    break;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                commandTime = timestamp.getTime();
                try{
                    switch (this.type) {

                        case "TempSensor":
                            SensorEvent tempEvent = new SensorEvent(commandTime, this.clientId, this.id, this.type, generateTempSensorValue());
                            sendEvent(tempEvent);
                            break;
                        case "PressureSensor":
                            SensorEvent pressureEvent = new SensorEvent(commandTime, this.clientId, this.id, this.type, generatePressureSensorValue());
                            sendEvent(pressureEvent);
                            break;
                        case "CO2Sensor":
                            SensorEvent co2Event = new SensorEvent(commandTime, this.clientId, this.id, this.type, generateCO2SensorValue());
                            sendEvent(co2Event);
                            break;
                        case "Switch":
                            SensorEvent switchEvent = new SensorEvent(commandTime, this.clientId, this.id, this.type, generateSwitchValue());
                            sendEvent(switchEvent);
                            break;
                        default:
                            System.out.println("ERROR: Invalid Sensor Type");
                            break;
                    }
                }catch (Exception e) {
                    System.out.println("ERROR setting new Entity Endpoint: " + e);
                    tryCount += 1;
                }

            }



        }

    }



    public void sendEvent(Event event) {

        //DOES THIS WORK?????

        try {
            String eventString = event.toString();  //Get event string
            OutputStream clientOutput = eventSocket.getOutputStream(); //Get output stream from server
            clientOutput.write(eventString.getBytes());  //Write to the output stream
            eventSocket.close();  //Close stream

        } catch (IOException e) {
            System.out.println("ERROR setting new Entity Endpoint: ");
            e.printStackTrace();
        }
    }

    public static double generateTempSensorValue() {
        // Generate a random double number between 20 and 24 (Celsius)

        return (Math.random() * (24.0 - 20.0 + 1) + 20.0)/100.0; //rounds to nearest 100th

    }

    public static double generatePressureSensorValue() {
        // Generate a random double number between 1020 and 1024 (in millibars)
        // Return the generated value
        return (Math.random() * (1024.0 - 1020.0 + 1) + 1020.0)/100.0; //rounds to nearest 100th
    }

    public static double generateCO2SensorValue() {
        // Generate a random double (in ppm)
        // Return the generated value
        return Math.random()/100.0; //rounds to nearest 100th
    }

    public static double generateSwitchValue() {
        // Generate a random boolean (50-50% chance of true or false)
        // Return the generated value
        return Math.round(Math.random());
    }
}