import javax.swing.JOptionPane;

import server.ChatServer;
import  client.ChatClient;
public class ChatProgram {
    public static void main(String[] args) {
      int port = 8888; // default port
      if (args.length > 0) {
        try {
          port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
          System.out.println("Invalid port number. Using default port.");
        }
      }
      
      ChatServer server = new ChatServer(port);
      server.start();
  
      String serverName = "localhost";
      String userName = JOptionPane.showInputDialog(null, "Enter your name:", "User name", JOptionPane.PLAIN_MESSAGE);
      ChatClient client = new ChatClient(serverName, port, userName);
      client.start();
    }
  }
  