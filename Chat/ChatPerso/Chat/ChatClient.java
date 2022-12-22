package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import listener.TextFieldListener;
public class ChatClient {
    String serverName;
    int port;
    String userName;
    JFrame frame;
    JTextField textField;
    JTextArea messageArea;
    
    JComboBox<String> clientList;
    PrintWriter writer;
    Socket socket;

    public ChatClient(String serverName, int port, String userName) {
        this.serverName = serverName;
        this.port = port;
        this.userName = userName;
        this.frame = new JFrame("Chat Client - " + userName);
        this.textField = new JTextField(40);
        this.messageArea = new JTextArea(8, 40);
        this.clientList = new JComboBox<>();
        this.clientList.addItem("All");
    
        // Manamboatra Frame
        textField.setEditable(true);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(clientList);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void start() {
        
        // Connection server
        try {
            socket = new Socket(serverName, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(userName);
            Thread readerThread = new Thread(new IncomingReader(socket, this));
            readerThread.start();
            // Listener
            textField.addActionListener(new TextFieldListener(writer, textField, clientList));
        
        } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Error connecting to server", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
        }
    }
  
    public void addClientToList(String userName) {
        clientList.addItem(userName);
    }
  
    public void removeClientFromList(String userName) {
      clientList.removeItem(userName);
    }      
    public JTextArea getMessageArea() {
        return messageArea;
    }
    public void setMessageArea(JTextArea messageArea) {
        this.messageArea = messageArea;
    }
}