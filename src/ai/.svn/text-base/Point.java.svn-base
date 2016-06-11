package ai;

import java.util.ArrayList;

/**
 * Class to represent a Point from the map.
 * 
 * @author Bella Dunvoska, Martin Mihov
 * 
 */
public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Method to get x.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method to set x.
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Method to get y.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Method to set y.
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Method to generate children of the current point.
	 * 
	 * @return
	 */
	public ArrayList<Point> getChildren() {
		ArrayList<Point> children = new ArrayList<Point>();

		children.add(new Point(x + 1, y));
		children.add(new Point(x - 1, y));
		children.add(new Point(x, y + 1));
		children.add(new Point(x, y - 1));
		children.add(new Point(x - 1, y - 1));
		children.add(new Point(x + 1, y + 1));
		children.add(new Point(x - 1, y + 1));
		children.add(new Point(x + 1, y - 1));
		return children;

	}

	@Override
	public boolean equals(Object obj) {
		Point p = (Point) obj;
		if (this.x == p.x && this.y == p.y) {
			return true;
		}
		return false;
	}
}
