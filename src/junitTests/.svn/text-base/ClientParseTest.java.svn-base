package junitTests;

import static org.junit.Assert.*;
import game.objects.Projectile;

import java.util.ArrayList;

import networking.Client2Server;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author Sam
 *
 */
public class ClientParseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}	
		Client2Server parser = new Client2Server(null);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCode() {
		ArrayList<Projectile> newProjectiles = new ArrayList<Projectile>();
		
		//sets up an array list containing two projectiles which must be coded before being sent
		newProjectiles.add(new Projectile(20, 30, 60, 32, 0));
		newProjectiles.add(new Projectile(50, 100, 90, 15, 0));
		System.out.println(parser.code("TP", 0, 20, 30, 45, 45, newProjectiles));
		
		assertEquals("Code Result and pre-defined message", "T#0#20#30#45.0#45.0!!P#20#30#60#32#0!!P#50#100#90#15#0!!", parser.code("[T][P]", 0, 20, 30, 45, 45, newProjectiles));
	}

}
