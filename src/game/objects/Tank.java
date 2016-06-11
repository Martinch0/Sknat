package game.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

/**
 * Class that represents a tank. Has methods to calculate pointing angle based
 * on mouse coordinates.
 * 
 * @author Alexandre Portugal, Bella Dunvoska, Martin Mihov, Tendaishe
 *         Nyamapfeni, Samuel Hill
 * 
 */
public class Tank extends Entity {

	// Initial position of a tank
	private int originalX;
	private int originalY;

	// Angle at which the tanks top (cannon) and bot (body) are facing
	private double topAngle;
	private double botAngle;
	private int tankSize;

	// Tank Health
	private int health;

	// Speed of tank
	private int speed = 5;

	// Paths to images tot he tank
	private String tankTopImg = "src/resources/tankTop.png";
	private String tankBotImg = "src/resources/tankBasePublicDomain.png";

	// Destroyed tank
	static String destroyedTank = "src/resources/destroyedTank.png";

	// Red Tank
	static String redTankTop = "src/resources/redTankTop.png";
	static String redTankBot = "src/resources/redTankBot.png";

	static String redHealthBar1 = "src/resources/redHealthBar1.png";
	static String redHealthBar2 = "src/resources/redHealthBar2.png";
	static String redHealthBar3 = "src/resources/redHealthBar3.png";

	// Green Tank
	static String blueTankTop = "src/resources/blueTankTop.png";
	static String blueTankBot = "src/resources/blueTankBot.png";

	// Blue health for ally tanks
	static String blueHealthBar1 = "src/resources/blueHealthBar1.png";
	static String blueHealthBar2 = "src/resources/blueHealthBar2.png";
	static String blueHealthBar3 = "src/resources/blueHealthBar3.png";

	// Player health bar are yellow for the tank the player is controlling
	static String yellowHealthBar1 = "src/resources/yellowHealthBar1.png";
	static String yellowHealthBar2 = "src/resources/yellowHealthBar2.png";
	static String yellowHealthBar3 = "src/resources/yellowHealthBar3.png";

	// Yellow Top to hold the key
	static String yellowTankTop = "src/resources/yellowTankTop.png";

	// Tank ID
	private int Id;

	// Tank Team (0 red or 1 green)
	private int teamId;

	// Nickname for player
	private String name;

	// Flag to indicate the ready state for a player
	private boolean ready = false;

	public Tank(int x, int y, int Id) {
		this.x = x;
		this.y = y;
		setOriginalX(x);
		setOriginalY(y);
		this.Id = Id;
		this.topAngle = 0;
		this.botAngle = 0;
		this.setTankSize(32);

		resetTankImg();

		// Creating images for tank base
		ImageIcon a = new ImageIcon(tankBotImg);
		Image test = a.getImage();
		this.setHeight(test.getHeight(null));
		this.setWidth(test.getWidth(null));
		health = 3;
	}

	private void resetTankImg() {
		if (teamId == 0) {
			tankTopImg = redTankTop;
			tankBotImg = redTankBot;
		} else {
			tankTopImg = blueTankTop;
			tankBotImg = blueTankBot;
		}
	}

	/**
	 * @return the topAngle
	 */
	public double getTopAngle() {
		return topAngle;
	}

	/**
	 * Tank respawn
	 */
	public void tankRespawn(int respawnX, int respawnY) {
		health = 3;
		x = respawnX;
		y = respawnY;
	}

	/**
	 * @param topAngle
	 *            the topAngle to set
	 */
	public void setTopAngle(double topAngle) {
		this.topAngle = topAngle;
	}

	/**
	 * @return the botAngle
	 */
	public double getBotAngle() {
		return botAngle;
	}

	/**
	 * @param botAngle
	 *            the botAngle to set
	 */
	public void setBotAngle(double botAngle) {
		this.botAngle = botAngle;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		Id = id;
	}

	/**
	 * Makes the tanks top yellow
	 */
	public void makeTopYellow() {
		tankTopImg = yellowTankTop;
	}

	/**
	 * Unmakes the tanks top yellow
	 */
	public void makeTopNotYellow() {
		if (teamId == 0) {
			tankTopImg = redTankTop;
		} else {
			tankTopImg = blueTankTop;
		}
	}

	/**
	 * Calculates angle that the tanks top should face when the mouse is passed
	 * as coordinates.
	 * 
	 * @param mx
	 * @param my
	 * 
	 * @return the angle
	 */
	public double calcTopAngle(int mx, int my) {
		// X and Y difference in mouse position and tank position
		double dy = my - this.getY();
		double dx = mx - this.getX();

		// Variables for calculation
		double add;
		double angle;
		double posDY = Math.abs(dy);
		double posDX = Math.abs(dx);

		// Mathematical calculation of the angle that the tank should be
		// pointing
		if (dx >= 0) {
			if (dy >= 0) {
				add = Math.PI / 2;
				angle = add + Math.atan(posDY / posDX);
			} else {
				angle = (Math.PI / 2) - Math.atan(posDY / posDX);
			}

		} else {
			if (dy >= 0) {
				add = Math.PI;
				angle = add + (Math.PI / 2) - Math.atan(posDY / posDX);
			} else {
				add = 3 * Math.PI / 2;
				angle = add + Math.atan(posDY / posDX);
			}

		}

		// Returns the angle and sets it to this object
		topAngle = angle;
		return angle;
	}

