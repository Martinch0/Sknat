package client;

import java.io.IOException;

import networking.Client2Server;

/**
 * Listens to the server and decodes the string it receives.
 * 
 * @author Samuel Hill
 * 
 */
public class ClientListener implements Runnable {
	private GameClient gc;

	public ClientListener(GameClient gc) {
		this.gc = gc;
	}

	@Override
	public void run() {
		String receivedString;
		while (true) {

			try {
				receivedString = gc.getClient().receive();

				if (receivedString != null) {

					gc.receiveFromServer(receivedString);

				}
			} catch (IOException e) {
				System.out
						.println("There was an error in connecting to the server.");
				break;
			}

		}
	}

}
