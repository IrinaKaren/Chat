import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {
  private Socket socket;
  private ChatServer server;
  private PrintWriter writer;

  public ClientThread(Socket socket, ChatServer server) {
    this.socket = socket;
    this.server = server;
  }

  public void run() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer = new PrintWriter(socket.getOutputStream(), true);

      while (true) {
        String message = reader.readLine();
        if (message == null) {
          break;
        }
        server.broadcast(message);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      server.removeClient(this);
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
}
