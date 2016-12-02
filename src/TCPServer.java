import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 
public class TCPServer extends Thread 
{
    public static final int SERVERPORT = 4444;
    private boolean running = false;
    private PrintWriter mOut;
    private OnMessageReceived messageListener;
 
    public static void main(String[] args) 
    {
        //deschide fereastra de mesaje
        ServerBoard frame = new ServerBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
 
    }
 
    public TCPServer(OnMessageReceived messageListener) 		//constructor
    {
        this.messageListener = messageListener;
    }
 
    @Override
    public void run() 
    {
        super.run();
        running = true;
 
        try 
        {
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
            Socket client = serverSocket.accept();	//asteapta o conexiune la acest socket si o accepta
            System.out.println("S: Receiving...");
 
            try 
            {
                //buferele de in si out
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
 
                while (running) 		//se asteapta mesaje...
                {
                    String message = in.readLine();
 
                    if (message != null && messageListener != null) 
                    {
                        messageListener.messageReceived(message);	//se apeleaza metoda implementata in ServerBoard
                    }
                }
 
            } 
            catch (Exception e) 
            {
                System.out.println("S: Error");
                e.printStackTrace();
            } 
            finally 
            {
                client.close();
                System.out.println("S: Done.");
            }
 
        } 
        catch (Exception e) 
        {
            System.out.println("S: Error");
            e.printStackTrace();
        }
 
    }
 
    public interface OnMessageReceived 
    {
        public void messageReceived(String message);
    }
 
}