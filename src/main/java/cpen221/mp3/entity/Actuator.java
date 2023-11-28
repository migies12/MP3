package cpen221.mp3.entity;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.ServerSocket;

import cpen221.mp3.client.Request;
import cpen221.mp3.event.Event;
import cpen221.mp3.server.SeverCommandToActuator;

public class Actuator implements Entity {
    private final int id;
    private int clientId;
    private final String type;
    private boolean state;
    private double eventGenerationFrequency = 0.2; // default value in Hz (1/s)

    // the following specifies the http endpoint that the actuator should send events to
    private String serverIP = null;
    private int serverPort = 0;
    
    // the following specifies the http endpoint that the actuator should be able to receive commands on from server
    private String host = null;
    private int port = 0;

    // the following specifies the socket that the actuator should be able to receive commands on from server
    private Socket serverSocket;

    public Actuator(int id, String type, boolean init_state) {
        this.id = id;
        this.clientId = -1;         // remains unregistered
        this.type = type;
        this.state = init_state;
         
        try{
            ServerSocket serverSocket = new ServerSocket(this.serverPort);
        }
        catch (Exception e){
            System.out.println("Error: e");
        }
    }

    public Actuator(int id, int clientId, String type, boolean init_state) {
        this.id = id;
        this.clientId = clientId;   // registered for the client
        this.type = type;
        this.state = init_state;
        try{
            ServerSocket serverSocket = new ServerSocket(this.serverPort);
        }
        catch (Exception e){
            System.out.println("Error: e");
        }
    }

    public Actuator(int id, String type, boolean init_state, String serverIP, int serverPort) {
        this.id = id;
        this.clientId = -1;         // remains unregistered
        this.type = type;
        this.state = init_state;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        try{
            ServerSocket serverSocket = new ServerSocket(this.serverPort);
        }
        catch (Exception e){
            System.out.println("Error: e");
        }
    }

    public Actuator(int id, int clientId, String type, boolean init_state, String serverIP, int serverPort) {
        this.id = id;
        this.clientId = clientId;   // registered for the client
        this.type = type;
        this.state = init_state;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        try{
            ServerSocket serverSocket = new ServerSocket(this.serverPort);
        }
        catch (Exception e){
            System.out.println("Error: e");
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
        return true;
    }

    public boolean getState() {
        return state;
    }

    public String getIP() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void updateState(boolean new_state) {
        this.state = new_state;
    }

    /**
     * Registers the actuator for the given client
     * 
     * @return true if the actuator is new (clientID is -1 already) and gets successfully registered or if it is already registered for clientId, else false
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
     * the actuator should send events to
     * 
     * @param serverIP the IP address of the endpoint
     * @param serverPort the port number of the endpoint
     */
    public void setEndpoint(String serverIP, int serverPort){
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    /**
     * Sets the frequency of event generation
     *
     * @param frequency the frequency of event generation in Hz (1/s)
     */

    //TODO: make sure this can only be called by Client.ClientID==Entity.ClientID
    public void setEventGenerationFrequency(double frequency){
        this.eventGenerationFrequency = frequency;
    }

    /**
     * Sends an event to the endpoint of following structure:
     * {
            "Actuator": {
                "getId": "<value of getId()>",
                "ClientId": "<value of getClientId()>",
                "EntityType": "<value of getType()>",
                "IP": "<value of getIP()>",
                "Port": "<value of getPort()>"
            }
        }
     */
    public void sendEvent(Event event) {
        String eventString = event.toString();

        
    }

    public void processServerMessage(Request command) {
        // implement this method
    }

    @Override
    public String toString() {
        return "Actuator{" +
                "getId=" + getId() +
                ",ClientId=" + getClientId() +
                ",EntityType=" + getType() +
                ",IP=" + getIP() +
                ",Port=" + getPort() +
                '}';
    }

    // you will most likely need additional helper methods for this class
}