package networking;

import game.objects.Entity;
import game.objects.Projectile;

import java.util.ArrayList;

import client.GameClient;

/**
 * Parser class for client to server communication
 * 
 * @author Bella Dunovska, Martin Mihov, Alexandre Portugal, Samuel Hill
 * 
 */
public class Client2Server {
	private GameClient gc;

	/**
	 * Codes the variables into a string for transfer NOTE: The overloading of
	 * the functions allows the method signature to eradicate nulls to be
	 * submitted for every field. In the event that the signatures clash (e.g.
	 * [I] vs [R], or there are composite messages(e.g. [T][P]) then the type
	 * string can be examined to determine how and what to code.
	 * 
	 * @param tankx
	 * @param tanky
	 * @param tankdirection
	 * @param newProjectiles
	 * 
	 * @return A coded string
	 * 
	 */
	public Client2Server(GameClient gc) {
		this.gc = gc;
	}

	/**
	 * Command types must be encompassed by [] This allows me to ensure that
	 * overlapping strings in command context do not cause any issues. Code a
	 * Tank
	 * 
	 * @param type
	 * @param id
	 * @param tankx
	 * @param tanky
	 * @param tankTopAngle
	 * @param tankBotAngle
	 * @return
	 */
	public String code(String type, int id, int tankx, int tanky,
			double tankTopAngle, double tankBotAngle) {
		String message = "";
		String tankStr = "";
		// if sending tank position
		if (type.contains("[T]")) {
			tankStr = "T#" + id + "#" + tankx + "#" + tanky + "#"
					+ tankTopAngle + "#" + tankBotAngle + "!!";
			message += tankStr;
		}
		return message;

	}

	/**
	 * Code sending a change team request
	 * 
	 * @param type
	 * @param playerID
	 * @param requestedTeamNumber
	 * @return
	 */
	public String code(String type, int playerID, int requestedTeamNumber) {
		String message = "";
		if (type.contains("[CT]")) {
			message += "CT#" + playerID + "#" + requestedTeamNumber + "!!";
		}
		return message;
	}

	/**
	 * Code a text based chat message to be sent.
	 * 
	 * @param type
	 * @param playerID
	 * @param text
	 * @return
	 */
	public String code(String type, int playerID, String text) {
		String message = "";
		if (type.contains("[IM]")) {
			message += "IM#" + playerID + "#" + text;
		} else if (type.contains("[NC]")) {
			// where text refers to a new nickname
			message += "NC#" + playerID + "#" + text;
		}
		return message;
	}

	/**
	 * Code a start state.
	 * 
	 * @param type
	 * @return
	 */
	public String code(String type) {
		if (type.contains("[SG]")) {
			return "SG#!!";
		} else {
			return "";
		}
	}

	/**
	 * Code a ready signal to be sent to the server, who will in turn dish it
	 * out to all the clients who will update their menus!
	 * 
	 * @param type
	 * @param playerID
	 * @return
	 */
	public String code(String type, int playerID) {
		String message = "";
		if (type.contains("[R]")) {
			message = "R#" + playerID + "!!";
		} else if (type.contains("[CT]")) {
			message = "CT#" + playerID + "!!";
		} else if (type.contains("[CT]")) {
			message += "CT#" + playerID + "!!";
		} else if (type.contains("[RLH]")) {
			message += "RLH#" + playerID + "!!";
		}
		return message;
	}

	/**
	 * Codes a composite message of local tank data and new projectiles.
	 * 
	 * @param type
	 * @param id
	 * @param tankx
	 * @param tanky
	 * @param tankTopAngle
	 * @param tankBotAngle
	 * @param newProjectiles
	 * @return
	 */
	public String code(String type, int id, int tankx, int tanky,
			double tankTopAngle, double tankBotAngle,
			ArrayList<Projectile> newProjectiles) {
		String message = "";
		String tankStr = "";
		// if sending tank postion
		if (type.contains("[T]")) {
			tankStr = "T#" + id + "#" + tankx + "#" + tanky + "#"
					+ tankTopAngle + "#" + tankBotAngle + "!!";
			message += tankStr;
		}

		// command types must be encompassed by [] This allows me to ensure that
		// overlapping strings in command context do not cause any issues.

		// if sending new projectiles+
		if (type.contains("[P]") && newProjectiles != null) {
			String projStr = "";
			for (int i = 0; i < newProjectiles.size(); i++) {
				// a temporary projectile
				Projectile tempProj = newProjectiles.get(i);
				String temp = "P#" + tempProj.getX() + "#" + tempProj.getY()
						+ "#" + tempProj.getxDir() + "#" + tempProj.getyDir()
						+ "#" + tempProj.getTeam() + "!!";
				projStr += temp;
			}
			message += projStr;

		}
		return message;
	}

