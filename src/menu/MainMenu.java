package menu;

import game.GameData;
import game.GamePanel;
import game.objects.Tank;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import map.Maps;
import server.GameServer;
import client.GameClient;

/**
 * This class is used to create instances oaf the startMenuPanel,
 * multiplayerPanel, teamSelectPanel and the gamePanel. Using CardLayout this
 * class allows efficient transition between panels with the use of one JFrame.
 * 
 * @author Bella Dunvoska, Martin Mihov,Tendaishe Nyamapfeni, Samuel Hill
 * 
 */
public class MainMenu {
	// Class Fields
	private JFrame frame = new JFrame("Sknat");
	private JPanel panelContainer = new JPanel();

	private StartMenuPanel startMenuPanel;
	private MultiplayerPanel multiplayerPanel;
	private LobbyPanel lobbyPanel;
	private GameModePanel gameModePanel;
	private EndGamePanel endGamePanel;
	private GamePanel gamePanel;
	private GameData gameData;
	private GameServer server;
	private GameClient client;

	public static String GAME_TYPE = "";

	public static int playerID = 0; // Initialize player ID before server/client
	// creation

	private CardLayout cardLayout = new CardLayout();

	/**
	 * Main Method: Creates an instance of MainMenu
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		MainMenu mm = new MainMenu();

	}

	/**
	 * Constructor: Initializes all the JPanels and JFrame.
	 */
	public MainMenu() {
		// set layout for the container panel
		panelContainer.setLayout(cardLayout);

		// Panels for the container
		startMenuPanel = new StartMenuPanel(this);
		multiplayerPanel = new MultiplayerPanel(this);
		lobbyPanel = new LobbyPanel(this);
		gameModePanel = new GameModePanel(this);

		// add custom panels to the container panel
		panelContainer.add(startMenuPanel, "1");
		panelContainer.add(multiplayerPanel, "2");
		panelContainer.add(lobbyPanel, "3");
		panelContainer.add(gameModePanel, "4");

		// Show the first panel
		cardLayout.show(panelContainer, "1");

		// Frame setup
		frame.add(panelContainer);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setMinimumSize(new Dimension((int) (Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth() / 3 + 400), (int) (Toolkit
				.getDefaultToolkit().getScreenSize().getHeight() / 3 + 400)));

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Method: This method is used to start the server and a client. The client
	 * is automatically connected because this is the host client.
	 * 
	 * This method is called by the MultiplayerPanel.
	 */
	public void startHosting(int playerNum, String gameType) {
		// ///Server//////
		try {
			// Start server for 2 players
			setServer(new GameServer(playerNum, gameType));
			getServer().startServer();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Server could not be started.");
		}
		// ///Client////////
		try {
			// Connect client to server
			// Initial information received here....
			this.client = new GameClient("localhost", this);
			playerID = client.getID();

			// Setups the game panel and adds to the container
			setupInitialGamePanel(playerNum);

			// Set the team ID of the player's tank
			// obtains from the client, the allocated teamID and then adds it to
			// the local gamedata
			gamePanel.gameData.getTanks()[playerID].setTeamId(client
					.getTeamID());

			// frame.add(gamePanel, new BorderLayout());

			// Pass Game Panel to Game Client
			client.setGamePanel(gamePanel);
			// Now Client is in full communication
			client.listenForUpdates();
			client.sendGameState();

			// Update team tables
			// System.out.println("The current team id of the host is: "+gamePanel.gameData.getTanks()[playerID].getTeamId());
			refreshTableModel();

			updateReady(0); // set the host tank ready
			// Set up thread to listen to whether all players are ready
			lobbyPanel.checkReady();
			// teamSelectPanel.checkChat(); //Check instant Messages
			refreshTableModel();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to Host.");
		}
		System.out.println("Hosting ...");
	}

	/**
	 * Method to create an ai client.
	 * 
	 */
	public void createAI(int playerNum, int team) {

		try {
			GameClient aiClient = new GameClient("localhost", this);
			GamePanel aipanel = new GamePanel(aiClient.getID(), true, this);
			aipanel.owner = frame;
			aipanel.gameData = Maps.map1(playerNum);
			aiClient.setAi(true);
			aiClient.setGamePanel(aipanel);
			aiClient.listenForUpdates();
			aiClient.changeName(aiClient.getID(), "AI");
			aiClient.sendGameState();
			while (aiClient.getTeamID() == -1)
				;
			if (aiClient.getTeamID() != team) {
				aiClient.changeTeam(aiClient.getID());
			}

			aiClient.setReady(aiClient.getID());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method: This method is used to connect a client to an existing server
	 * using a String parameter which represents the Host IP address.
	 * 
	 * This method is called by the MultiplayerPanel.
	 * 
	 * @param hostIP
	 */
	public void startClient(String hostIP) {
		// //Client////////
		try {
			// Connect client to server
			client = new GameClient(hostIP, this);
			playerID = client.getID();

			lobbyPanel.renameBtnStartGame();

			// Setups the game panel and adds to the container
			setupInitialGamePanel(GameClient.NUMBER_OF_PLAYERS);

			// Set the team ID of the player's tank
			gamePanel.gameData.getTanks()[playerID].setTeamId(client
					.getTeamID());
			refreshTableModel();
			// System.out.println("The teams have been updated");
			// client.se
			panelContainer.add(gamePanel, "5");

			// Pass Game Panel to Game Client
			client.setGamePanel(gamePanel);
			client.listenForUpdates();
			client.sendGameState();

			refreshTableModel();
			// If connection is successful the current panel will changed
			cardLayout.show(panelContainer, "3");
			// teamSelectPanel.checkChat(); // Check instant Messages

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to Host.");
		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "Unable to connect to Host.");
		}
		System.out.println("Joining ...");

	}

	/**
	 * Method: This method is used to enable movement threads in the GamePanel.
	 * 
	 * This method is called by LobbyPanel.
	 */
	public void startGamePanel() {
		// Start movement in GamePanel
		gamePanel.startGamePan();
	}

	/**
	 * Method: This method is used to initialize the GamePanel with Tanks,
	 * Obstacles and the Key.
	 * 
	 * This method is called by 'this' class.
	 */
	public void setupInitialGamePanel(int playerNum) {
		// Initialize the GameData
		gameData = map.Maps.map1(playerNum);

		// Initialize GamePanel and add the GameData
		gamePanel = new GamePanel(playerID, this);
		gamePanel.owner = frame;
		gamePanel.gameData = gameData;
		panelContainer.add(gamePanel, "5");
	}

	/**
	 * Method: This method is used to switch panels on the display. The string
	 * parameter represents the ID set when the panel was added to the
	 * containerPanel.
	 * 
	 * @param panelNumber
	 */
	public void switchPanel(String panelNumber) {
		// Display selected panel
		cardLayout.show(panelContainer, panelNumber);
	}

	/**
	 * Method - setupButtons (this method removes the default format of buttons
	 * and replaces it with custom format, which uses images)
	 * 
	 * @param button
	 * @param hover
	 * @param pressed
	 */
	public void setupButtons(JButton button, BufferedImage hover,
			BufferedImage pressed) {
		// Create Image Icons
		ImageIcon hoverIcon = new ImageIcon(hover);
		ImageIcon pressedIcon = new ImageIcon(pressed);

		// Format Jbutton
		button.setSelectedIcon(hoverIcon);
		button.setPressedIcon(pressedIcon);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(hoverIcon);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
	}

	public int getPlayerID() {
		return playerID;
	}

	public boolean isPlayersReady() {

		for (Tank t : gameData.getTanks()) {
			// t.setReady(true);
			if (t.isReady() == false) {
				// System.out.println("YAY FALSE" + t.getId());

				return false;
			}
		}
		return true;
	}

	public void setReady(int playerID) {
		// Updates the local game data to show that the player is
		// now ready!

		gameData.setReady(playerID);
		refreshTableModel();

	}

	// Tells the client to send the updates ready status to the server, who will
	// in turn inform all of the clients. The server client will update its
	// local gamedata
	public void updateReady(int playerID) {
		// This call updates the other clients game data
		client.setReady(playerID);
		// This call updates the local game data
		this.setReady(playerID);
		refreshTableModel();
	}

	public void sendStartState() {
		client.sendStartState();
	}

	// Sent instant message
	public void sendChatMessage(String message) {
		client.sendIM(message);
	}

	public void startGame() {
		// changes the current panel to the game panel so that the game is
		// actually displayed!
		cardLayout.show(panelContainer, "5");
		startGamePanel();
	}

	// Get all messages
	public ArrayList<String> getChatMessages() {
		if (gameData != null) {
			return gameData.getChat();
		} else {
			// This empty arraylist is there to stop null pointer exception///
			ArrayList<String> arr = new ArrayList<String>();
			return arr;
		}
	}

	/**
	 * @return the teamSelectPanel
	 */
	public LobbyPanel getLobbyPanel() {
		return lobbyPanel;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public GameData getGameData() {
		return this.gameData;
	}

	public GameServer getServer() {
		return server;
	}

	public void setServer(GameServer server) {
		this.server = server;
	}

	/**
	 * A public method to refresh the tables, should be called after any team/
	 * nickname changes
	 */
	public void refreshTableModel() {
		// System.out.println("refreshing tables...");
		TeamTableModel team1 = lobbyPanel.getTableModel(0);
		TeamTableModel team2 = lobbyPanel.getTableModel(1);
		updateTableModels(team1, team2);
	}

	/**
	 * Method to update the tanks contained within the team tables
	 * 
	 * @param team1
	 * @param team2
	 */
	private void updateTableModels(TeamTableModel team1, TeamTableModel team2) {
		// System.out.println("Updating tables");
		Tank[] tanks = gameData.getTanks();
		if (tanks == null) {
			// System.out.println("There are no tanks for which data can be ");
			return;
		}
		ArrayList<Tank> team1Tanks = new ArrayList<Tank>();
		ArrayList<Tank> team2Tanks = new ArrayList<Tank>();

		for (int i = 0; i < tanks.length; i++) {
			// System.out.println("The team id of player: "+tanks[i].getId()+" is "+tanks[i].getTeamId());
			switch (tanks[i].getTeamId()) {

			case 0:

				team1Tanks.add(tanks[i]);
				// System.out.println("Adding tank " + i + " to team 0");
				break;
			case 1:
				team2Tanks.add(tanks[i]);

				// System.out.println("Adding tank " + i + " to team 1");
				break;
			default:
				// System.out.println("Defaulting...");
				break;
			}

		}
		try {
			// System.out.println(" setting tank list");
			team1.setTankList(team1Tanks);
			team2.setTankList(team2Tanks);
		} catch (NullPointerException e) {
			System.out.println("Table cannot be updated");
		}
	}

	public void updateTeam() {
		// TODO Auto-generated method stub
		client.changeTeam(playerID);
	}

	public void changeName(String nickname) {
		// TODO Auto-generated method stub
		client.changeName(playerID, nickname);
	}

	public int[] getTeamScores() {
		int[] arr = new int[2];
		arr[0] = gameData.getTeamScores()[1]; // Team 1
		arr[1] = gameData.getTeamScores()[0]; // Team 2

		return arr;
	}

	public void showEndGamePanel(int victoryState) {
		// System.out.println(victoryState);
		endGamePanel = new EndGamePanel(this, victoryState);
		panelContainer.add(endGamePanel, "6");
		switchPanel("6");

	}

	public void setGameType(String gameType) {
		// TODO Auto-generated method stub
		GAME_TYPE = gameType;
	}

	public String getGameType() {
		return GAME_TYPE;
	}

}
