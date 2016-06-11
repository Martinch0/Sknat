package game.objects.obstacles;

import game.objects.Obstacle;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author Alexandre Portugal,Tendaishe Nyamapfeni
 *
 */
public class HeadquartersObs extends Obstacle {
	String redBase = "src/resources/redHouse.png";
	String blueBase = "src/resources/house.png";
	String imgPath;
	
	public HeadquartersObs(int x, int y, int base) {

		super(x, y, 0, 0);
		if (base == 0) {
			imgPath = redBase;
		} else {
			imgPath = blueBase;
		}
		
		// Creating images for tank base
		ImageIcon a = new ImageIcon(imgPath);
		Image test = a.getImage();
		this.setHeight(test.getHeight(null));
		this.setWidth(test.getWidth(null));

	}

	/**
	 * Paints the obstacle
	 * 
	 * @param g2d
	 */
	@Override
	public void paintObstacle(Graphics2D g2d) {
		ImageIcon obs = new ImageIcon(imgPath);
		g2d.drawImage(obs.getImage(), this.getX(), this.getY(), null);
	}
}
