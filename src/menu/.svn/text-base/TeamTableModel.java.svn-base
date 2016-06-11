package menu;

import game.objects.Tank;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 * Renders based on changes to the tanks game data. Returns values for tankID,
 * name and ready status.
 * 
<<<<<<< .mine
 * @author Samuel Hill
 * A class to handle the data contained within the team tables
 * They render based on the tank details contained in the local gamedata
=======
 * @author Samuel Hill
 * 
>>>>>>> .r299
 */
public class TeamTableModel extends AbstractTableModel {
	private int playersPerTeam;
	// which team? as in 1 or 2
	private int teamNumber;

	private ArrayList<Tank> tanks;
	private int clientID;
	private String readyImgLoc = "src/resources/Ready_small.png";
	private String unreadyImgLoc = "src/resources/Unready_small.png";
	private ImageIcon readyIcon = new ImageIcon(readyImgLoc, "Ready");

	private ImageIcon unreadyIcon = new ImageIcon(unreadyImgLoc, "Not Ready");
	/**
	 * Constructs the table model with reference to its team number
	 * @param teamNumber
	 */
	/**
	 * Constructor
	 * 
	 * @param teamNumber
	 *            the corresponding team's id.
	 */
	public TeamTableModel(int teamNumber) {
		super();
		this.teamNumber = teamNumber;
		this.tanks = new ArrayList<Tank>();

	}
	/**
	 * Constructs the table model independent of team number
	 */
	/**
 * 
 */
	public TeamTableModel() {
		super();
		this.tanks = new ArrayList<Tank>();
	}
	/**
	 * @param column index for which a column name must be returned 
	 */
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case 0:
			return "ID";
		case 1:
			return "Name";
		case 2:
			return "Ready";
		}
		return null;
	}
	/**
	 * returns number of columns in the column model
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	/**
	 * Number of rows returned as the number of tanks for which details should be displayed
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return tanks.size();
	}
	/**
	 * @param x The row index to be rendered
	 * @param y The column index to be rendered
	 * A method to return the value of the tank based on the row
	 * corresponding to tanklist index, and the column index, pertaining to the tank field. 
	 */
	@Override
	public Object getValueAt(int x, int y) {
		// TODO Auto-generated method stub
		if (tanks.get(x) == null || x >= tanks.size()) {
			return "No Player";
		} else {
			switch (y) {
			case 0:
				return tanks.get(x).getId();

			case 1:
				String tankName = tanks.get(x).getName();
				if (!(tankName == null)) {

					return tankName;
				} else
					return tanks.get(x).getId();
			case 2:
				Boolean readyFlag = tanks.get(x).isReady();
				if (readyFlag) {

					// System.out.println("player "+tanks.get(x).getId()+" is ready");
					return readyIcon;
				} else {

					// System.out.println("player "+tanks.get(x).getId()+" is not ready");
					return unreadyIcon;
				}

			}

		}
		return null;

	}

	/**
	 * A method to ensure that no cells are editable upon being rendered!
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public Tank getTank(int rowIndex) {
		return tanks.get(rowIndex);
	}

	/**
	 * A method to remove a tank by specifying player ID
	 * 
	 * @param tankID
	 */
	public void removeById(int tankID) {
		for (int i = 0; i < tanks.size(); i++) {
			if (tanks.get(i).getId() == tankID) {
				tanks.remove(i);
			}
		}
		fireTableDataChanged();
	}

	/**
	 * A method to add a tank to a team
	 * 
	 * @param tank
	 */
	public void addTank(Tank tank) {
		tanks.add(tank);
		fireTableDataChanged();
	}
	/**
	 * Updates the main data field of this class, from which the table is rendered
	 * @param newTanks the new list of tanks
	 */
	public void setTankList(ArrayList<Tank> newTanks) {
		this.tanks = newTanks;
		fireTableDataChanged();
	}
	/**
	 * Sets the client id for this given table
	 * @param pID
	 */
	public void setPlayerID(int pID) {
		this.clientID = pID;
	}
	/**
	 * An overriden method to allow the rendering of icons within the table
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub

		return getValueAt(0, columnIndex).getClass();

	}

}