	/**
	 * @param mx
	 *            - Mouse x coordinate
	 * @param my
	 *            - Mouse y coordinate
	 * @param cx
	 *            - x coordination of the start Panel location on screen
	 * @param cy
	 *            - y coordination of the start Panel location on screen
	 * @return
	 */
	public void calcAngleOutsideScreen(int mx, int my, int cx, int cy, int tx,
			int ty, double sf, int by) {

		// gets the change in x and y
		double dy = my - ((super.getY() + (super.getHeight() / 2)) * sf) - ty
				- by - cy;
		double dx = mx - ((super.getX() + (super.getWidth() / 2)) * sf) - tx
				- cx;

		// variables needed for calculations
		double add;
		double angle;

		// absulute values of the change in x and y
		double posDY = Math.abs(dy);
		double posDX = Math.abs(dx);

		// Mathematical calculation of the angle that the tank should be
		// pointing, uses pythagoras to determine the angle

		// positive dx
		if (dx >= 0) {
			// positive dy
			if (dy >= 0) {
				add = Math.PI / 2;
				angle = add + Math.atan(posDY / posDX);
				// negative dy
			} else {
				angle = (Math.PI / 2) - Math.atan(posDY / posDX);
			}
			// Negative dx
		} else {
			// positive dy
			if (dy >= 0) {
				add = Math.PI;
				angle = add + (Math.PI / 2) - Math.atan(posDY / posDX);
				// negative dy
			} else {
				add = 3 * Math.PI / 2;
				angle = add + Math.atan(posDY / posDX);
			}

		}

		// double angle = Math.PI/2;

		// System.out.println("pre " + angle + "ADDED " + add);
		topAngle = angle;
	}

	public void paintTank(Graphics2D g2d, int myID, int tankID, int myTeam) {

		if (health == 3) {
			resetTankImg();
			if (myID != tankID) {
				if (teamId != myTeam) {
					ImageIcon healthBar = new ImageIcon(redHealthBar3);
					g2d.drawImage(
							healthBar.getImage(),
							this.getX(),
							this.getY() - (this.getHeight() / 2)
									- healthBar.getIconHeight(), null);
				} else {
					ImageIcon healthBar = new ImageIcon(blueHealthBar3);
					g2d.drawImage(
							healthBar.getImage(),
							this.getX(),
							this.getY() - (this.getHeight() / 2)
									- healthBar.getIconHeight(), null);
				}
			} else {
				ImageIcon healthBar = new ImageIcon(yellowHealthBar3);
				g2d.drawImage(healthBar.getImage(), this.getX(), this.getY()
						- (this.getHeight() / 2) - healthBar.getIconHeight(),
						null);
			}

		} else if (health == 2) {
			resetTankImg();
			if (myID != tankID) {
				if (teamId != myTeam) {
					ImageIcon healthBar = new ImageIcon(redHealthBar2);
					g2d.drawImage(
							healthBar.getImage(),
							this.getX(),
							this.getY() - (this.getHeight() / 2)
									- healthBar.getIconHeight(), null);
				} else {
					ImageIcon healthBar = new ImageIcon(blueHealthBar2);
					g2d.drawImage(
							healthBar.getImage(),
							this.getX(),
							this.getY() - (this.getHeight() / 2)
									- healthBar.getIconHeight(), null);
				}
			} else {
				ImageIcon healthBar = new ImageIcon(yellowHealthBar2);
				g2d.drawImage(healthBar.getImage(), this.getX(), this.getY()
						- (this.getHeight() / 2) - healthBar.getIconHeight(),
						null);
			}

		} else if (health == 1) {
			resetTankImg();
			if (myID != tankID) {
				if (teamId != myTeam) {
					ImageIcon healthBar = new ImageIcon(redHealthBar1);
					g2d.drawImage(
							healthBar.getImage(),
							this.getX(),
							this.getY() - (this.getHeight() / 2)
									- healthBar.getIconHeight(), null);
				} else {
					ImageIcon healthBar = new ImageIcon(blueHealthBar1);
					g2d.drawImage(
							healthBar.getImage(),
							this.getX(),
							this.getY() - (this.getHeight() / 2)
									- healthBar.getIconHeight(), null);
				}
			} else {
				ImageIcon healthBar = new ImageIcon(yellowHealthBar1);
				g2d.drawImage(healthBar.getImage(), this.getX(), this.getY()
						- (this.getHeight() / 2) - healthBar.getIconHeight(),
						null);
			}

		} else if (health == 0) {
			tankTopImg = "src/resources/emptyPixel.png";
			tankBotImg = destroyedTank;
		}

		// Initializing variables
		Image tankImgVar;
		Image tankBaseImgVar;

		// Creating images for tank base
		ImageIcon tankBaseVar = new ImageIcon(tankBotImg);
		tankBaseImgVar = tankBaseVar.getImage();

		// Getting dimmensions of tank base
		int spriteHeight = this.getHeight();
		int spriteWidth = this.getWidth();

		// Rotatating shape
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(botAngle, this.getX() + spriteWidth / 2,
				this.getY() + spriteHeight / 2);

		affineTransform.translate(this.getX(), this.getY());

		// Drawing it
		g2d.drawImage(tankBaseImgVar, affineTransform, null);

		// Creating images for tank top
		ImageIcon it = new ImageIcon(tankTopImg);
		tankImgVar = it.getImage();

		// Getting dimensions of tank top
		spriteHeight = this.getHeight();
		spriteWidth = this.getWidth();

		AffineTransform affineTransform2 = new AffineTransform();
		affineTransform2.rotate(topAngle, this.getX() + spriteWidth / 2,
				this.getY() + spriteHeight / 2);

		affineTransform2.translate(this.getX(), this.getY());

		// Drawing it
		g2d.drawImage(tankImgVar, affineTransform2, null);
	}

