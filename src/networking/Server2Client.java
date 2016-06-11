package networking;

import game.objects.Key;
import game.objects.Projectile;
import game.objects.Tank;

import java.util.ArrayList;

import server.GameServer;

/**
 * Parser class for server to client communication
 * 
 * @author Bella Dunovska, Martin Mihov, Alexandre Portugal, Samuel Hill
 * 
 * 
 */
public class Server2Client {
	private GameServer gs;

	public Server2Client(GameServer gs) {
		this.gs = gs;
	}

	/**
	 * Method to code a start game message.
	 * 
	 * @param type
	 * @return the string
	 */
	public String code(String type) {
		if (type.contains("[SG]")) {
			return "SG#" + "!!";

		} else {
			return "";
		}
	}

	/**
	 * Method to code chat messages and new nickname messages.
	 * 
	 * @param type
	 * @param ID
	 * @param message
	 * @return
	 */
	public String code(String type, Integer id, String message) {

		if (type.contains("[IM]")) {
			return "IM#" + id + "#" + message;
		} else if (type.contains("[NC]")) {
			// Where message refers to a new nickname
			return "NC#" + id + "#" + message;
		} else {
			return "";
		}
	}

	/**
	 * Method to code a key message.
	 * 
	 * @param id
	 * @param tankArray
	 * @param projectiles
	 * @param key
	 * @return
	 */
	public String code(String type, Key key) {
		String keyString = "";
		if (type.contains("[KP]")) {
			keyString += "KP#" + key.getX() + "#" + key.getY() + "#"
					+ key.getVisible() + "!!";
		} else {
			keyString = "";
		}
		return keyString;
	}

	/**
	 * Method to code the time message.
	 * 
	 * @param type
	 * @param time
	 * @return
	 */
	public String code(String type, String time) {
		if (type.contains("[TIMER]")) {
			return "TIMER#" + time + "!!";
		} else if (type.contains("[GT]")) {
			return "GT#" + time + "!!";
		} else {
			return "";
		}
	}

	/**
	 * Method to code a timer message.
	 * 
	 * @param type
	 * @param tanks
	 * @param projectiles
	 * @param key
	 * @return
	 */
	public String code(String type, Tank[] tanks,
			ArrayList<Projectile> projectiles, String time) {
		return code("[T]", tanks) + code("[P]", projectiles)
				+ code("[TIMER]", time);
	}

	/**
	 * Method to code a id setting, ready state, keyholder id, resert, end
	 * state, number of players and objective messages.
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public String code(String type, Integer id) {
		if (type.contains("[I]")) {
			return "I#" + id + "!!";
		} else if (type.contains("[R]")) {
			return "R#" + id + "!!";
		} else if (type.contains("[K]")) {
			return "K#" + id + "!!";
		} else if (type.contains("[TIMER]")) {
			return "TIMER#" + id + "!!";
		} else if (type.contains("[RES]")) {
			return "RES#" + id + "!!";
		} else if (type.contains("[END]")) {
			// here id is interpreted as a winner team.
			return "END#" + id + "!!";

		} else if (type.contains("[NP]")) {
			// here id is interpreted as the number of players
			return "NP#" + id + "!!";

		} else if (type.contains("[O]")) {
			return "O#" + id + "!!";
		} else {
			return "";
		}
	}

	/**
	 * Method to code the initial position message.
	 * 
	 * @param type
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 */
	public String code(String type, int id, int x, int y) {
		if (type.contains("[INITP]")) {
			return "INITP#" + id + "#" + x + "#" + y + "!!";
		} else {
			return "";
		}
	}

	/**
	 * Method to code both id and teamID messages.
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public String code(String type, Integer id, Integer teamID) {
		if (type.contains("[I]")) {
			return code("[I]", id);
		} else if (type.contains("[S]")) {
			// in this case we interpreted the id as a teamId and the teamID as
			// a
			// score.
			return "S#" + id + "#" + teamID + "!!";
		} else if (type.contains("[TI]")) {
			return "TI#" + id + "#" + teamID + "!!";
		} else if (type.contains("[H]")) {
			// in this case id is the id of the tank and the teamID is
			// interpreted as
			// health
			return "H#" + id + "#" + teamID + "!!";

		} else {
			return "";

		}
	}

	/**
	 * Method to code a tank message.
	 * 
	 * @param type
	 * @param id
	 * @param tankArray
	 * @return
	 */
	public String code(String type, Tank[] tankArray) {
		String tankStr = "";
		if (type.contains("[T]")) {
			for (int i = 0; i < tankArray.length; i++) {
				// Each Individual projectiles values will be split by a -.

				tankStr += "T" + "#" + tankArray[i].getId() + "#"
						+ tankArray[i].getX() + "#" + tankArray[i].getY() + "#"
						+ tankArray[i].getBotAngle() + "#"
						+ tankArray[i].getTopAngle() + "!!"; // projectiles.get(i).getVariable();

			}
		} else {
			tankStr = "";
		}

		return tankStr;
	}

