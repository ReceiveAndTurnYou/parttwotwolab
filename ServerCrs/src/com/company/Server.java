package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket = null;

    public Server(int port) {
        this.port = port;
    }


    public void start() {
        try {
            serverSocket = new ServerSocket(port);

            System.out.print("Server started on: " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() + "\n");

            acceptClients();
        }
        catch (IOException ex) {
            System.out.print("Server exception: " + ex.getMessage() + "\n");
        }
    }

    private void acceptClients() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();

                new Session(socket).start();
            }
            catch (IOException ex) {
                System.out.print("Server exception: " + ex.getMessage() + "\n");
            }
        }
    }
}
