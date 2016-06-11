package game.objects;

import game.GameData;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

/**
 * Class that represents the key
 * 
 * @author Alexandre Portugal,Bella Dunvoska, Martin Mihov
 * 
 */
public class Key extends Entity {
	static String keyImg = "src/resources/Key.png";
	private int visible;
	private int keySize = 16;
	private int keyHolder = -1; // By default -1 (no holder)

	/**
	 * Constructor for the key
	 * 
	 * @param x
	 * @param y
	 * @param vis
	 */
	public Key(int x, int y, int vis) {
		this.x = x;
		this.y = y;
		this.setVisible(vis);
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	/**
	 * Paints the key
	 * 
	 * @param g2d
	 */
	public void paintKey(Graphics2D g2d, GameData gd) {
		if (visible == 1) {
			if (keyHolder == -1) {
				ImageIcon keyImgIcon = new ImageIcon(keyImg);
				g2d.drawImage(keyImgIcon.getImage(), this.getX(), this.getY(),
						null);
			} else {
				ImageIcon keyImgIcon = new ImageIcon(keyImg);
				g2d.drawImage(keyImgIcon.getImage(),
						gd.getTanks()[keyHolder].getX(),
						gd.getTanks()[keyHolder].getY(), null);
			}
		}
	}

	public int getKeySize() {
		return keySize;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}

	public int getKeyHolder() {
		return keyHolder;
	}

	public void setKeyHolder(int keyHolder) {
		this.keyHolder = keyHolder;
	}
}
