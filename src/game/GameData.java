package game;

import game.objects.FloorTile;
import game.objects.Key;
import game.objects.Missile;
import game.objects.Objective;
import game.objects.Obstacle;
import game.objects.Projectile;
import game.objects.Tank;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Contains all the data necessary to run the game.
 * 
 * @author Alexandre Portugal, Bella Dunvoska, Martin Mihov
 *
 */
public class GameData {

	// Projectile arrays
	// projectiles is array of projectiles that the server has, newProjectiles
	// is the new projectiles that the player created and need to be sent to the
	// server
	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> newProjectiles;

	// Array of tanks that represent players
	private Tank[] tanks;

	// The player team
	private int myTeam;

	// Timer in String format (minutes:seconds), for example 2:24
	private String timer = "m:ss";

	// Array of obstacles
	private Obstacle[] obs;

	// Array of obstacles
	private FloorTile[] floorT;

	// Objective
	private Objective objective;

	// Variable for the key object
	private Key key;

	// Counts the score of each team
	private int[] teamScores;

	// Temporary Chat Array
	private ArrayList<String> chat;

	// Map height and width
	private int mWidth = 900;
	private int mHeight = 900;
	private int timeRemaining;

	// Bases
	private int xTeam1 = 0;
	private int yTeam1 = 0;
	private int xTeam2 = 0;
	private int yTeam2 = 0;

	// Missile to draw
	public Missile theBlueMiss;
	public Missile theRedMiss;
	
	/**
	 * Empty constructor to be used by the GameServer to keep the game data.
	 */
	public GameData() {
		projectiles = new ArrayList<Projectile>();
		newProjectiles = new ArrayList<Projectile>();
		chat = new ArrayList<String>();

		teamScores = new int[2];
		
		theRedMiss = new Missile(0, 0, 0, 0, 0);
		theRedMiss.setActive(0);
		
		theBlueMiss = new Missile(0, 0, 0, 0, 0);
		theBlueMiss.setActive(0);
	}

	/**
	 * @return the array of projectiles that was sent by the server
	 */
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * @return all the tank objects that are currently playing
	 */
	public Tank[] getTanks() {
		return tanks;
	}

	/**
	 * Set the readiness of each tank(player).
	 * 
	 * @param playerID
	 */
	public void setReady(int playerID) {
		if (playerID >= 0 && playerID < tanks.length) {
			tanks[playerID].setReady(true);
		}
	}

	/**
	 * @param playerID
	 * @return the current ready state the tank corresponding to the playerID
	 */
	public boolean isReady(int playerID) {
		if (playerID >= 0 && playerID < tanks.length) {
			return tanks[playerID].isReady();
		} else {
			return false; // Return false if ID is not valid
		}
	}

	/**
	 * Method to update the parameters of tank with id.
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param bAngle
	 * @param tAngle
	 */
	public void updateTank(int id, int x, int y, double bAngle, double tAngle) {
		tanks[id].setX(x);
		tanks[id].setY(y);
		tanks[id].setBotAngle(bAngle);
		tanks[id].setTopAngle(tAngle);
	}

	/**
	 * Sets the tank array to be an array with different tanks
	 * 
	 * @param Tanks
	 */
	public void setTanks(Tank[] Tanks) {
		this.tanks = Tanks;
	}

	/**
	 * Calls all the object's draw methods
	 * 
	 * @param g2d
	 */
	public void updateDisplay(Graphics2D g2d, int myID) {

		// paints the objective.
		objective.changeObjectiveImg(getKeyHolder(), getTimeRemaining());
		objective.paintObjective(g2d);

		// Draws all the floortiles
		for (int i = 0; i < floorT.length; i++) {
			floorT[i].paintFloorTile(g2d);
			// System.out.println(obs[i].getX() + " " + obs[i].getY());
		}

		// Draws all the obstacles
		// System.out.println(obs.length);
		for (int i = 0; i < obs.length; i++) {
			obs[i].paintObstacle(g2d);
			// System.out.println(obs[i].getX() + " " + obs[i].getY());
		}

		// paints the objective.
		objective.paintObjective(g2d);

		ArrayList<Projectile> copy = new ArrayList<Projectile>();
		copy = (ArrayList<Projectile>) projectiles.clone();

		// Draws all the projectiles
		for (int i = 0; i < copy.size(); i++) {
			if (copy.get(i) != null)
				copy.get(i).paintProjectile(g2d);
		}

		// Draws all the tanks
		for (int i = 0; i < tanks.length; i++) {
			tanks[i].paintTank(g2d, myID, i, myTeam);

		}

		// Draws the key
		if (key != null) {
			key.paintKey(g2d, this);
		}

		if (theRedMiss.getActive() == 1) {
			theRedMiss.paintProjectile(g2d);
		}

		if (theBlueMiss.getActive() == 1) {
			theBlueMiss.paintProjectile(g2d);		
		}
	}

	/**
	 * Adds a new projectile to be drawn
	 * 
	 * @param x
	 * @param y
	 * @param xD
	 * @param yD
	 */
	public void addProjectile(int x, int y, int xD, int yD, int team) {
		Projectile p = new Projectile(x, y, xD, yD, team);
		p.setRealX(x);
		p.setRealY(y);
		projectiles.add(p);
	}

	/**
	 * Sets the projectiles that are to be drawn. Mostly called when a new array
	 * of projectiles is received by the server
	 * 
	 * @param projectiles
	 */
	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @param myTeam
	 *            the myTeam to set
	 */
	public void setMyTeam(int myTeam) {
		this.myTeam = myTeam;
	}

