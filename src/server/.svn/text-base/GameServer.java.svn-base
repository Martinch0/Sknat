package server;

import game.GameData;
import game.objects.Key;
import game.objects.Objective;
import game.objects.Obstacle;
import game.objects.Projectile;
import game.objects.Tank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import map.Maps;
import networking.Server2Client;
import networking.TCPServer;
import ai.Point;

/**
 * Class responsible for establishing the connection with the clients, for
 * providing the communication between the clients, updating the game state,
 * based on the current state and making sure every client has the newest game
 * state.
 * 
 * @author Bella Dunovska, Martin Mihov, Alexandre Portugal, Samuel Hill
 */
public class GameServer {

	private TCPServer server;
	private GameData gameData;
	private int idCounter = 0;
	private final Server2Client parser;
	private final int NUMBER_OF_PLAYERS;
	public final String GAME_TYPE;
	private long gameStartTime = 0;
	private final long GAME_TIME_LIMIT = 300000; // 5 minutes 300000
	private final long TIME_FOR_CAPTURE = 3000; // 5 seconds
	private final int TIME_FOR_RESPAWN = 5000; // 5 seconds
	public final static int SCORES_FOR_OBJECTIVE = 3;
	private final int SCORES_FOR_KILL = 1;
	private boolean gameEnd = false;
	private Thread connectPlayers;

	/**
	 * Constructor to initialise the GameServer and a corresponding parser for
	 * it.
	 * 
	 * @param num
	 *            the number of players
	 * @param gameType
	 * @throws IOException
	 *             when unable to create a server
	 */
	public GameServer(int num, String gameType) throws IOException {
		server = new TCPServer();
		setGameData(new GameData());

		initialiseMap(num);

		NUMBER_OF_PLAYERS = num;
		GAME_TYPE = gameType;

		parser = new Server2Client(this);
	}

	/**
	 * Initialises the tank character for the number of players.
	 * 
	 * @param numberOfPlayers
	 */
	private void initialiseMap(int numberOfPlayers) {
		gameData = Maps.map1(numberOfPlayers);
	}

	/**
	 * Method to start the communication between the server and the players.
	 */
	public void startServer() {
		connectPlayers = new Thread(new NetworkingThread());
		connectPlayers.start();
	}

	/**
	 * Method to stop the game.
	 */
	public void stopServer() {
		while (!server.isEverythingSent())
			;
		gameEnd = true;
		server.stopSocket();
	}

	/**
	 * Method to be used when updating the tank data.
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param bAngle
	 * @param tAngle
	 */
	public void updateTank(int id, int x, int y, double bAngle, double tAngle) {
		getGameData().updateTank(id, x, y, bAngle, tAngle);
	}

	/**
	 * Method to be used for adding a projectile.
	 * 
	 * @param x
	 * @param y
	 * @param xD
	 * @param yD
	 */
	public void addProjectile(int x, int y, int xD, int yD, int team) {
		getGameData().addProjectile(x, y, xD, yD, team);
	}

	/**
	 * Method to get the game data corresponding to the server.
	 * 
	 * @return
	 */
	public GameData getGameData() {
		return gameData;
	}

