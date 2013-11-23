import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Server extends JFrame {
	private JTextArea jta = new JTextArea();
	
	public static void main(String[] args) {
		new Server();
	}
	
	public  Server() {
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		setTitle("Server");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			InetAddress inetAddress = serverSocket.getInetAddress();
			jta.append("Server started at " + new Date() + '\n');
			
			Socket socket = serverSocket.accept();
			
			DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
			DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
			
			while(true) {
				
				jta.append("before input");
				double radius = inputFromClient.readDouble();
				double area = radius * radius * Math.PI;
				jta.append("after input");
				outputToClient.writeDouble(area);
				jta.append("Radius received from client: " + radius + '\n');
				jta.append("Area found: " + area + '\n');
				System.out.println("Client's host name is " +
						inetAddress.getHostName());
				System.out.println("Client's IP Address is " +
						inetAddress.getHostAddress());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
