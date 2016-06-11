package map;

import game.GameData;
import game.objects.FloorTile;
import game.objects.Key;
import game.objects.Missile;
import game.objects.Objective;
import game.objects.Obstacle;
import game.objects.Tank;
import game.objects.floortiles.SandFloor;
import game.objects.obstacles.BaseWallDown;
import game.objects.obstacles.BaseWallLeft;
import game.objects.obstacles.BlueMissileLauncher;
import game.objects.obstacles.EastWall;
import game.objects.obstacles.HeadquartersObs;
import game.objects.obstacles.MapObstacleHorizontal;
import game.objects.obstacles.MapObstacleVertical;
import game.objects.obstacles.NorthWall;
import game.objects.obstacles.RedMissileLauncher;
import game.objects.obstacles.SouthWall;
import game.objects.obstacles.WestWall;

import java.util.ArrayList;

import ai.Point;

/**
 * Class to create a map.
 * 
 * @author Alexandre Portugal, Bella Dunvoska, Martin Mihov,Tendaishe Nyamapfeni
 *
 */
public class Maps {
	private static int mapHeight;
	private static int mapWidth;
	public static final int GRID_ENTITY_WIDTH = 7;

	public static GameData map1(int playerNum) {
		// Size of map 1
		mapHeight = 600;
		mapWidth = 900;

		int remH = mapHeight % 32;
		int remW = mapWidth % 32;

		mapHeight = mapHeight - remH;
		mapWidth = mapWidth - remW;

		// Initialize the GameData
		GameData gameData = new GameData();

		// Initialize essential objects for the Game Panel
		// /////Tanks/////
		Tank[] d = new Tank[playerNum];

		Tank b = new Tank(mapWidth - 100, 50, 0);
		Tank c = new Tank(120, mapHeight - 30, 1);
		b.setTeamId(-1);
		c.setTeamId(-1);
		d[0] = b;
		d[1] = c;

		if (playerNum == 4) {
			Tank b1 = new Tank(mapWidth - 50, 50, 2);
			Tank c1 = new Tank(80, mapHeight - 30, 3);
			b1.setTeamId(-1);
			c1.setTeamId(-1);
			d[2] = b1;
			d[3] = c1;

		}
		// Set all ID's to -1 here so that they are unallocated!

		// The objective is assumed to be right next to the brown obstacle.
		Objective objective = new Objective((mapWidth / 2) - 50,
				(mapHeight / 2) - 50, 32, 32);

		// /////Obstacles///////
		Obstacle f = new Obstacle(500, 80, 80, 50);
		Obstacle g = new Obstacle(850, 270, 50, 150);

		// Top Base
		Obstacle sizeB = new BaseWallDown(0, 0); // vertical
		Obstacle sizeA = new BaseWallLeft(0, 0); // horizontal

		Obstacle topRightBaseLeftWall = new BaseWallDown(mapWidth - 32
				- sizeA.getWidth() - 50, 32); // vertical
		Obstacle topRightBaseBotWall = new BaseWallLeft(mapWidth
				- sizeA.getWidth(), 140); // horizontal

		// Bottom Base
		Obstacle botLeftBaseRightWall = new BaseWallDown(190, mapHeight
				- topRightBaseLeftWall.getHeight()); // vertical

		Obstacle botLeftBaseTopWall = new BaseWallLeft(31, mapHeight
				- topRightBaseLeftWall.getHeight() - 70); // horizontal
		
		// Missile Launchers
		Obstacle missileLauncherTopRight = new BlueMissileLauncher(
				objective.getX() + objective.getWidth(), objective.getY());

		missileLauncherTopRight = new BlueMissileLauncher(objective.getX()
				+ objective.getWidth(), objective.getY()
				- missileLauncherTopRight.getHeight());

		Obstacle missileLauncherBotLeft = new RedMissileLauncher(
				objective.getX() - missileLauncherTopRight.getWidth(),
				objective.getY() + objective.getHeight());
		
		// Team Headquarters
		Obstacle redHeadquarters = new HeadquartersObs(mapWidth - 64 + 16, 0, 0);
		Obstacle blueHeadquarters = new HeadquartersObs(0, mapHeight - 64 + 10, 1);
		
		//Map Obstacles
			//Horizontal map obstacles
		Obstacle mapObstacle1 = new MapObstacleHorizontal(100, 90);
		Obstacle mapObstacle2 = new MapObstacleHorizontal(420, 120);
		Obstacle mapObstacle3 = new MapObstacleHorizontal(700, 230);
		Obstacle mapObstacle4 = new MapObstacleHorizontal(700, 490);
		Obstacle mapObstacle5 = new MapObstacleHorizontal(400, 460);
		Obstacle mapObstacle6 = new MapObstacleHorizontal(100, 350);
			//Vertical map obstacles
		Obstacle mapObstacle7 = new MapObstacleVertical(150, 175);
		Obstacle mapObstacle8 = new MapObstacleVertical(305, 90);
		Obstacle mapObstacle9 = new MapObstacleVertical(750, 315);
		Obstacle mapObstacle10 = new MapObstacleVertical(600, 400);

		// Creates the bounds of the map
		Obstacle[] bounds = createBorders(mapHeight, mapWidth);
		Obstacle[] h = new Obstacle[18 + bounds.length];
		h[h.length - 18] = mapObstacle10;
		h[h.length - 17] = mapObstacle9;
		h[h.length - 16] = mapObstacle8;
		h[h.length - 15] = mapObstacle7;
		h[h.length - 14] = mapObstacle6;
		h[h.length - 13] = mapObstacle5;
		h[h.length - 12] = mapObstacle4;
		h[h.length - 11] = mapObstacle3;
		h[h.length - 10] = mapObstacle2;
		h[h.length - 9] = mapObstacle1;
		h[h.length - 8] = topRightBaseLeftWall;
		h[h.length - 7] = botLeftBaseRightWall;
		h[h.length - 6] = topRightBaseBotWall;
		h[h.length - 5] = botLeftBaseTopWall;
		h[h.length - 4] = missileLauncherTopRight;
		h[h.length - 3] = missileLauncherBotLeft;
		h[h.length - 2] = redHeadquarters;
		h[h.length - 1] = blueHeadquarters;

		for (int z = 0; z < bounds.length; z++) {
			h[z] = bounds[z];
			// System.out.println(h[z].getX() + "   " + h[z].getY());
		}
		// ///Key////
		Key key = new Key(80, 80, 1);

		// Add objects to the GameData
		gameData.setTanks(d);
		gameData.setObstacles(h);
		gameData.setKey(key);
		gameData.setObjective(objective);

		/*
		 * Obstacle missileLauncherTopRight = new
		 * RedMissileLauncher(objective.getX
		 * ()+objective.getWidth(),objective.getY()); Obstacle
		 * missileLauncherBotLeft = new
		 * RedMissileLauncher(objective.getX()-missileLauncherTopRight
		 * .getWidth(),objective.getY()+objective.getHeight());
		 */
		gameData.theRedMiss = new Missile(objective.getX()
				- missileLauncherTopRight.getWidth(), objective.getY()
				+ objective.getHeight(),
				(blueHeadquarters.getX() + blueHeadquarters.getWidth() / 2),
				(blueHeadquarters.getY() + blueHeadquarters.getHeight() / 2)-23,0);
		gameData.theBlueMiss = new Missile(objective.getX()
				+ objective.getWidth(), objective.getY()
				- missileLauncherTopRight.getHeight(),
				(redHeadquarters.getX() + redHeadquarters.getWidth() / 2),
				(redHeadquarters.getY() + redHeadquarters.getHeight() / 2)-23, 1);

		gameData.setmWidth(mapWidth + 16);
		gameData.setmHeight(mapHeight + 16);

		gameData.setFloorT(generateFloor(mapWidth, mapHeight));
		return gameData;
	}

