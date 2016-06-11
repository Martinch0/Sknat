package ai;

import java.util.ArrayList;

/**
 * Class to represent a Node consisting of Point and a parent node for the
 * search.
 * 
 * @author Bella Dunvoska, Martin Mihov
 * 
 */
public class Node {
	private Point point;
	private Node parent;
	private double heuristic;

	public Node(Point point, Node parent) {
		this.setPoint(point);
		this.setParent(parent);
	}

	/**
	 * Gets the point of the node
	 * 
	 * @return
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Sets the point of the node.
	 * 
	 * @param point
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

	/**
	 * Gets the parent of the node.
	 * 
	 * @return
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets the parent of the node.
	 * 
	 * @param parent
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * Checks the quality of two nodes in terms of their points' parameters.
	 */
	@Override
	public boolean equals(Object obj) {
		Node n = (Node) obj;
		if (this.point.getX() == n.point.getX()
				&& this.point.getY() == n.point.getY()) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the children of the current node.
	 * 
	 * @return
	 */
	public ArrayList<Node> getChildren() {
		ArrayList<Point> points = this.point.getChildren();
		ArrayList<Node> children = new ArrayList<Node>();
		for (Point p : points) {
			children.add(new Node(p, this));
		}
		return children;

	}

	/**
	 * Gets the heuristic for this node.
	 * 
	 * @return
	 */
	public double getHeuristic() {
		return heuristic;
	}

	/**
	 * Sets the heuristic for this node.
	 * 
	 * @param heuristic
	 */
	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}

}
