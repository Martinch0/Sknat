package game.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class that represents a projectile. Has methods to calculate projectile
 * motion.
 * 
 * @author Alexandre Portugal
 */
public class Projectile extends Entity {

	private int xDir;
	private int yDir;
	private int speed = 7;
	private int team;

	private double realX;
	private double realY;

	private int range = 250;
	private double distance = 0;
	private int projectSize = 11;

	// Variables for projectile movement updating
	private double xIncrement;
	private double yIncrement;

	protected String imgPath = "src/resources/proj.png";

	/**
	 * @return the xDir
	 */
	public int getxDir() {
		return xDir;
	}

	/**
	 * @param xDir
	 *            the xDir to set
	 */
	public void setxDir(int xDir) {
		this.xDir = xDir;
	}

	/**
	 * @return the yDir
	 */
	public int getyDir() {
		return yDir;
	}

	/**
	 * @param yDir
	 *            the yDir to set
	 */
	public void setyDir(int yDir) {
		this.yDir = yDir;
	}

	/**
	 * @return the xIncrement
	 */
	public double getXIncrement() {
		return xIncrement;
	}

	/**
	 * @param xIncrement
	 *            the xIncrement to set
	 */
	public void setXIncrement(double xIncrement) {
		this.xIncrement = xIncrement;
	}

	/**
	 * @return the yIncrement
	 */
	public double getYIncrement() {
		return yIncrement;
	}

	/**
	 * @param yIncrement
	 *            the yIncrement to set
	 */
	public void setYIncrement(double yIncrement) {
		this.yIncrement = yIncrement;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	public Projectile(int x, int y, int xDir, int yDir, int team) {
		this.x = x;
		this.y = y;
		this.xDir = xDir;
		this.yDir = yDir;
		this.team = team;
		this.realX = x;
		this.realY = y;

		// Creating images for tank base
		ImageIcon a = new ImageIcon(imgPath);
		Image test = a.getImage();
		this.setHeight(test.getHeight(null));
		this.setWidth(test.getWidth(null));

		calculateMovement();
	}

	/**
	 * Calculates the direction of movement per tick in terms of x and y
	 */
	public void calculateMovement() {

		// Calculates the magnitude of x and y motion
		double deltaX = xDir - x;
		double deltaY = yDir - y;

		// Calculates the absolute magnitude
		double absDeltaX = Math.abs(deltaX);
		double absDeltaY = Math.abs(deltaY);

		// Finds constant k
		double k = absDeltaX / absDeltaY;

		// Squares constant
		double kSquared = k * k;

		// Adds 1 to constant
		double kSquaredAdded = kSquared + 1;

		// Divides speed squared by (k squared + 1)
		double yDistancePerTickSquared = speed * speed / kSquaredAdded;

		// Square roots to get yDistance per tick
		double yDistancePerTick = Math.sqrt(yDistancePerTickSquared);

		// Finds the proportional x value per tick
		double xDistancePerTick = yDistancePerTick * k;

		double changeX;
		double changeY;

		/*
		 * Finds the direction of movement
		 */
		if (deltaX >= 0) {
			changeX = xDistancePerTick;
		} else if (deltaX <= 0) {
			changeX = -xDistancePerTick;
		} else {
			changeX = 0;
		}

		if (deltaY >= 0) {
			changeY = yDistancePerTick;
		} else if (deltaY <= 0) {
			changeY = -yDistancePerTick;
		} else {
			changeY = 0;
		}

		// Updates x and y increment
		this.xIncrement = changeX;
		this.yIncrement = changeY;

	}

	/**
	 * Draws the projectile
	 * 
	 * @param g2d
	 */
	public void paintProjectile(Graphics2D g2d) {
		ImageIcon proj = new ImageIcon(this.getImgPath());
		g2d.drawImage(proj.getImage(), this.getX(), this.getY(), null);
	}

	public int getProjectSize() {
		return projectSize;
	}

	public void setProjectSize(int projectSize) {
		this.projectSize = projectSize;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public double getDistance() {
		return distance;
	}

	public void incrementDistance(double distance) {
		this.distance += distance;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getRealY() {
		return realY;
	}

	public void setRealY(double realY) {
		this.realY = realY;
	}

	public double getRealX() {
		return realX;
	}

	public void setRealX(double realX) {
		this.realX = realX;
	}

}
