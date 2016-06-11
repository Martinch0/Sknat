package client;

import game.GamePanel;
import game.objects.Key;
import game.objects.Projectile;
import game.objects.Tank;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import menu.MainMenu;
import networking.Client2Server;
import networking.TCPClient;
import networking.TCPServer;
import server.GameServer;
import ai.ArtificialTank;

/**
 * Class to represent a player who communicates through a server.
 * 
 * @author Samuel Hill, Bella Dunvoska, Martin Mihov
 */
public class GameClient {
	// To handle all sending and receiving of strings.
	private TCPClient tcpClient;
	// Contains the local copy of the game data which will synchronise 60 times
	// per second with the server's copy.
	private GamePanel gamePanel;
	// Parser to translate the data into strings and the other way around.
	private Client2Server parser;
	// Identifier of player
	private MainMenu mm;
	// ...
	private ArrayList<Projectile> projectiles;
	public int clientID;
	private int teamID = -1;
	public static int NUMBER_OF_PLAYERS = 0;
	private boolean isAi = false;
	public static String GAME_TYPE = "";

	/**
	 * The constructor initiates the TCPClient which in turn will connect to the
	 * server. The user must provide the IP of the server they wish to connect
	 * t2.
	 * 
	 * @param serverIP
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public GameClient(String serverIP, MainMenu mm)
			throws UnknownHostException, IOException {
		projectiles = new ArrayList<Projectile>();
		this.mm = mm;
		tcpClient = new TCPClient(serverIP, TCPServer.DEFAULT_PORT);

		parser = new Client2Server(this);
		System.out.println("connected to server");
		initialiseIDandTeam();

		System.out.println("Received ID");
	}

	/**
	 * Initialises the player with an id and number of players.
	 */
	private void initialiseIDandTeam() {
		try {
			parser.decode(tcpClient.receive());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to send new Game State. It codes the new parameters into a string
	 * and sends them to the server.
	 */
	public void sendGameState() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						// The '!' are required to distinguish separate command
						// types!
						String s = parser.code("[T][P]", clientID,
								gamePanel.gameData.getTanks()[clientID].getX(),
								gamePanel.gameData.getTanks()[clientID].getY(),
								gamePanel.gameData.getTanks()[clientID]
										.getBotAngle(), gamePanel.gameData
										.getTanks()[clientID].getTopAngle(),
								gamePanel.gameData.getNewProjectiles());
						gamePanel.gameData.clearNewProjArray();

						tcpClient.send(s);

					} catch (IOException e) {
						System.out.println("Server has been disconnected.");
						break;
					}
					try {

						Thread.sleep(15);
					} catch (InterruptedException e) {
						System.out
								.println("Unexpected interruption in game client!");
					}
				}
			}
		});
		thread.start();
	}

	/**
	 * Gets the used tcpClient.
	 * 
	 * @return
	 */
	public TCPClient getClient() {
		return tcpClient;
	}

	/**
	 * Method to start a new listening thread for receiving data from the
	 * server.
	 */
	public void listenForUpdates() {
		Thread listen = new Thread(new ClientListener(this) {

		});
		listen.start();
	}

	/**
	 * Method to decode the received data.
	 * 
	 * @param encodedData
	 */
	public void receiveFromServer(String encodedData) {
		projectiles.clear();
		parser.decode(encodedData);
		gamePanel.gameData.setProjectiles(projectiles);

	}

	/**
	 * Method to update the position of other players' tanks.
	 * 
	 * @param id
	 *            the tank's id
	 * @param x
	 * @param y
	 * @param bAngle
	 * @param tAngle
	 */
	public void updateTank(int id, int x, int y, double bAngle, double tAngle) {
		if (id != clientID) {
			gamePanel.updateTank(id, x, y, bAngle, tAngle);
		}
	}

	/**
	 * Adds a projectile to the game data once it has been shoot.
	 * 
	 * @param xP
	 * @param yP
	 * @param xD
	 * @param yD
	 */
	public void addProjectile(int xP, int yP, int xD, int yD, int team) {
		projectiles.add(new Projectile(xP, yP, xD, yD, team));
	}

	/**
	 * Setting the gamePanel
	 * 
	 * @param gamepanel
	 */
	public void setGamePanel(GamePanel gamepanel) {
		gamePanel = gamepanel;
	}

	/**
	 * Sets the position of the key.
	 * 
	 * @param xK
	 * @param yK
	 * @param vis
	 */
	public void setKeyPosition(int xK, int yK, int vis) {
		gamePanel.gameData.setKey(new Key(xK, yK, vis));
	}

	/**
	 * Sets the tank's health points.
	 * 
	 * @param tId
	 * @param tHealth
	 */
	public void setTankHealth(int tId, int tHealth) {
		gamePanel.gameData.setTankHealth(tId, tHealth);
	}

	/**
	 * Sets the client's id.
	 * 
	 * @param clientID
	 */
	public void setID(int clientID) {
		this.clientID = clientID;

	}

	/**
	 * Returns the client's id.
	 * 
	 * @return
	 */
	public int getID() {
		return this.clientID;
	}

	/**
	 * Adds a received message to the chat window of the current player.
	 * 
	 * @param playerID
	 * @param message
	 */
	public void receiveMessage(int playerID, String message) {
		// Here we will tell the panel to add a message or something like that!
		// :P
		if (getID() != playerID && isAi) {
			sendIM("I am a computer. I cannot talk. Just start the game!");
		} else if (!isAi) {
			String displayName = gamePanel.gameData.getTanks()[playerID]
					.getName();
			if (displayName != null) {

				mm.getLobbyPanel().getListModel()
						.addElement(displayName + ": " + message);
			} else {

				mm.getLobbyPanel().getListModel()
						.addElement(playerID + ": " + message);
			}
		}
	}

	/**
	 * Starts the game
	 */
	public void startGame() {

		if (!isAi) {
			mm.startGame();
		} else {
			ArtificialTank aitank = new ArtificialTank(this, gamePanel);
			aitank.startAi();
		}
	}

	/**
	 * This method receives a mouse click from a main menu, it then sends to the
	 * server the id of the player who is now ready!
	 * 
	 * @param playerID
	 */
	public void setReady(int playerID) {
		try {
			tcpClient.send(parser.code("[R]", playerID));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the ready state upon connecting to the server.
	 * 
	 * @param readyID
	 *            the id of the current connecting player.
	 */
	public void updateReady(int readyID) {
		mm.setReady(readyID);
	}

	/**
	 * Sends the start state to begin the game and to display the game field.
	 */
	public void sendStartState() {
		try {
			tcpClient.send(parser.code("[SG]"));
		} catch (IOException e) {
			System.out.println("Unable to start game!");
		}
	}

	/**
	 * Sends instant message.
	 * 
	 * @param message
	 */
	public void sendIM(String message) {
		try {
			tcpClient.send(parser.code("[IM]", this.getID(), message));
		} catch (IOException e) {
			System.out.println("Unable to send IM!");
		}
	}

	/**
	 * Updates the team.
	 * 
	 * @param id
	 * @param teamID
	 */
	public void updateTeam(int id, int teamID) {
		if (clientID == id) {

			this.setTeamID(teamID);
		}
		gamePanel.gameData.setTeamId(id, teamID);
		mm.refreshTableModel();
	}

	/**
	 * Gets the team id.
	 * 
	 * @return
	 */
	public int getTeamID() {
		return teamID;
	}

	/**
	 * Set the team id.
	 * 
	 * @param teamID
	 */
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	/**
	 * Updating the team scores.
	 * 
	 * @param teamId
	 * @param score
	 */
	public void updateScores(int teamId, int score) {
		if (teamId == 0) {
			// Sends a missile towards the enemy base if objective was captured.
			int scoreDiff = score - gamePanel.gameData.getTeamScores()[teamID];
			if (scoreDiff == GameServer.SCORES_FOR_OBJECTIVE) {
				System.out.println("Send miss");
				new Thread() {
					public void run() {
						if (mm.getGameData().theRedMiss.getActive() != 1) {
							mm.getGameData().theRedMiss.restart();
						}
					}

				}.start();
			}
		} else {
			// Sends a missile towards the enemy base if objective was captured.
			int scoreDiff = score - gamePanel.gameData.getTeamScores()[teamID];
			if (scoreDiff == GameServer.SCORES_FOR_OBJECTIVE) {
				System.out.println("Send miss");
				new Thread() {
					public void run() {
						if (mm.getGameData().theBlueMiss.getActive() != 1) {
							mm.getGameData().theBlueMiss.restart();
						}
					}

				}.start();
			}

		}
		gamePanel.gameData.setTeamScore(teamId, score);
	}

	public void setKeyHolder(int idK) {
		gamePanel.gameData.setKeyHolder(idK);
		Tank[] tankArray = gamePanel.gameData.getTanks();

		// Makes tanks top yellow. Disabled for now.
		for (int i = 0; i < tankArray.length; i++) {
			if (i == idK) {
				// tankArray[i].makeTopYellow();
			} else {
				// tankArray[i].makeTopNotYellow();
			}
		}
	}

	public void respawnTank(int id3) {
		gamePanel.gameData.getTanks()[id3].resetTank();
	}

	public void setTime(String timer) {
		gamePanel.gameData.setTimer(timer);
	}

	public boolean isAi() {
		return isAi;
	}

	public void setAi(boolean isAi) {
		this.isAi = isAi;
	}

	public void changeTeam(int playerID) {
		String s = parser.code("[CT]", playerID);
		try {
			tcpClient.send(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		GameClient.NUMBER_OF_PLAYERS = numberOfPlayers;
		System.out.println("Number of players is: " + NUMBER_OF_PLAYERS);
	}

	public int getNumberOfPlayers() {

		return GameClient.NUMBER_OF_PLAYERS;

	}

	public void changeName(int playerID, String nickname) {
		try {
			tcpClient.send(parser.code("[NC]", playerID, nickname));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void endGame(int winner) {
		System.out.println("Winner is " + winner);
		if (!isAi)
			gamePanel.decideWinner(winner);
	}

	public void receiveNameChange(int pID, String nickname) {
		if (clientID == pID) {
			System.out.println("My new nickname is " + nickname);

		}
		gamePanel.gameData.setNickname(pID, nickname);
		System.out.println("Player " + pID + " now named " + nickname);
		mm.refreshTableModel();
	}

	public void setInitialPosition(int idpos, int xip, int yip) {
		Tank t = gamePanel.gameData.getTanks()[idpos];
		t.setX(xip);
		t.setY(yip);
		t.setOriginalX(xip);
		t.setOriginalY(yip);
	}

	public void setTimeRemaining(int timeRemaining) {
		gamePanel.gameData.setTimeRemaining(timeRemaining);
	}

	public void requestLobbyHistory() {
		parser.code("[RLH]", clientID);
	}

	/**
	 * Method that receives the type of the game.
	 * 
	 * @param gameType
	 */
	public void receiveGameType(String gameType) {
		// TODO Auto-generated method stub
		GAME_TYPE = gameType;
		mm.setGameType(gameType);
		System.out.println("Type of Game is " + gameType);

	}
}