	public static int[][] createArrayFromMap(GameData gd) {
		int width = mapWidth / GRID_ENTITY_WIDTH;
		if (mapWidth % GRID_ENTITY_WIDTH > 0) {
			width++;

		}
		width++;
		int height = mapHeight / GRID_ENTITY_WIDTH;
		if (mapHeight % GRID_ENTITY_WIDTH > 0) {
			height++;
		}
		height++;

		int[][] mapArray = new int[width][height];
		Obstacle[] obstacles = gd.getObstacles();
		for (Obstacle obs : obstacles) {
			int xObstacle = obs.getX();
			int yObstacle = obs.getY();
			int heightObstacle = obs.getHeight();
			int widthObstacle = obs.getWidth();

			int obstacleWidthEnd = xObstacle + widthObstacle;
			int arrayWidthEnd = obstacleWidthEnd / GRID_ENTITY_WIDTH;
			if (obstacleWidthEnd % GRID_ENTITY_WIDTH > 0) {
				arrayWidthEnd++;
			}

			int obstacleHeightEnd = yObstacle + heightObstacle;
			int arrayHeightEnd = obstacleHeightEnd / GRID_ENTITY_WIDTH;
			if (obstacleHeightEnd % GRID_ENTITY_WIDTH > 0) {
				arrayHeightEnd++;
			}

			for (int i = xObstacle / GRID_ENTITY_WIDTH; i < arrayWidthEnd
					&& i < width; i++) {
				for (int j = yObstacle / GRID_ENTITY_WIDTH; j < arrayHeightEnd
						&& j < height; j++) {
					mapArray[i][j] = 1;
				}
			}
		}
		return mapArray;

	}

