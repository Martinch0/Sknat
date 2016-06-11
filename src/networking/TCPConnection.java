package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * An abstract class for creating a TCP/IP connection.
 * 
 * @author Bella Dunovska, Martin Mihov
 * 
 */
public abstract class TCPConnection {

	
	protected Socket clientSocket;
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;

	
	/**
	 * Sends a string over the socket.
	 * 
	 * @param s
	 *            the string to be sent.
	 * @throws IOException
	 *             when sending has failed.
	 */
	public void send(String s) throws IOException {
		outputStream.writeUTF(s);
	}

	/**
	 * Receives a string.
	 * 
	 * @return the received string.
	 * @throws IOException
	 *             when an I/O error occurs while waiting for a connection.
	 */
	public String receive() throws IOException {
		return inputStream.readUTF();
	}

	/**
	 * Closes the socket.
	 * 
	 * @param socket
	 *            the corresponding socket.
	 * @throws IOException
	 *             when an I/O error occurs while waiting for a connection.
	 */
	public void closeSocket() {
		try {
			clientSocket.close();
		} catch (IOException e) {

		}
	}

}
