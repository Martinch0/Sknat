package junitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import game.objects.Projectile;
import game.objects.Tank;

import java.util.ArrayList;

import networking.Server2Client;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author Sam
 *
 */
public class ServerParseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
		Server2Client parser = new Server2Client(null);
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
		Tank t1 =  new Tank(1,2,0);
		t1.setBotAngle(45);
		t1.setTopAngle(90);
		Tank t2 = new Tank(50, 100, 1);
		t2.setBotAngle(90);
		t2.setTopAngle(90);
		Tank[] tankArray= new Tank[2];
		tankArray[0]= t1;
		tankArray[1]= t2;
		
		
		ArrayList<Projectile> newProjectiles = new ArrayList<Projectile>();
		
		
		//sets up an array list containing two projectiles which must be coded before being sent
		newProjectiles.add(new Projectile(20, 30, 60, 32, 0));
		newProjectiles.add(new Projectile(50, 100, 90, 15, 0));

//		assertEquals("Code Result and pre-defined message", "T#0#1#2#45.0#90.0!!T#1#50#100#90.0#90.0!!P#20#30#60#32#0!!P#50#100#90#15#0!!", parser.code(null,tankArray , newProjectiles));
	
		
		
	}

}
