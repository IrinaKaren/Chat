package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import client.ChatClient;

public class IncomingReader implements Runnable {
    Socket socket;
    ChatClient client;
  
    public IncomingReader(Socket socket, ChatClient client) {
      this.socket = socket;
      this.client = client;
    }
  
    public void run() {
      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message;
        while ((message = reader.readLine()) != null) {
          if (message.startsWith("[") && message.endsWith("has joined the chat]")) {
            String userName = message.substring(1, message.indexOf(" "));
            client.addClientToList(userName);
          } else if (message.startsWith("[") && message.endsWith("has left the chat]")) {
            String userName = message.substring(1, message.indexOf(" "));
            client.removeClientFromList(userName);
          } else {
            client.getMessageArea().append(message + "\n");
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
  