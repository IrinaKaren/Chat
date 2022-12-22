package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import server.ChatServer;
public class ClientThread extends Thread {
    Socket socket;
    ChatServer server;
    PrintWriter writer;
    String userName;

    public ClientThread(Socket socket, ChatServer server) {
      this.socket = socket;
      this.server = server;
    }

    public void run() {
      try {
        //ahafahana mandefa message
        writer = new PrintWriter(socket.getOutputStream(), true);
        //mamaky donnee
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userName = reader.readLine();
        
        server.broadcast("[" + userName + " has joined the chat]");
        
        while (true) {
          String message = reader.readLine();
          if (message == null) {
            break;
          }
          else {
            server.broadcast(userName + ": " + message);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        server.removeClient(this);
        server.broadcast("[" + userName + " has left the chat]");
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    
    public void sendMessage(String message) {
      writer.println(message);
    }
    
    public String getUserName() {
      return userName;
    }
}  