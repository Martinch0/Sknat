package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A class for creating a TCP/IP client, that sends and receives packets to and
 * from the server.
 * 
 * @author Bella Dunovska, Martin Mihov
 * 
 */
public class TCPClient extends TCPConnection {

	public TCPClient(String serverIP, int serverPort)
			throws UnknownHostException, IOException {
		clientSocket = new Socket(serverIP, serverPort);
		inputStream = new DataInputStream(clientSocket.getInputStream());
		outputStream = new DataOutputStream(clientSocket.getOutputStream());
	}

}
