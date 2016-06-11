package junitTests;

import static org.junit.Assert.*;

import java.io.IOException;

import networking.TCPClient;
import networking.TCPServer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit Test Case for testing the networking part of the game.
 * 
 * @author TeamA5
 * 
 */
public class NetworkingTests {

	private static TCPServer server;
	private static TCPClient client;

	/**
	 * Method to set up the connection exactly once before the tests run.
	 * 
	 * @throws Exception
	 *             when there was a failure while creating the server and the
	 *             client or the connection between them.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		server = new TCPServer();
		client = new TCPClient("localhost", TCPServer.DEFAULT_PORT);
		server.acceptConnection(0);
	}

	/**
	 * Method to terminate the connection after the tests have been performed.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.closeSocket();
	}

	/**
	 * Method to verify the connection from client to server.
	 */
	@Test
	public void clientToServer() {

		try {
			String sendString = "HelloServer!";
			client.send(sendString);
			assertEquals("Test from client to server", sendString,
					server.receive());
		} catch (IOException e) {
			fail("Connection failed!");
		}
	}

	/**
	 * Method to verify the connection from server to client.
	 */
	@Test
	public void serverToClient() {

		try {
			String sendString = "HelloClient!";
			server.send(0,sendString);
			assertEquals("Test from server to client", sendString,
					client.receive());
		} catch (IOException e) {
			fail("Connection failed!");
		}
	}
}