	public static Point getPosition(int tankId, int teamId) {

		if (tankId == 0 && teamId == 0) {
			return new Point(mapWidth - 100, 50);

		} else if (tankId == 0 && teamId == 1) {
			return new Point(120, mapHeight - 30);
		} else if (tankId == 1 && teamId == 0) {
			return new Point(mapWidth - 50, 100);
		} else if (tankId == 1 && teamId == 1) {
			return new Point(80, mapHeight - 100);
		}
		return null;

	}

	private static Obstacle[] createBorders(int h, int w) {
		NorthWall a = new NorthWall(0, 0);
		// System.out.println(a.getHeight() + " " + a.getWidth());

		int b = (int) Math.ceil(h / a.getHeight());
		int c = (int) Math.ceil(w / a.getWidth()) + 1;

		// System.out.println(b + "  " + c);
		ArrayList<Obstacle> f = new ArrayList<Obstacle>();

		// South
		for (int i = 0; i < c; i++) {
			int p = i * a.getWidth();
			SouthWall wall = new SouthWall(p, h);
			// System.out.println(p + "   " + h);
			f.add(wall);
		}

		// West
		for (int i = 0; i < b; i++) {
			int p = i * a.getHeight();
			WestWall wall = new WestWall(0, p);
			// System.out.println(w + "   " + p);
			f.add(wall);
		}

		// North
		for (int i = 0; i < c; i++) {
			int p = i * a.getWidth();
			NorthWall wall = new NorthWall(p, 0);
			// System.out.println(p + "   " + 0);
			f.add(wall);
		}

		// East
		for (int i = 0; i < b; i++) {
			int p = i * a.getHeight();
			EastWall wall = new EastWall(w, p);
			// System.out.println(w + "   " + p);
			f.add(wall);
		}

		/*
		 * NortWest corner (0,0) NorthEast corner (w - widthofcornerimage,0)
		 * SouthWest corner (0,h-heightofcornerimage) SouthEasy corner (w -
		 * widthofcornerimage,h-heightofcornerimage)
		 */

		Obstacle[] toReturn = new Obstacle[f.size()];

		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = f.get(i);
		}

		return (toReturn);

	}

	public static FloorTile[] generateFloor(int w, int h) {
		ArrayList<SandFloor> aList = new ArrayList<SandFloor>();

		SandFloor a = new SandFloor(0, 0);
		int fw = a.getWidth();
		int fh = a.getHeight();

		int nWidth = w / fw;
		int nHeight = h / fh;

		for (int i = 0; i < nHeight; i++) {
			for (int j = 0; j < nWidth; j++) {
				SandFloor b = new SandFloor(j * fw, i * fh);
				aList.add(b);
			}

		}

		SandFloor[] toReturn = new SandFloor[aList.size()];

		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = aList.get(i);
		}

		return toReturn;

	}
}
