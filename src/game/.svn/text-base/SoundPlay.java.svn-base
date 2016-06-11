package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class responsible for playing sound.
 * 
 * @author Alexandre Portugal
 *
 */
public class SoundPlay {

	// Shooting sounds
	private String bulletFireSound = "src/resources/tankshoot.au";
	private static String explosionSound = "src/resources/explosion.au";
	private Clip tankFire;
	private Clip explClip;

	/**
	 * Initializes all the sounds
	 */
	public SoundPlay() {

		try {
			tankFire = AudioSystem.getClip();
			tankFire.open(AudioSystem.getAudioInputStream(new File(bulletFireSound)));
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
//		try {
//			explClip = AudioSystem.getClip();
//			explClip.open(AudioSystem.getAudioInputStream(new File(explosionSound)));
//		} catch (LineUnavailableException | IOException
//				| UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Plays a new bullet sound in a new thread
	 */
	public synchronized void playTankBulletSound() {
		new Thread(new Runnable() {
			public void run() {
				try {

					if(explClip.isRunning())
					{
						Clip newTankFire = AudioSystem.getClip();
						newTankFire.open(AudioSystem.getAudioInputStream(new File(bulletFireSound)));
						newTankFire.start();
					}
					else
					{
						tankFire.setFramePosition(0);
						tankFire.start();
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	/**
	 * Plays a new bullet sound in a new thread
	 */
	public static void playExplosionSound() {
		new Thread(new Runnable() {
			public void run() {
				try {

						Clip newExplFire = AudioSystem.getClip();
						newExplFire.open(AudioSystem.getAudioInputStream(new File(explosionSound)));
						newExplFire.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
}