	/**
	 * Decodes a string into variables and updates the game data
	 * correspondingly. Expects a string of type
	 * "identifier#int#int...!!identifier#...."
	 * 
	 * @param coded
	 *            the string to be decoded
	 * 
	 */
	public DataObject decode(String coded) {

		String[] checkString = coded.split("#");
		if (checkString[0].compareTo("IM") == 0) {

			int pID = Integer.parseInt(checkString[1]);
			String message = "";
			// Index of first occurrence of hash
			int firstHashIndex = coded.indexOf('#');
			// Index of second occurrence of hash
			int secondHashIndex = coded.indexOf('#', firstHashIndex + 1);
			message += coded.substring(secondHashIndex + 1);
			gc.receiveMessage(pID, message);

		} else if (checkString[0].compareTo("NC") == 0) {

			int pID = Integer.parseInt(checkString[1]);
			String message = "";
			int indexOfFirstHash = coded.indexOf('#');
			int indexOfSecondHash = coded.indexOf('#', indexOfFirstHash + 1);
			message += coded.substring(indexOfSecondHash + 1);

			gc.receiveNameChange(pID, message);
		} else {
			// number of tanks

			String[] instructionArray = coded.split("!!");
			// for all of the instructions, determine what type they are and act
			// accordingly...

			for (int i = 0; i < instructionArray.length; i++) {
				String[] parsedInstruction = instructionArray[i].split("#");
				switch (parsedInstruction[0]) {
				// decode an id
				case "I":
					int clientID = Integer.parseInt(parsedInstruction[1]);
					gc.setID(clientID);
					break;
				// decode a tank position
				case "T":
					int id = Integer.parseInt(parsedInstruction[1]);
					int x = Integer.parseInt(parsedInstruction[2]);
					int y = Integer.parseInt(parsedInstruction[3]);
					double bAngle = Double.parseDouble(parsedInstruction[4]);
					double tAngle = Double.parseDouble(parsedInstruction[5]);
					gc.updateTank(id, x, y, bAngle, tAngle);
					break;
				// decode of a projectile
				case "P":
					int xP = Integer.parseInt(parsedInstruction[1]);
					int yP = Integer.parseInt(parsedInstruction[2]);
					int xD = Integer.parseInt(parsedInstruction[3]);
					int yD = Integer.parseInt(parsedInstruction[4]);
					int team = Integer.parseInt(parsedInstruction[5]);
					gc.addProjectile(xP, yP, xD, yD, team);
					break;
				// decode a keyholder
				case "K":
					int idK = Integer.parseInt(parsedInstruction[1]);
					gc.setKeyHolder(idK);
					break;
				// decode a keyposition
				case "KP":
					int xK = Integer.parseInt(parsedInstruction[1]);
					int yK = Integer.parseInt(parsedInstruction[2]);
					int vis = Integer.parseInt(parsedInstruction[3]);
					gc.setKeyPosition(xK, yK, vis);
					break;
				// decode a health points
				case "H":
					int tId = Integer.parseInt(parsedInstruction[1]);
					int tHealth = Integer.parseInt(parsedInstruction[2]);
					gc.setTankHealth(tId, tHealth);
					break;
				// decode an instant message
				case "IM":
					int playerID = Integer.parseInt(parsedInstruction[1]);
					String message = "";
					message += parsedInstruction[2];
					gc.receiveMessage(playerID, message);
					break;
				// decode a start game message
				case "SG":
					// Tell The main menu of the client that the game needs to
					// start.
					gc.startGame();
					break;
				case "R":
					// We need to tell the client that some client is ready, by
					int readyID = Integer.parseInt(parsedInstruction[1]);
					gc.updateReady(readyID);
					break;
				// decode a team id message.
				case "TI":
					int id1 = Integer.parseInt(parsedInstruction[1]);
					int teamID = Integer.parseInt(parsedInstruction[2]);
					gc.updateTeam(id1, teamID);
					break;
				// decode a score message.
				case "S":
					int teamId = Integer.parseInt(parsedInstruction[1]);
					int score = Integer.parseInt(parsedInstruction[2]);
					gc.updateScores(teamId, score);
					break;
				// decode a timer.
				case "TIMER":
					String timer = parsedInstruction[1];
					gc.setTime(timer);
					break;
				// decode a message about respwaning
				case "RES":
					int id3 = Integer.parseInt(parsedInstruction[1]);
					gc.respawnTank(id3);
					break;
				// decode the end game message
				case "END":
					int winner = Integer.parseInt(parsedInstruction[1]);
					gc.endGame(winner);
					break;
				// decode a number of players message.
				case "NP":
					int numberOfPlayers = Integer
							.parseInt(parsedInstruction[1]);
					gc.setNumberOfPlayers(numberOfPlayers);
					break;
				// decode an initial position message.
				case "INITP":
					int idpos = Integer.parseInt(parsedInstruction[1]);
					int xip = Integer.parseInt(parsedInstruction[2]);
					int yip = Integer.parseInt(parsedInstruction[3]);
					gc.setInitialPosition(idpos, xip, yip);
					break;
				// decode a game type message.
				case "GT":
					String gameType = parsedInstruction[1];
					gc.receiveGameType(gameType);
					break;
				// decode a message about the remaining time for obtaining
				// objective's points
				case "O":
					int timeRemaining = Integer.parseInt(parsedInstruction[1]);
					gc.setTimeRemaining(timeRemaining);
					break;
				}

			}
		}

		return null;
	}

	/**
	 * Data object which allows the parser to return more than on data object in
	 * a method call
	 * 
	 * 
	 */
	public class DataObject {
		public final Entity player1;
		public final ArrayList<Projectile> projectiles;

		/**
		 * Constructor for the data object.
		 * 
		 * @param player1
		 * @param projectiles
		 */
		public DataObject(Entity player1, ArrayList<Projectile> projectiles) {
			this.player1 = player1;
			this.projectiles = projectiles;
		}

	}

}
