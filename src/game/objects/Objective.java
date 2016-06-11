package game.objects;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

/**
 * Class that represents an objective.
 * 
 * @author Alexandre Portugal, Bella Dunvoska, Martin Mihov,Tendaishe Nyamapfeni
 * 
 */
public class Objective extends Entity {
	// Normal objective
	private String objectiveImg = "src/resources/Objective_Territory.png";

	// RED Counter objective
	private String objectiveImgRed1 = "src/resources/Objective_Red_1.png";
	private String objectiveImgRed2 = "src/resources/Objective_Red_2.png";
	private String objectiveImgRed3 = "src/resources/Objective_Red_3.png";
	// BLUE Counter objective
	private String objectiveImgBlue1 = "src/resources/Objective_Blue_1.png";
	private String objectiveImgBlue2 = "src/resources/Objective_Blue_2.png";
	private String objectiveImgBlue3 = "src/resources/Objective_Blue_3.png";
	private ImageIcon displayedObjectiveIcon;

	public Objective(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		displayedObjectiveIcon = new ImageIcon(objectiveImg);
		this.setHeight(displayedObjectiveIcon.getIconHeight());
		this.setWidth(displayedObjectiveIcon.getIconWidth());

	}

	/**
	 * Paints the obstacle
	 * 
	 * @param g2d
	 */
	public void paintObjective(Graphics2D g2d) {

		g2d.drawImage(displayedObjectiveIcon.getImage(), this.getX(),
				this.getY(), null);
	}

	public void changeObjectiveImg(int keyHolder, int timer) {
		// RED TEAM timer
		if (keyHolder == 0 && timer > 0) {
			if (timer == 3) {
				displayedObjectiveIcon = new ImageIcon(objectiveImgRed3);
			} else if (timer == 2) {
				displayedObjectiveIcon = new ImageIcon(objectiveImgRed2);
			} else {
				displayedObjectiveIcon = new ImageIcon(objectiveImgRed1);
			}
			// BLUE TEAM timer
		} else if (keyHolder == 1 && timer > 0) {
			if (timer == 3) {
				displayedObjectiveIcon = new ImageIcon(objectiveImgBlue3);
			} else if (timer == 2) {
				displayedObjectiveIcon = new ImageIcon(objectiveImgBlue2);
			} else {
				displayedObjectiveIcon = new ImageIcon(objectiveImgBlue1);
			}
			// Set image back to the original objective
		} else {
			displayedObjectiveIcon = new ImageIcon(objectiveImg);
		}
	}

}