	public int getTankSize() {
		return tankSize;
	}

	public void setTankSize(int tankSize) {
		this.tankSize = tankSize;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Moves character left
	 */
	public void moveLeft(Tank[] arraysTank, Obstacle[] obsArray) {

		if (checkBounds(-speed, 0, arraysTank, obsArray)) {
			x = x - speed;
			if (x < 0) {
				x = 0;
			}
		}

	}

	/**
	 * Moves character right
	 */
	public void moveRight(Tank[] arraysTank, Obstacle[] obsArray) {
		if (checkBounds(speed, 0, arraysTank, obsArray)) {
			x = x + speed;
		}
	}

	/**
	 * Moves character up
	 */
	public void moveUp(Tank[] arraysTank, Obstacle[] obsArray) {

		if (checkBounds(0, -speed, arraysTank, obsArray)) {
			y = y - speed;
			if (y < 0) {
				y = 0;
			}
		}

	}

	/**
	 * Moves character down
	 */
	public void moveDown(Tank[] arraysTank, Obstacle[] obsArray) {
		if (checkBounds(0, speed, arraysTank, obsArray)) {
			y = y + speed;
		}
	}

	/**
	 * Checks if you can move (aka obstacles)
	 * 
	 * @param movementX
	 * @param movementY
	 * @return
	 */
	public boolean checkBounds(int movementX, int movementY, Tank[] arraysTank,
			Obstacle[] obsArray) {

		// Checks for collision against other tanks
		for (int i = 0; i < arraysTank.length; i++) {
			if (i != this.Id) {
				int ox = arraysTank[i].getX();
				int oy = arraysTank[i].getY();

				if (((this.x + movementX >= ox) & (this.x + movementX <= (ox + this
						.getWidth())))
						| ((this.x + movementX + this.getWidth() >= ox) & (this.x
								+ movementX + this.getWidth() <= (ox + this
								.getWidth())))) {
					if (((this.y + movementY >= oy) & (this.y + movementY <= (oy + this
							.getHeight())))
							| ((this.y + movementY + this.getHeight() >= oy) & (this.y
									+ movementY + this.getHeight() <= (oy + this
									.getHeight())))) {
						return false;
					}
				}
			}
		}

		// Checks for collision against obstacles
		for (int i = 0; i < obsArray.length; i++) {

			int ox = obsArray[i].getX();
			int oy = obsArray[i].getY();

			if (((this.x + movementX >= ox) & (this.x + movementX <= (ox + obsArray[i]
					.getWidth())))
					| ((this.x + movementX + this.getWidth() >= ox) & (this.x
							+ movementX + this.getWidth() <= (ox + obsArray[i]
							.getWidth())))) {
				if (((this.y + movementY >= oy) & (this.y + movementY <= (oy + obsArray[i]
						.getHeight())))
						| ((this.y + movementY + this.getHeight() >= oy) & (this.y
								+ movementY + this.getHeight() <= (oy + obsArray[i]
								.getHeight())))) {
					return false;
				}

			}
		}

		return true;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTeamId() {
		return teamId;
	}

	/**
	 * Method to set the team id.
	 * 
	 * @param teamId
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;

		if (teamId == 0) {
			tankTopImg = redTankTop;
			tankBotImg = redTankBot;
		} else {
			tankTopImg = blueTankTop;
			tankBotImg = blueTankBot;
		}
	}

	/**
	 * Method to respawn the tank at the starting position.
	 */
	public void resetTank() {
		setX(getOriginalX());
		setY(getOriginalY());
		setHealth(3);
	}

	public int getOriginalX() {
		return originalX;
	}

	public void setOriginalX(int originalX) {
		this.originalX = originalX;
	}

	public int getOriginalY() {
		return originalY;
	}

	public void setOriginalY(int originalY) {
		this.originalY = originalY;
	}
}