	/**
	 * Method to set the game data for the server.
	 * 
	 * @param gameData
	 */
	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}

	/**
	 * Class to represent the networking thread responsible for connecting the
	 * players and updating the game states.
	 * 
	 * @author TeamA5
	 * 
	 */
	public class NetworkingThread implements Runnable {

		private long objectiveCaptured = 0; // The time when the objective has

		// been entered.

		/**
		 * Method to accept a connection from multiple players.
		 * 
		 * A unique Id is assigned to each player.
		 */
		public void acceptPlayers() {
			Thread accept = new Thread(new Runnable() {

				@Override
				public void run() {
					while (idCounter < NUMBER_OF_PLAYERS) {
						try {
							// Accept a client connection and assign unique
							// value
							// for each new player.
							server.acceptConnection(idCounter);
							int teamNumber = assignTeam(idCounter);
							Tank[] tanks = gameData.getTanks();
							tanks[idCounter].setTeamId(teamNumber);
							String s = parser.code("[I]", idCounter);
							String teams = parser.code("[TI]", idCounter,
									teamNumber);
							String num = parser.code("[NP]", NUMBER_OF_PLAYERS);
							String gType = parser.code("[GT]", GAME_TYPE);
							// sends important pre game information to all
							// players as and when they connect

							// To the current player we will send their ID and
							// their number of players
							server.send(idCounter, s + num + gType);
							for (int i = 0; i < idCounter; i++) {

								System.out.println(idCounter
										+ " I am working "
										+ parser.code("[TI]", i,
												tanks[i].getTeamId()));
								// To the new player we send all the old players
								// teamID
								server.send(
										idCounter,
										parser.code("[TI]", i,
												tanks[i].getTeamId()));
								// To the new players, we should inform them of
								// who are ready
								if (tanks[i].isReady()) {

									server.send(idCounter,
											parser.code("[R]", i));
									System.out
											.println("Sending Ready status to new player "
													+ idCounter
													+ " for player " + i);
								}
								// To the new players we should inform them of
								// what everyones nicknames now are
								String currentName = tanks[i].getName();
								if (currentName != null) {

									server.send(idCounter,
											parser.code("[NC]", i, currentName));
								}
							}
							// To old players, we send the new player's team ID
							// s
							for (int i = 0; i <= idCounter; i++) {
								server.send(i, teams);
							}

							// if the connection is successful increment the
							// counter
							// for the next player.
							idCounter++;

						} catch (IOException e) {
							System.out
									.println("Socket closed. Unable to add a player.");
							break;
						}
					}
				}

				/**
				 * Method to assign initial teams according to the number of
				 * players.
				 * 
				 * @param idCounter
				 *            id counter represents number of connected players
				 * @return teamID returns which team should be joined for
				 *         connecting player
				 */
				private Integer assignTeam(int idCounter) {

					int[] teamIds = new int[2];

					Tank[] tanks = gameData.getTanks();

					for (int i = 0; i < idCounter; i++) {
						if (tanks[i].getTeamId() == 0) {
							teamIds[0]++;
						} else {
							teamIds[1]++;
						}
					}
					if (teamIds[0] > teamIds[1]) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			accept.start();
		}

		/**
		 * Method to apply the corresponding updates.
		 */
		public void doUpdates() {

			while (!gameEnd) {

				String s = server.receive();
				while (s != null) {
					// The parser will decode the received string in order to
					// get the actual values for updating.
					parser.decode(s);

					s = server.receive();
					// System.out.println("Receive: "+s);
				}

				if (TCPServer.isGameRunning) {
					// Method to count the game time
					checkTimeLimit();
					// Methods that calculate and amend the game state.
					moveProjectile();
					checkForHits();
					checkForKey();
					// moveKey();
					checkForObjective();

					// The new state is being passed to all players.
					sendNewState();
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					System.out.println("Game Server Thread interrupted!");
				}
			}
		}

		/**
		 * Method to set a time of 3 minutes in which the game is supposed to be
		 * played. After the time has elapsed compare the scores of each time.
		 * Chooses a winner based on the highest score.
		 */
		private void checkTimeLimit() {

			if (gameStartTime != 0
					&& System.currentTimeMillis() - gameStartTime >= GAME_TIME_LIMIT) {
				Tank[] tanks = gameData.getTanks();
				int[] teamScores = gameData.getTeamScores();
				// the winner which corresponds to a teamID.
				int winner = 0;
				// flag to indicate a draw.
				int draw = 0;
				for (int i = 1; i < teamScores.length; i++) {
					// if the 0 team has less score then team 1 wins.
					if (teamScores[winner] < teamScores[i]) {
						winner = i;
						draw = 0;
					} else if (teamScores[winner] == teamScores[i]) {
						draw = 1;
					}
				}
				String s = "";
				// if the result is the same send -1.
				if (draw == 1) {
					s = parser.code("[END]", -1);

				} else {
					// send the winner
					s = parser.code("[END]", winner);
				}
				for (int i = 0; i < tanks.length; i++) {
					server.send(i, s);
				}
				// after the time has passed the game stops.
				stopServer();
			}
		}

		/**
		 * Method to check whether a tank has reached the objective holding the
		 * key.
		 */
		private void checkForObjective() {
			// We are concerned only with the tank who is holding the key.
			if (gameData.getKeyHolder() != -1) {
				// The main objective.
				Objective objective = gameData.getObjective();
				// All the tanks
				Tank[] tanks = getGameData().getTanks();
				// Just the keyholder.
				Tank keyHolder = tanks[gameData.getKeyHolder()];

				// The parameters of the keyholder.
				int xT = keyHolder.getX();
				int yT = keyHolder.getY();
				int xT1 = xT + keyHolder.getWidth();
				int yT1 = yT + keyHolder.getHeight();

				// The parameters of the objective.
				int xO = objective.getX();
				int yO = objective.getY();
				int xO1 = xO + objective.getWidth();
				int yO1 = yO + objective.getHeight();

				// If the parameters interfere then add point and reset the key
				// position.
				if (((xT > xO && xT < xO1) || (xT1 > xO && xT1 < xO1))
						&& ((yT > yO && yT < yO1) || (yT1 > yO && yT1 < yO1))) {
					if (objectiveCaptured != 0
							&& System.currentTimeMillis() - objectiveCaptured > TIME_FOR_CAPTURE) {
						// Objective has been captured for more than
						// TIME_FOR_CAPTURE
						incrementScoreAndResetKey(keyHolder, tanks);
					} else if (objectiveCaptured == 0) {
						// The keyHolder has entered the objective
						objectiveCaptured = System.currentTimeMillis();
					}
					// Send time remaining for capturing the objective.
					long timeRemaining = (TIME_FOR_CAPTURE - (System
							.currentTimeMillis() - objectiveCaptured)) / 1000 + 1;
					for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
						server.send(i, parser.code("[O]", (int) timeRemaining));
					}
				} else {
					// The keyHolder is outside of the objective
					objectiveCaptured = 0;
				}
			}
		}

		/**
		 * Increments the score of the corresponding team and respawns the key
		 * at a random position.
		 * 
		 * @param keyHolder
		 *            The tanks that is currently holding the key.
		 * @param tanks
		 *            The array of tanks.
		 */
		private void incrementScoreAndResetKey(Tank keyHolder, Tank[] tanks) {
			gameData.incrementTeamScores(keyHolder.getTeamId(),
					SCORES_FOR_OBJECTIVE);
			resetKey();
			String s = parser.code("[S]", keyHolder.getTeamId(),
					gameData.getTeamScores()[keyHolder.getTeamId()]);
			s += parser.code("[K]", -1);
			s += parser.code("[KP]", gameData.getKey());
			// System.out.println(s);
			for (int i = 0; i < tanks.length; i++) {
				server.send(i, s);
			}
			objectiveCaptured = 0;
		}

		/**
		 * Method to return the key on a random position after a team has
		 * received a point. Makes sure that the key does not appear on top of
		 * an obstacle. Also, makes sure that the key does not appear on a tank.
		 */
		public void resetKey() {
			// Flag to represent a safe state to leave the key(indicate that
			// there is nothing on this position)
			boolean flagKey = true;

			Random random = new Random();

			// All possible obstacles.
			Obstacle[] obstacles = gameData.getObstacles();

			int xK, yK;

			// while a valid position for the key has been found
			while (true) {
				flagKey = true;

				// Generate new random coordinates for the key.
				xK = random.nextInt(900);
				yK = random.nextInt(600);
				int xK1 = xK + gameData.getKey().getKeySize();
				int yK1 = yK + gameData.getKey().getKeySize();

				// Get the parameters of all obstacles.
				for (int i = 0; i < obstacles.length; i++) {
					int x = obstacles[i].getX();
					int y = obstacles[i].getY();
					int x2 = x + obstacles[i].getWidth();
					int y2 = y + obstacles[i].getHeight();

					// If the newly generated coordinates interfere with an
					// obstacle then go back
					// and generate new random position.
					if (((xK > x && xK < x2) || (xK1 > x && xK1 < x2))
							&& ((yK > y && yK < y2) || (yK1 > y && yK1 < y2))) {
						flagKey = false;

					}
				}

				// Get the parameters of all tanks.
				Tank[] tanks = gameData.getTanks();
				for (int i = 0; i < tanks.length; i++) {
					int xT = tanks[i].getX();
					int yT = tanks[i].getY();
					int xT1 = xT + tanks[i].getWidth();
					int yT1 = yT + tanks[i].getHeight();

					// If the newly generated coordinates interfere with a tank
					// then go back
					// and generate new random position.
					if (((xK > xT && xK < xT1) || (xK1 > xT && xK1 < xT1))
							&& ((yK > yT && yK < yT1) || (yK1 > yT && yK1 < yT1))) {
						flagKey = false;

					}
				}
				// If there is no interference place the key.
				if (flagKey) {
					// Add the new key to the game data and reset the keyholder.
					gameData.setKey(new Key(xK, yK, 1));
					gameData.setKeyHolder(-1);
					String s = parser.code("[KP]", gameData.getKey());
					for (int i = 0; i < tanks.length; i++) {
						server.send(i, s);
					}
					break;

				}
			}

		}

		/**
		 * Method to decrement the health points of a tank and kill it
		 * accordingly.
		 * 
		 * @param id
		 */
		public void hitTank(int id) {
			final Tank[] tanks = getGameData().getTanks();
			final Tank tank = tanks[id];
			int health = tank.getHealth();
			health--;
			if (health == 0) {

				// increment score
				int killed = tank.getTeamId();
				gameData.incrementTeamScores(killed ^ 1, SCORES_FOR_KILL);
				String s = parser.code("[S]", killed ^ 1,
						gameData.getTeamScores()[killed ^ 1]);

				// reset the key if the dead tank is the keyholder
				if (tank.getId() == gameData.getKeyHolder()) {
					resetKey();
				}

				tank.setHealth(0);
				String healths = s + parser.code("[H]", id, 0);
				for (int i = 0; i < tanks.length; i++) {
					server.send(i, healths);
				}

				Thread killingThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(TIME_FOR_RESPAWN);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						/*
						 * If a tank has been killed (the health point reaches
						 * 0) send an event and respawn it. If the tank that has
						 * been killed has the key respawn the key at a random
						 * position.
						 */
						String s = parser.code("[RES]", tank.getId());

						tank.resetTank();

						for (int i = 0; i < tanks.length; i++) {
							server.send(i, s);
						}
					}
				});
				killingThread.start();
				// Reset key, send keyHolder, reset tank

			} else if (health > 0) {

				/*
				 * If a tank is shot but there are still health points available
				 * decrement them by 1 per hit.
				 */
				tank.setHealth(health);
				String s = parser.code("[H]", id, tank.getHealth());
				for (int i = 0; i < tanks.length; i++) {
					server.send(i, s);
				}
			}
		}

		/**
		 * Method to check whether a tank has been hit by a projectile. For
		 * every tank, compare its position with all the projectiles. If a tank
		 * is hit decrease the health points and delete the projectile.
		 */
		public void checkForHits() {
			// All existing projectiles.
			ArrayList<Projectile> projectile = getGameData().getProjectiles();
			// The ones to be deleted.
			ArrayList<Projectile> projectilesToBeDeleted = new ArrayList<Projectile>();
			// All tanks.
			Tank[] tanks = getGameData().getTanks();

			// For each tank and projectile compare their coordinates
			for (int i = 0; i < tanks.length; i++) {
				for (Projectile p : projectile) {
					int xT = tanks[i].getX();
					int yT = tanks[i].getY();
					int xTs = xT + tanks[i].getTankSize();
					int yTs = yT + tanks[i].getTankSize();

					int xP = p.getX();
					int yP = p.getY();
					int xPs = xP + p.getProjectSize();
					int yPs = yP + p.getProjectSize();
					// If they interfere note the hit and delete the projectiles
					// that performed the
					// hit.

					if ((((xP > xT && xP < xTs) || (xPs > xT && xPs < xTs)) && tanks[i]
							.getTeamId() != p.getTeam())
							&& (((yP > yT && yP < yTs) || (yPs > yT && yPs < yTs)) && tanks[i]
									.getTeamId() != p.getTeam())) {
						hitTank(i);
						// System.out.println("hit");
						projectilesToBeDeleted.add(p);
					}

				}

				projectile.removeAll(projectilesToBeDeleted);
			}

		}

		/**
		 * Method to check whether a tank currently holds a key, if so it
		 * assigns the keyholder.
		 * 
		 */
		public void checkForKey() {
			if (gameData.getKeyHolder() == -1) {
				// All tanks.
				Tank[] tanks = getGameData().getTanks();
				// The key.
				Key key = getGameData().getKey();

				// For all tanks compare their coordinates with the key's ones.
				for (int i = 0; i < tanks.length; i++) {
					int xT = tanks[i].getX();
					int yT = tanks[i].getY();
					int xTs = xT + tanks[i].getTankSize();
					int yTs = yT + tanks[i].getTankSize();

					int xK = key.getX();
					int yK = key.getY();
					int xKs = xK + key.getKeySize();
					int yKs = yK + key.getKeySize();

					// If they interfere set the keyholder to be the according
					// tank.
					if (((xK > xT && xK < xTs) || (xKs > xT && xKs < xTs))
							&& ((yK > yT && yK < yTs) || (yKs > yT && yKs < yTs))) {
						gameData.setKeyHolder(i);

						// Send an event notifying every player that the key has
						// been collected.
						String s = parser.code("[K]", i);
						for (int j = 0; j < tanks.length; j++) {
							server.send(j, s);
						}
						break;
					}

				}
			}
		}

		/**
		 * Method to compute the movement of projectiles and to delete them if
		 * they are out of range.
		 */
		public void moveProjectile() {
			// All projectiles.
			ArrayList<Projectile> projectiles = getGameData().getProjectiles();
			// The ones that need to be deleted.
			ArrayList<Projectile> projectilesToBeDeleted = new ArrayList<Projectile>();
			// For each projectile get its parameters.
			for (Projectile p : projectiles) {
				double xI = p.getXIncrement();
				double yI = p.getYIncrement();
				double realX = p.getRealX() + xI;
				double realY = p.getRealY() + yI;
				p.setRealX(realX);
				p.setRealY(realY);
				p.setX((int) realX);
				p.setY((int) realY);

				// Computing the distance which a projectile has moved and
				// comparing it to the range.
				double distance = Math.sqrt(xI * xI + yI * yI);
				p.incrementDistance(distance);
				if (p.getDistance() > p.getRange()) {
					projectilesToBeDeleted.add(p);

				} else {
					// For all obstacles on the map check their parameters
					// against the projectiles.
					Obstacle[] obstacles = gameData.getObstacles();
					for (int i = 0; i < obstacles.length; i++) {
						int x = obstacles[i].getX();
						int y = obstacles[i].getY();
						int x2 = x + obstacles[i].getWidth();
						int y2 = y + obstacles[i].getHeight();

						int xP = p.getX();
						int yP = p.getY();
						int xPs = xP + p.getProjectSize();
						int yPs = yP + p.getProjectSize();

						// If they interfere delete the projectile.
						if (((xP > x && xP < x2) || (xPs > x && xPs < x2))
								&& ((yP > y && yP < y2) || (yPs > y && yPs < y2))) {
							projectilesToBeDeleted.add(p);
							break;
						}
					}
				}
			}
			projectiles.removeAll(projectilesToBeDeleted);
			getGameData().setProjectiles(projectiles);
		}

		/**
		 * Method to send the updated states.
		 */
		private void sendNewState() {
			long timeElapsed = System.currentTimeMillis() - gameStartTime;
			long timeRemaining = GAME_TIME_LIMIT - timeElapsed;

			String timeString = timeRemaining / 60000 + ":";
			if ((timeRemaining % 60000) / 1000 < 10) {
				timeString += "0" + (timeRemaining % 60000) / 1000;
			} else {
				timeString += (timeRemaining % 60000) / 1000;
			}
			String s = parser.code("[T][P][TIMER]", getGameData().getTanks(),
					getGameData().getProjectiles(), timeString);
			// Iterates through all players to send them the state.
			for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
				server.send(i, s);
			}
		}

		/**
		 * Method to start the game server.
		 */
		@Override
		public void run() {
			acceptPlayers();
			doUpdates();

		}

	}

	/**
	 * Method to notify the ready state to the client.
	 * 
	 * @param id
	 */
	public void sendReadyState(int id) {
		gameData.getTanks()[id].setReady(true);
		String s = parser.code("[R]", id);
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			if (i != id) {
				server.send(i, s);
			}
		}
	}

	/**
	 * Method to send the start state.
	 */
	public void sendStartState() {
		TCPServer.isGameRunning = true;
		gameStartTime = System.currentTimeMillis();
		String s = parser.code("[SG]");
		String pos = initialiseTankPositions();
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			server.send(i, pos + s);

		}
	}

	/**
	 * Method to get the tanks initial position.
	 * 
	 * @return the initial position of tanks.
	 */
	public String initialiseTankPositions() {
		Tank[] tanks = gameData.getTanks();
		String posString = "";
		int[] team = { -1, -1 };
		for (int i = 0; i < tanks.length; i++) {
			team[tanks[i].getTeamId()]++;
			Point pos = Maps.getPosition(team[tanks[i].getTeamId()],
					tanks[i].getTeamId());
			tanks[i].setX(pos.getX());
			tanks[i].setY(pos.getY());
			tanks[i].setOriginalX(pos.getX());
			tanks[i].setOriginalY(pos.getY());

			posString += parser.code("[INITP]", i, tanks[i].getX(),
					tanks[i].getY());
		}
		return posString;

	}

	/**
	 * Method to send messages.
	 * 
	 * @param id
	 */
	public void sendIM(int id, String message) {
		String s = parser.code("[IM]", id, message);
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			server.send(i, s);
		}
	}

	/**
	 * Method to send the new nickname.
	 * 
	 * @param id
	 *            the player which nickname is to be set.
	 * @param newName
	 *            the desired name.
	 */
	public void sendNC(int id, String newName) {
		gameData.getTanks()[id].setName(newName);
		String s = parser.code("[NC]", id, newName);
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			server.send(i, s);
		}
	}

	/**
	 * Method for changing the team. The teams are differentiated by id. So we
	 * only need to change the team id.
	 * 
	 * @param pID
	 *            the team id to be changed
	 */
	public void changeTeam(int pID) {
		Tank[] tanks = gameData.getTanks();

		int oldTeamID = tanks[pID].getTeamId();
		int newTeamID = oldTeamID ^ 1;
		tanks[pID].setTeamId(newTeamID);
		String s = parser.code("[TI]", pID, newTeamID);
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			server.send(i, s);

		}
	}

	/**
	 * Gets the scores give for objective's fullfilment.
	 * 
	 * @return the SCORES_FOR_OBJECTIVE
	 */
	public int getSCORES_FOR_OBJECTIVE() {
		return SCORES_FOR_OBJECTIVE;
	}

	/**
	 * Getting the type of the game.
	 * 
	 * @return
	 */
	public String getGameType() {
		return GAME_TYPE;
	}

}
