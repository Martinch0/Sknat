package game.objects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Base class for map tiles which each obstacle tile type extend.
 * 
 * @author Alexandre Portugal
 *
 */
public class Obstacle extends Entity {

	//Dimensions of the Obstacle

	private String imgPath;


	/**
	 * Constuctor for an object
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Obstacle(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.setHeight(height);
		this.setWidth(width);
		
	}
	
	
	
	/**
	 * Paints the obstacle
	 * 
	 * @param g2d
	 */
	public void paintObstacle(Graphics2D g2d)
	{
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y, this.getWidth(), this.getHeight());
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
