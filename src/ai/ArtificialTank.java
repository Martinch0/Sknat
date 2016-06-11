package ai;

import game.GamePanel;
import game.objects.Key;
import game.objects.Objective;
import game.objects.Tank;

import java.util.Random;

import map.Maps;
import client.GameClient;

/**
 * Implementation of Artificial Intelligence for the tanks.
 * 
 * @author Bella Dunvoska, Martin Mihov
 * 
 */
public class ArtificialTank {
	private GameClient client;
	private GamePanel panel;

	/**
	 * Constructor.
	 * 
	 * @param client
	 * @param panel
	 */
	public ArtificialTank(GameClient client, GamePanel panel) {
		this.client = client;
		this.panel = panel;
	}

	/**
	 * Method to compare the coordinates of the aitank against the coordinates
	 * of different object.
	 * 
	 * @param aitank
	 * @param x
	 * @param y
	 */
	public boolean movementControl(Tank aitank, int x, int y) {
		if (aitank.getHealth() > 0) {

			int tankX = (aitank.getX() + aitank.getWidth() / 2)
					/ Maps.GRID_ENTITY_WIDTH;
			int tankY = (aitank.getY() + aitank.getHeight() / 2)
					/ Maps.GRID_ENTITY_WIDTH;

			// Calculates the direction of movement according to coordinates to
			// be
			// followed.
			if (tankX < x) {
				aitank.moveRight(panel.gameData.getTanks(),
						panel.gameData.getObstacles());
			} else if (tankX > x) {
				aitank.moveLeft(panel.gameData.getTanks(),
						panel.gameData.getObstacles());
			}
			if (tankY > y)
				aitank.moveUp(panel.gameData.getTanks(),
						panel.gameData.getObstacles());
			else if (tankY < y) {
				aitank.moveDown(panel.gameData.getTanks(),
						panel.gameData.getObstacles());

			}

			if (tankX >= x && tankX <= x && tankY <= y && tankY >= y) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Method to move the tank towards its goal, following the path specified by
	 * the search.
	 * 
	 * @param aitank
	 */
	public void followPath(Tank aitank) {
		while (true) {
			Point[] path = updatePath();
			if (path != null && path.length > 1) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Point current = path[1];
				int x = current.getX();
				int y = current.getY();
				movementControl(aitank, x, y);
			} else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Method to calculate the path to be followed by the aitank.
	 * 
	 * @return
	 */
	public Point[] updatePath() {
		Tank[] tanks = panel.gameData.getTanks();
		Tank aitank = tanks[client.getID()];
		Key key = panel.gameData.getKey();
		Objective objective = panel.gameData.getObjective();
		Search bfs = new AStarSearch();

		// Goal point to be followed.
		Point goal;
		if (panel.gameData.getKeyHolder() == -1) {
			// If the key has not been picked up it is the goal.
			goal = new Point(key.getX() / Maps.GRID_ENTITY_WIDTH, key.getY()
					/ Maps.GRID_ENTITY_WIDTH);

		} else if (aitank.getId() == panel.gameData.getKeyHolder()) {
			// If the AI tank has picked up the key, the objective is the goal.
			goal = new Point((objective.getX() + objective.getWidth() / 2)
					/ Maps.GRID_ENTITY_WIDTH,
					(objective.getY() + objective.getHeight() / 2)
							/ Maps.GRID_ENTITY_WIDTH);
		} else {
			// If another tank is holding the key, the AI should follow it.
			int keyholder = panel.gameData.getKeyHolder();
			Tank keyHolderTank = tanks[keyholder];
			goal = new Point(keyHolderTank.getX() / Maps.GRID_ENTITY_WIDTH,
					keyHolderTank.getY() / Maps.GRID_ENTITY_WIDTH);
		}

		// Setting the goal, the initial position and the map for the search.
		bfs.setGoal(goal);
		bfs.setInitialPosition(new Point(
				(aitank.getX() + aitank.getWidth() / 2)
						/ Maps.GRID_ENTITY_WIDTH, (aitank.getY() + aitank
						.getHeight() / 2) / Maps.GRID_ENTITY_WIDTH));
		bfs.setMap(Maps.createArrayFromMap(panel.gameData));

		Point[] result = bfs.doSearch(panel.gameData.getTanks(), aitank.getId());
		return result;
	}

	/**
	 * Method to send projectiles towards enemy tanks.
	 * 
	 * @param aitank
	 */
	private void shoot(Tank aitank) {
		Tank[] tanks = panel.gameData.getTanks();
		for (int i = 0; i < tanks.length; i++) {
			if (aitank.getTeamId() != tanks[i].getTeamId()) {

				int x = tanks[i].getX() - aitank.getX();
				int y = tanks[i].getY() - aitank.getY();
				int distance = (int) Math.sqrt(x * x + y * y);
				// checks whether the tank from the opposite team is close
				// enough to be
				// shot.
				if (distance < 400) {
					aitank.calcTopAngle(tanks[i].getX() + tanks[i].getWidth()
							/ 2, tanks[i].getY() + tanks[i].getHeight() / 2);

					panel.newBulletAI(tanks[i].getX() + tanks[i].getWidth() / 2,
							tanks[i].getY() + tanks[i].getHeight() / 2);

				}
			}
		}

	}

	/**
	 * Method to initialise a thread for shooting against opposite team.
	 * 
	 * @param aitank
	 */
	public void startShooting(final Tank aitank) {
		Thread shooting = new Thread(new Runnable() {

			@Override
			public void run() {
				Random r = new Random();
				while (true) {
					shoot(aitank);
					try {
						Thread.sleep(r.nextInt(1300) + 100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		shooting.start();
	}

	/**
	 * Method to start the ai.
	 */
	public void startAi() {
		Thread startAiClient = new Thread(new Runnable() {

			@Override
			public void run() {
				Tank[] tanks = panel.gameData.getTanks();
				Tank aitank = tanks[client.getID()];
				startShooting(aitank);

				followPath(aitank);
			}
		});
		startAiClient.start();
	}
}
