package ai;

import game.objects.Tank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import map.Maps;

/**
 * Class to represent the A* search.
 * 
 * @author Bella Dunvoska, Martin Mihov
 * 
 */
public class AStarSearch extends Search {
	private PriorityQueue<Node> agenda; // the nodes to be explored.
	private ArrayList<Node> explored; // the nodes that has been already
										// visited.

	public AStarSearch() {
		// Ensures that the nodes are ordered by heuristics.
		agenda = new PriorityQueue<Node>(1, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getHeuristic() < o2.getHeuristic())
					return -1;
				if (o1.getHeuristic() > o2.getHeuristic())
					return 1;
				return 0;
			}
		});
		explored = new ArrayList<Node>();
	}

	public void restoreTankPoints(ArrayList<Point> points) {
		for (Point p : points) {
			getMap()[p.getX()][p.getY()] = 0;
		}
	}

	@Override
	public Point[] doSearch(Tank[] tanks, int aiId) {
		// Get the node corresponding to the initial position.
		ArrayList<Point> tankPoints = new ArrayList<Point>();
		for (int i = 0; i < tanks.length; i++) {
			if (i != aiId) {
				int x = tanks[i].getX();
				int y = tanks[i].getY();
				int width = tanks[i].getWidth();
				int height = tanks[i].getHeight();
				for (int p = x / Maps.GRID_ENTITY_WIDTH; p < x
						/ Maps.GRID_ENTITY_WIDTH
						+ (width / Maps.GRID_ENTITY_WIDTH); p++) {
					for (int q = y / Maps.GRID_ENTITY_WIDTH; q < y
							/ Maps.GRID_ENTITY_WIDTH
							+ (height / Maps.GRID_ENTITY_WIDTH); q++) {
						if (getMap()[p][q] != 1) {
							tankPoints.add(new Point(p, q));
							getMap()[p][q] = 1;
						}

					}
				}
			}
		}
		Node current = new Node(getInitialPosition(), null);
		generateHeuristic(current.getPoint(), getGoal(), current);

		Point[] check = generatePoints(current.getPoint());

		agenda.add(current);

		if (current.getPoint().equals(getGoal()) || check[0].equals(getGoal())
				|| check[1].equals(getGoal()) || check[2].equals(getGoal())
				|| check[3].equals(getGoal())) {
			restoreTankPoints(tankPoints);
			return nodeToPath(current);
		}
		// while the goal has not been found and there are nodes to be explored.
		while (!agenda.isEmpty()) {
			Node p = agenda.poll();
			// generate the children.
			ArrayList<Node> children = p.getChildren();
			explored.add(p);

			for (Node child : children) {
				Point mapPoint = child.getPoint();

				check = generatePoints(mapPoint);
				// check whether it is the goal.
				if (mapPoint.equals(getGoal()) || check[0].equals(getGoal())
						|| check[1].equals(getGoal())
						|| check[2].equals(getGoal())
						|| check[3].equals(getGoal())) {
					restoreTankPoints(tankPoints);
					return nodeToPath(child);
				}
				if (!agenda.contains(child)
						&& !explored.contains(child)
						&& getMap()[mapPoint.getX() - 2][mapPoint.getY() - 2] != 1
						&& getMap()[mapPoint.getX() + 2][mapPoint.getY() - 2] != 1
						&& getMap()[mapPoint.getX() - 2][mapPoint.getY() + 2] != 1
						&& getMap()[mapPoint.getX() + 2][mapPoint.getY() + 2] != 1) {
					generateHeuristic(mapPoint, getGoal(), child);
					// adds the child to the agenda if it hasn't been visited.
					agenda.add(child);
				}
			}

		}
		restoreTankPoints(tankPoints);
		return null;
	}

	/**
	 * Method to set heuristic to a node.
	 * 
	 * @param current
	 * @param mapPoint
	 * @param toSet
	 */
	private void generateHeuristic(Point current, Point mapPoint, Node toSet) {
		int p2 = current.getX() - mapPoint.getX();
		int q2 = current.getY() - mapPoint.getY();
		toSet.setHeuristic(Math.sqrt(p2 * p2 + q2 * q2));
	}

	/**
	 * Method to generate points to be checked against the goal.
	 * 
	 * @param point
	 * @return
	 */
	private Point[] generatePoints(Point point) {
		Point[] check = new Point[4];
		check[0] = new Point(point.getX() - 2, point.getY() - 2);
		check[1] = new Point(point.getX() + 2, point.getY() - 2);
		check[2] = new Point(point.getX() - 2, point.getY() + 2);
		check[3] = new Point(point.getX() + 2, point.getY() + 2);
		return check;
	}

}
