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
public class MapObstacleVertical extends Obstacle {
	String imgPath = "src/resources/Obstacle_Vertical.png";

	public MapObstacleVertical(int x, int y) {

		super(x, y, 0, 0);
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
