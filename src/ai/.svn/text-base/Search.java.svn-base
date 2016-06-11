package ai;

import game.objects.Tank;

import java.util.Stack;

/**
 * Abstract class to wrap-up the Search functionality.
 * 
 * @author Bella Dunvoska, Martin Mihov
 * 
 */
public abstract class Search {

	private Point goal;
	private Point initialPosition;
	private int[][] map;

	/**
	 * Abstract method to perform the search.
	 * 
	 * @return the shorthest path to be followed.
	 */
	public abstract Point[] doSearch(Tank[] tanks, int aiId);

	/**
	 * Gets the goal.
	 * 
	 * @return
	 */
	public Point getGoal() {
		return goal;
	}

	/**
	 * Sets the goal.
	 * 
	 * @param goal
	 */
	public void setGoal(Point goal) {
		this.goal = goal;
	}

	/**
	 * Gets the initial position.
	 * 
	 * @return
	 */
	public Point getInitialPosition() {
		return initialPosition;
	}

	/**
	 * Sets the initial position.
	 * 
	 * @param initialPosition
	 */
	public void setInitialPosition(Point initialPosition) {
		this.initialPosition = initialPosition;
	}

	/**
	 * Gets the map.
	 * 
	 * @return
	 */
	public int[][] getMap() {
		return map;
	}

	/**
	 * Sets the map.
	 * 
	 * @param map
	 */
	public void setMap(int[][] map) {
		this.map = map;
	}

	/**
	 * Given a goal node calculates a path of points leading to it.
	 * 
	 * @param n
	 *            the goal node.
	 * @return the shortest path of points leading to the goal node.
	 */
	public Point[] nodeToPath(Node n) {
		Stack<Point> path = new Stack<Point>();
		path.add(n.getPoint());
		while (n.getParent() != null) {
			n = n.getParent();
			path.add(n.getPoint());
		}
		int size = path.size();
		Point[] pointPath = new Point[size];
		for (int i = 0; i < size; i++) {
			pointPath[i] = path.pop();
		}

		return pointPath;

	}
}
