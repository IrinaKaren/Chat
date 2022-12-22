import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {
  private String serverName;
  private int port;
  private String userName;
  private JFrame frame;
  private JTextField textField;
  private JTextArea messageArea;
  private PrintWriter writer;
  private Socket socket;

  public ChatClient(String serverName, int port, String userName) {
    this.serverName = serverName;
    this.port = port;
    this.userName = userName;
    this.frame = new JFrame("Chat Client - " + userName);
    this.textField = new JTextField(40);
    this.messageArea = new JTextArea(8, 40);
  }

  public void start() {
    // Set up the GUI
    textField.setEditable(true);
    messageArea.setEditable(false);
    frame.getContentPane().add(textField, BorderLayout.SOUTH);
    frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    // Add a listener for the send button
    textField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        writer.println(userName + ": " + textField.getText());
        textField.setText("");
      }
    });

    // Connect to the server and start receiving messages
    try {
      socket = new Socket(serverName, port);
      writer = new PrintWriter(socket.getOutputStream(), true);
      Thread readerThread = new Thread(new IncomingReader(socket));
      readerThread.start();
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(frame, "Error connecting to server", "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  private class IncomingReader implements Runnable {
    private Socket socket;

    public IncomingReader(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message;
        while ((message = reader.readLine()) != null) {
          messageArea.append(message + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    String serverName = JOptionPane.showInputDialog(
        null,
        "Enter the server name:",
        "Server Name",
        JOptionPane.QUESTION_MESSAGE);
    String portString = JOptionPane.showInputDialog(
        null,
        "Enter the port number:",
        "Port Number",
        JOptionPane.QUESTION_MESSAGE);
    int port = Integer.parseInt(portString);
    String userName = JOptionPane.showInputDialog(
        null,
        "Enter your user name:",
        "User Name",
        JOptionPane.QUESTION_MESSAGE);
  
    ChatClient client = new ChatClient(serverName, port, userName);
    client.start();
  }
  

}
