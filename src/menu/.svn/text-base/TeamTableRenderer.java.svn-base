package menu;

import game.objects.Tank;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.GameClient;

/**

 * A class to customize colour rendering of the team changing tables

 * Performs validation checks on team changes and colours table accordingly ,
 * indicates corresponding player
 * 

 * @author Samuel Hill
 * 
 */
public class TeamTableRenderer extends DefaultTableCellRenderer {
	/**
	 * Returns the appropriate component to be displayed 
	 * for the given row and column index
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		boolean highlightFlag = false;
		int playersPerTeam = 0;
		setHorizontalAlignment(CENTER);
		//Validates the team entries so unbalanced players are highlighted red
		if (GameClient.NUMBER_OF_PLAYERS != 0) {

			playersPerTeam = (GameClient.NUMBER_OF_PLAYERS / 2);
			highlightFlag = true;

		}
		//if unbalanced then highlight
		if (highlightFlag && (row >= playersPerTeam)) {

			setBackground(Color.RED);

		} else {
			setBackground(Color.ORANGE);
		}
		//retrieves data so that the table may identify itself 
		TeamTableModel tableModel = (TeamTableModel) table.getModel();
		
		Tank currentTank = tableModel.getTank(row);
		//If the tank being rendered belongs to the client, then border it blue
		if (currentTank.getId() == MainMenu.playerID) {


			setBorder(BorderFactory.createMatteBorder(row, 0, row, 2, Color.blue));

			// setBorder(BorderFactory.createLineBorder(Color.blue));
			setBorder(BorderFactory.createMatteBorder(row, 0, row, 2,
					Color.blue));

		}
		return this;
	}
}
