import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class ServerBoard extends JFrame {
    private JTextArea messagesArea; 
    private TCPServer mServer;
 
    public ServerBoard() 
    {
        super("SpyServer");
        //se creeaza panel-ul
        JPanel panelFields = new JPanel();
        panelFields.setLayout(new BoxLayout(panelFields,BoxLayout.X_AXIS));
        messagesArea = new JTextArea();
        messagesArea.setColumns(30);
        messagesArea.setRows(40);
        messagesArea.setEditable(false);
        //conexiunea la server ( implementarea metodei abstracte )
        mServer = new TCPServer(new TCPServer.OnMessageReceived() 
        {
            public void messageReceived(String message) {
                messagesArea.append("\n "+message);
            }
        });
        mServer.start();
        
        //creeare spatiu de receive
        panelFields.add(messagesArea);
        getContentPane().add(panelFields);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setSize(300, 170);
        setVisible(true);
    }
}