	/**
	 * @return The projectiles that were shot by the player that haven-t been
	 *         sent to the server yet
	 */
	public ArrayList<Projectile> getNewProjectiles() {
		return newProjectiles;
	}

	/**
	 * @param newProjectiles
	 */
	public void setNewProjectiles(ArrayList<Projectile> newProjectiles) {
		this.newProjectiles = newProjectiles;
	}

	public void updateKey(int x, int y, int visible) {
		key.setX(x);
		key.setY(y);
		key.setVisible(visible);
	}

	/**
	 * @return the obs
	 */
	public Obstacle[] getObstacles() {
		return obs;
	}

	/**
	 * @param obs
	 *            the obs to set
	 */
	public void setObstacles(Obstacle[] obs) {
		this.obs = obs;
	}

	public FloorTile[] getFloorT() {
		return floorT;
	}

	public void setFloorT(FloorTile[] floorT) {
		this.floorT = floorT;
	}

	/**
	 * Adds new projectile to array to be sent to the server
	 * 
	 * @param newProj
	 */
	public void addNewProj(Projectile newProj) {
		newProjectiles.add(newProj);
	}

	/**
	 * Clears the projectile array (was sent to server)
	 */
	public void clearNewProjArray() {
		newProjectiles = new ArrayList<Projectile>();
	}

	/**
	 * Sets a specific tanks health
	 * 
	 * @param tId
	 * @param tHealth
	 */
	public void setTankHealth(int tId, int tHealth) {
		tanks[tId].setHealth(tHealth);
	}

	/**
	 * Gets the objective.
	 * 
	 * @return
	 */
	public Objective getObjective() {
		return objective;
	}

	/**
	 * Sets the objective.
	 * 
	 * @param objective
	 */
	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	/**
	 * Gets the keyholder.
	 * 
	 * @return
	 */
	public int getKeyHolder() {
		return key.getKeyHolder();
	}

	/**
	 * Set the keyholder.
	 * 
	 * @param keyHolder
	 */
	public void setKeyHolder(int keyHolder) {
		key.setKeyHolder(keyHolder);
	}

	/**
	 * Gets the array list representing the chat messages.
	 * 
	 * @return
	 */
	public ArrayList<String> getChat() {
		return chat;
	}

	/**
	 * Set the array list representing the chat messages.
	 * 
	 * @param chat
	 */
	public void setChat(ArrayList<String> chat) {
		this.chat = chat;
	}

	/**
	 * Add a line to the array of chat messages.
	 * 
	 * @param a
	 */
	public void addChatLine(String a) {
		chat.add(a);
	}

	/**
	 * Gets the team's scores.
	 * 
	 * @return
	 */
	public int[] getTeamScores() {
		return teamScores;
	}

	/**
	 * Sets scores for corresponding team.
	 * 
	 * @param teamId
	 * @param score
	 */
	public void setTeamScore(int teamId, int score) {
		teamScores[teamId] = score;
	}

	/**
	 * Increments the scores of the team who has reached the objective.
	 * 
	 * @param teamId
	 */
	public void incrementTeamScores(int teamId, int scores) {
		teamScores[teamId] += scores;
		System.out.println(teamId + ":" + teamScores[teamId]);
	}

	/**
	 * @return the mWidth
	 */
	public int getmWidth() {
		return mWidth;
	}

	/**
	 * @param mWidth
	 *            the mWidth to set
	 */
	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	/**
	 * @return the mHeight
	 */
	public int getmHeight() {
		return mHeight;
	}

	/**
	 * @param mHeight
	 *            the mHeight to set
	 */
	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}

	/**
	 * updates given tank to have a new specified team name.
	 * 
	 * @param id
	 * @param teamID
	 */
	public void setTeamId(int id, int teamID) {
		tanks[id].setTeamId(teamID);
	}

	/**
	 * A methid to set the nickname for a tank, this is called by the client
	 * when receiving [NC]
	 * 
	 * @param id
	 * @param name
	 */
	public void setNickname(int id, String name) {
		tanks[id].setName(name);
	}

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public boolean isEqualTeamID(int id) {
		for (Tank x : getTanks()) {
			if (x.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * @return the xTeam1
	 */
	public int getxTeam1() {
		return xTeam1;
	}

	/**
	 * @param xTeam1
	 *            the xTeam1 to set
	 */
	public void setxTeam1(int xTeam1) {
		this.xTeam1 = xTeam1;
	}

	/**
	 * @return the yTeam1
	 */
	public int getyTeam1() {
		return yTeam1;
	}

	/**
	 * @param yTeam1
	 *            the yTeam1 to set
	 */
	public void setyTeam1(int yTeam1) {
		this.yTeam1 = yTeam1;
	}

	/**
	 * @return the xTeam2
	 */
	public int getxTeam2() {
		return xTeam2;
	}

	/**
	 * @param xTeam2
	 *            the xTeam2 to set
	 */
	public void setxTeam2(int xTeam2) {
		this.xTeam2 = xTeam2;
	}

	/**
	 * @return the yTeam2
	 */
	public int getyTeam2() {
		return yTeam2;
	}

	/**
	 * @param yTeam2
	 *            the yTeam2 to set
	 */
	public void setyTeam2(int yTeam2) {
		this.yTeam2 = yTeam2;
	}

}