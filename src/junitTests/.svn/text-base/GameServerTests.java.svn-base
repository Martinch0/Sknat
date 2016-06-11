package junitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import game.objects.Key;
import game.objects.Projectile;
import game.objects.Tank;

import java.io.IOException;
import java.util.ArrayList;

import menu.MainMenu;

import org.junit.BeforeClass;
import org.junit.Test;

import client.GameClient;

import server.GameServer;
import server.GameServer.NetworkingThread;

/**
 * JUnit Test Case for testing the updates performed in the Game Server.
 * 
 * @author TeamA5
 * 
 */
public class GameServerTests {

	private static GameServer gameServer;
	private static GameClient firstClient;
	private static GameClient secondClient;

	/**
	 * Create the server before conducting all the tests.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gameServer = new GameServer(2, "1vs1");

	}

	/**
	 * Method to test whether the position of a tank has been made correctly.
	 */
	@Test
	public void testUpdateTank() {
		gameServer.updateTank(0, 30, 10, 1.5, 30.8);
		Tank tank = gameServer.getGameData().getTanks()[0];
		assertEquals(tank.getX(), 30);
		assertEquals(tank.getY(), 10);
		assertEquals(tank.getBotAngle(), 1.5, 0.01);
		assertEquals(tank.getTopAngle(), 30.8, 0.01);
	}

	/**
	 * Method to test whether a projectile has been added correctly.
	 */
	@Test
	public void testAddProjectile() {
		gameServer.addProjectile(30, 40, 80, 90, 1);
		Projectile p = gameServer.getGameData().getProjectiles().get(0);
		assertEquals(p.getX(), 30);
		assertEquals(p.getY(), 40);
		assertEquals(p.getxDir(), 80);
		assertEquals(p.getyDir(), 90);
		assertEquals(p.getTeam(), 1);
	}

	/**
	 * Method to test whether a hitting of a tank has been correctly performed.
	 */
	@Test
	public void testCheckForHits() {
		Tank[] tanks = new Tank[1];
		Tank t = new Tank(30, 30, 1);
		tanks[0] = t;
		gameServer.getGameData().setTanks(tanks);

		Projectile p1 = new Projectile(30, 31, 1, 1, 0);
		Projectile p2 = new Projectile(30, 101, 1, 1, 0);
		Projectile p3 = new Projectile(300, 31, 1, 1, 0);

		ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
		projectiles.add(p1);
		projectiles.add(p2);
		projectiles.add(p3);

		gameServer.getGameData().setProjectiles(projectiles);

		int size = gameServer.getGameData().getProjectiles().size();

		NetworkingThread nt = gameServer.new NetworkingThread();
		nt.checkForHits();
		assertEquals(size, gameServer.getGameData().getProjectiles().size());

	}

	/**
	 * Method to test whether a projectile is moving correctly.
	 */
	@Test
	public void testMoveProjectiles() {
		Projectile p1 = new Projectile(30, 31, 1, 1, 0);
		Projectile p2 = new Projectile(30, 101, 1, 1, 0);
		Projectile p3 = new Projectile(300, 31, 1, 1, 0);

		ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
		projectiles.add(p1);
		projectiles.add(p2);
		projectiles.add(p3);

		gameServer.getGameData().setProjectiles(projectiles);

		NetworkingThread nt = gameServer.new NetworkingThread();
		nt.moveProjectile();
		assertEquals((int) (30 + p1.getXIncrement()), p1.getX());
		assertEquals((int) (30 + p2.getXIncrement()), p2.getX());
		assertEquals((int) (300 + p3.getXIncrement()), p3.getX());
		assertEquals((int) (31 + p1.getYIncrement()), p1.getY());
		assertEquals((int) (101 + p2.getYIncrement()), p2.getY());
		assertEquals((int) (31 + p3.getYIncrement()), p3.getY());
	}

	/**
	 * Method to test whether the server is assigning a key holder correctly.
	 */
	@Test
	public void testCkeckForKey() {
		Tank[] tanks = new Tank[1];
		Tank t = new Tank(30, 30, 0);
		tanks[0] = t;

		gameServer.getGameData().setTanks(tanks);

		Key k = new Key(30, 30, 1);
		gameServer.getGameData().setKey(k);

		NetworkingThread nt = gameServer.new NetworkingThread();
		nt.checkForKey();

		assertEquals(t.getId(), gameServer.getGameData().getKeyHolder());

	}

}
