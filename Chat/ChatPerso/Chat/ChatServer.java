package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import thread.ClientThread;

public class ChatServer {
    int port;
    List<ClientThread> clients;

    public ChatServer(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientThread client = new ClientThread(clientSocket, this);
                client.start();
                clients.add(client);
            }
        }catch(Exception e){}
    }

    public void broadcast(String message) {
        for (ClientThread client : clients) {
        client.sendMessage(message);
        }
    }

    public void sendMessageToClient(String message, ClientThread client) {
        client.sendMessage(message);
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
    }

    public List<ClientThread> getClients() {
        return clients;
    }
}
