import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
  private int port;
  private List<ClientThread> clients;

  public ChatServer(int port) {
    this.port = port;
    this.clients = new ArrayList<>();
  }

  public void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Server started on port " + port);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        ClientThread client = new ClientThread(clientSocket, this);
        client.start();
        clients.add(client);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void broadcast(String message) {
    for (ClientThread client : clients) {
      client.sendMessage(message);
    }
  }

  public void removeClient(ClientThread client) {
    clients.remove(client);
  }

    public static void main(String[] args) {
        int port = 2017; // choose a port number
        ChatServer server = new ChatServer(port);
        server.start();   
    }
}
