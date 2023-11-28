package cpen221.mp3.handler;

import java.net.Socket;

class MessageHandlerThread implements Runnable {
    private Socket incomingSocket;

    public MessageHandlerThread(Socket incomingSocket) {
        this.incomingSocket = incomingSocket;
    }

    @Override
    public void run() {
        // handle the client request or entity event here
        // and deal with exceptions if needed
    }
}