package game.objects.floortiles;

import game.objects.FloorTile;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author Alexandre Portugal,Tendaishe Nyamapfeni
 *
 */
public class SandFloor extends FloorTile {
	String imgPath = "src/resources/floorSand2.png";
	
	public SandFloor(int x, int y) {
		
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
	public void paintFloorTile(Graphics2D g2d)
	{
		ImageIcon obs = new ImageIcon(imgPath);
		g2d.drawImage(obs.getImage(),this.getX(), this.getY(), null);
	}
}