	/**
	 * Method to code a projectile message.
	 * 
	 * @param type
	 * @param id
	 * @param tankArray
	 * @param projectiles
	 * @return
	 */
	public String code(String type, ArrayList<Projectile> projectiles) {

		// Goes through the projectile array and codes it
		String projStr = "";

		if (type.contains("[P]")) {
			for (int i = 0; i < projectiles.size(); i++) {
				// Each Individual projectiles values will be split by a -.

				projStr += "P#" + projectiles.get(i).getX() + "#"
						+ projectiles.get(i).getY() + "#"
						+ projectiles.get(i).getxDir() + "#"
						+ projectiles.get(i).getyDir() + "#"
						+ projectiles.get(i).getTeam() + "!!"; // projectiles.get(i).getVariable();
			}

		} else {
			projStr = "";
		}

		return projStr;
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
	public void decode(String coded) {
		// Messages must be parsed differently, otherwise the parse separators
		// may be entered in chat and break.
		String[] checkString = coded.split("#");
		if (checkString[0].compareTo("IM") == 0) {

			int pID = Integer.parseInt(checkString[1]);
			String message = "";
			// lets find the index of # first occurence

			int indexOfFirstHash = coded.indexOf('#');
			// index of second hash occurence indicates the start of the
			// messaging string.
			int indexOfSecondHash = coded.indexOf('#', indexOfFirstHash + 1);
			// Message between second hash and the end of the coded instruction.
			message += coded.substring(indexOfSecondHash + 1);

			gs.sendIM(pID, message);

		} else if (checkString[0].compareTo("NC") == 0) {

			int pID = Integer.parseInt(checkString[1]);
			String message = "";
			int indexOfFirstHash = coded.indexOf('#');
			int indexOfSecondHash = coded.indexOf('#', indexOfFirstHash + 1);
			message += coded.substring(indexOfSecondHash + 1);

			gs.sendNC(pID, message);
		} else {

			// Split the code into strings.
			String[] arrayString = coded.split("!!");

			for (int i = 0; i < arrayString.length; i++) {

				String[] arrayInLoop = arrayString[i].split("#");

				// use the first element in the array to identify the type of
				// the
				// action.
				switch (arrayInLoop[0]) {
				// decode a tank
				case "T":
					int id = Integer.parseInt(arrayInLoop[1]);
					int x = Integer.parseInt(arrayInLoop[2]);
					int y = Integer.parseInt(arrayInLoop[3]);
					double bAngle = Double.parseDouble(arrayInLoop[4]);
					double tAngle = Double.parseDouble(arrayInLoop[5]);
					gs.updateTank(id, x, y, bAngle, tAngle);
					break;
				// decode of a projectile message
				case "P":
					int xP = Integer.parseInt(arrayInLoop[1]);
					int yP = Integer.parseInt(arrayInLoop[2]);
					int xD = Integer.parseInt(arrayInLoop[3]);
					int yD = Integer.parseInt(arrayInLoop[4]);
					int team = Integer.parseInt(arrayInLoop[5]);
					gs.addProjectile(xP, yP, xD, yD, team);
					break;
				case "O":
					// decode of obstacles.
					break;
				// decode a ready state
				case "R":
					int id1 = Integer.parseInt(arrayInLoop[1]);
					System.out.println("ready" + id1);
					gs.sendReadyState(id1);
					break;
				// decode a start game message
				case "SG":
					System.out.println("Start");
					gs.sendStartState();
					break;
				// decode a change team message
				case "CT":
					int ctID = Integer.parseInt(arrayInLoop[1]);
					gs.changeTeam(ctID);
					break;

				}
			}
		}

	}
}
