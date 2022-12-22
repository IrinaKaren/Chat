package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JComboBox;
import javax.swing.JTextField;


public class TextFieldListener implements ActionListener{
    JTextField textField;
    JComboBox clientList;
    PrintWriter writer;
    public TextFieldListener(PrintWriter writer,JTextField textfield,JComboBox clientList){
        this.textField=textfield;
        this.clientList=clientList;
        this.writer=writer;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String targetUser = (String) clientList.getSelectedItem();
        if (targetUser.equals("All")) {
          writer.println(": " + textField.getText());
        } else {
          writer.println("/pm " + targetUser + " " + textField.getText());
        }
        textField.setText("");
    }
    
}
