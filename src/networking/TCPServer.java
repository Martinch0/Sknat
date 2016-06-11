package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class for creating a TCP/IP server, that sends and receives packets between
 * the players. Creates a sending and receiving threads for the new players.
 * 
 * @author Bella Dunovska, Martin Mihov.
 * 
 */
public class TCPServer {

	
	private final ArrayList<ConcurrentLinkedQueue<String>> sendQueue;
	private final ConcurrentLinkedQueue<String> receiveQueue;

	public static final int DEFAULT_PORT = 32874; // A random default port

	private final ServerSocket serverSocket;

	public static boolean isGameRunning = false; // A flag to annotated whether
												// the game has been
												// initialised.

	/**
	 * Initialises the server socket on the default port.
	 * 
	 * @throws IOException
	 *             when the port is unreachable.
	 */
	public TCPServer() throws IOException {
		serverSocket = new ServerSocket(DEFAULT_PORT);
		sendQueue = new ArrayList<ConcurrentLinkedQueue<String>>();
		receiveQueue = new ConcurrentLinkedQueue<String>();
	}

	/**
	 * Accepts a connection with a client and starts sending and receiving
	 * threads.
	 * 
	 * @throws IOException
	 *             when an I/O error occurs while waiting for a connection.
	 */
	public void acceptConnection(int id) throws IOException {
		Socket clientSocket = serverSocket.accept();

		// If a connection is successful then add a sending queue to the array
		// of queues.
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		sendQueue.add(id, queue);

		// Starts a thread for sending to the current accepted client.
		SendToPlayer playerSendThread = new SendToPlayer(id, clientSocket);
		Thread threadSend = new Thread(playerSendThread);
		threadSend.start();

		// Starts a thread for receiving from the current accepted client.
		ReceiveFromPlayer playerReceiveThread = new ReceiveFromPlayer(
				clientSocket);
		Thread threadReceive = new Thread(playerReceiveThread);
		threadReceive.start();

	}

	/**
	 * Method to send a string through the server. Makes use of a thread-safe
	 * queue.
	 * 
	 * @param id
	 *            the id of the client to whom the message will be send.
	 * @param s
	 * 
	 *            the string to be sent.
	 */
	public void send(int id, String s) {
		try{
			sendQueue.get(id).add(s);
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Warning: Player "+id+" has not connected yet.");
		}
	}

	/**
	 * Method to receive a string.Makes use of a thread-safe queue.
	 * 
	 * @return
	 */
	public String receive() {
		return receiveQueue.poll();
	}

	public void stopSocket(){
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Server stopped!");
		}
	}
	
	public boolean isEverythingSent() {
		boolean emptySentQueues = true;
		for(ConcurrentLinkedQueue<String> queue : sendQueue) {
			if(!queue.isEmpty()) emptySentQueues = false;
		}
		return emptySentQueues;
	}
	
	/**
	 * Class to represent the thread responsible for sending to a client from
	 * the server.
	 * 
	 * @author Bella Dunovska, Martin Mihov.
	 * 
	 */
	private class SendToPlayer extends TCPConnection implements Runnable {
		private int id;

		/**
		 * Constructor to initialise the outputStream corresponding to the
		 * socket.
		 * 
		 * @param id
		 *            the id of the current client
		 * @param client
		 *            the socket that has been opened to the client.
		 * @throws IOException
		 *             when an error occurs upon initialising the stream.
		 */
		public SendToPlayer(int id, Socket client) throws IOException {
			clientSocket = client;
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
			this.id = id;
		}

		/**
		 * Method to send the appropriate information to the corresponding
		 * player.
		 */
		@Override
		public void run() {
			while (true) {
				if(serverSocket.isClosed()) {
					try {
						clientSocket.close();
					} catch (IOException e) {
						System.out.println("Client socket already closed.");
					}
					break;
				}
				try {
					String s;
					// Checks the corresponding queue by id for a string to be
					// sent.
					while ((s = sendQueue.get(id).poll()) != null) {
						send(s); 
					}
					Thread.sleep(20);
				} catch (IOException e) {
					System.out.println("This data cannot be sent.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Class to represent the thread responsible for receiving from a client to
	 * the server.
	 * 
	 * @author Bella Dunovska, Martin Mihov.
	 * 
	 */
	private class ReceiveFromPlayer extends TCPConnection implements Runnable {

		/**
		 * Constructor to initialise the inputStream corresponding to the
		 * socket.
		 * 
		 * @param client
		 *            the socket that has been opened to the client.
		 * @throws IOException
		 *             when an error occurs upon initialising the stream.
		 */
		public ReceiveFromPlayer(Socket client) throws IOException {
			clientSocket = client;
			inputStream = new DataInputStream(clientSocket.getInputStream());
		}

		/**
		 * Method to receive the information from the corresponding player.
		 */
		@Override
		public void run() {
			while (true) {
				if(serverSocket.isClosed()) {
					try {
						clientSocket.close();
					} catch (IOException e) {
						System.out.println("Client socket already closed.");
					}
					break;
				}
				try {
					String received = receive();
					receiveQueue.add(received);
				} catch (IOException e) {
					System.out
							.println("Data cannot be received by this player.");
				}
			}

		}

	}
}
