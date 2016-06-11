package game.objects;

import game.SoundPlay;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

/**
 * Class that represents a Missile. Extends projectiles and has methods related
 * to how it moves.
 * 
 * @author Alexandre Portugal
 * 
 */
public class Missile extends Projectile {

	// Images for the missiles
	String redMissileImg = "src/resources/Missile.png";
	String blueMissileImg = "src/resources/blueMissile.png";

	// Actual missile Img that is drawn
	String missileIMG = "src/resources/Missile.png";

	// Actual missile Img that is drawn
	String explosionIMG = "src/resources/explosion.png";

	// Determines if the missile is active
	private int active;
	int ox, oy, baseX, baseY, team;

	public Missile(int x, int y, int baseX, int baseY, int team) {
		super(x, y, baseX, baseY, team);
		ox = x;
		oy = y;
		this.baseX = baseX;
		this.baseY = baseY;
		this.team = team;
		if (team == 0) {
			missileIMG = redMissileImg;
		} else {
			missileIMG = blueMissileImg;
		}

		active = 0;

	}

	public void restart() {
		active = 1;
		this.x = ox;
		this.y = oy;
		this.setRealX(ox);
		this.setRealY(oy);

		this.setyDir(baseY);
		this.setxDir(baseX);

		this.calculateMovement();

		startMissile();
	}

	public void startMissile() {

		moveMissile();
	}

	public void moveMissile() {
		int keepMoving = 1;
		System.out.println(this.getXIncrement() + "  " + this.getYIncrement());
		while (keepMoving == 1) {
			// Increments the position of the missile
			active = 1;
			if (this.getXIncrement() > 0) {
				if (this.getxDir() > this.getRealX()) {
					this.setRealX((this.getRealX() + this.getXIncrement()));
				} else {
					keepMoving = 0;
				}
			} else {
				if (this.getxDir() < this.getRealX()) {
					this.setRealX((this.getRealX() + this.getXIncrement()));
				} else {
					keepMoving = 0;
				}
			}

			if (this.getYIncrement() > 0) {
				if (this.getyDir() > this.getRealY()) {
					this.setRealY((this.getRealY() + this.getYIncrement()));
				} else {
					keepMoving = 0;
				}
			} else {
				if (this.getyDir() < this.getRealY()) {
					this.setRealY((this.getRealY() + this.getYIncrement()));
				} else {
					keepMoving = 0;
				}
			}

			try {
				System.out.println("END");
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("moving it");
		}

		SoundPlay.playExplosionSound();
		missileIMG = explosionIMG;

		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (team == 0) {
			missileIMG = redMissileImg;
		} else {
			missileIMG = blueMissileImg;
		}
		
		
		active = 0;
		// Sleep the thread depeing on how fast you want the missile to go

	}

	/**
	 * Draws the projectile
	 * 
	 * @param g2d
	 */
	@Override
	public void paintProjectile(Graphics2D g2d) {
		ImageIcon proj = new ImageIcon(missileIMG);
		g2d.drawImage(proj.getImage(), (int) this.getRealX(),
				(int) this.getRealY(), null);
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}
}
