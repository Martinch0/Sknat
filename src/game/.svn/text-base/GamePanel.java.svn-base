package game;

import game.objects.Projectile;
import game.objects.Tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

import menu.MainMenu;

/**
 * Panel where the game is drawn to. Also captures and processes user input.
 * 
 * @author Alexandre Portugal,Tendaishe Nyamapfeni
 * 
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener,
		MouseListener {

	public int wPressed = 0;
	public int aPressed = 0;
	public int sPressed = 0;
	public int dPressed = 0;

	public JFrame owner = null;

	// Game data being drawn
	public GameData gameData;

	// FPS Wanted
	private int wantedFps = 30;

	// Delay in frame drawing
	public int delay = 80;

	// Transposed ammount
	int tx = 0;
	int ty = 0;

	// Furthresest right block
	int furthX = 0;

	// Furthresest bot block
	int furthY = 0;

	// height of the info bar
	int by = 50;

	// Sacle factor for drawing
	public double sf = 1;

	// Game is running
	int gameRunning = 1;

	// Client tank's ID
	private int myID;

	private SoundPlay sounds;
	private MainMenu mm;

	public GamePanel(int myID, final MainMenu mainMenu) {
		super();
		this.myID = myID;
		this.requestFocus();

		sounds = new SoundPlay();

		this.addKeyListener(this);
		this.addMouseListener(this);
		mm = mainMenu;

	}

	public GamePanel(int myID, boolean ai, final MainMenu mainMenu) {
		super();
		this.myID = myID;
		mm = mainMenu;

	}

	/**
	 * Starts the listener and painting threads and makes the panel visible
	 */
	public void startGamePan() {

		gameData.setMyTeam(gameData.getTanks()[myID].getTeamId());

		furthX = findFarRight();
		furthY = findFarDown();

		this.requestFocus();

		// Runs Movement Control
		new Thread() {
			public void run() {
				movementControl();
			}
		}.start();

		// Draw the game
		new Thread() {
			public void run() {
				// paintEachFrame();
				paintForSpecificFps();
			}

		}.start();

		this.setVisible(true);
	}

	/**
	 * Closes this window and opens the victory/loss thing
	 * 
	 * @param win
	 *            Weather the team won or not 1 = win, 0 = loss
	 */
	public void endGamePanel(int win) {

		if (win == 1) { // Victory
			// mm.showEndGamePanel(1);
			gameRunning = 0;
		} else if (win == 0) { // Defeat
			// mm.showEndGamePanel(0);
			gameRunning = 0;
		} else { // Draw
			// mm.showEndGamePanel(-1);
			gameRunning = 0;
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// updates keys pressed
		updateKeysPressed(e);

	}

	private void updateKeysPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == 65)
			aPressed = 1;
		if (key == 68)
			dPressed = 1;
		if (key == 83)
			sPressed = 1;
		if (key == 87)
			wPressed = 1;

		calculateAngleOfShape();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		updateKeysReleased(e);
	}

	private void updateKeysReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == 65)
			aPressed = 0;
		if (key == 68)
			dPressed = 0;
		if (key == 83)
			sPressed = 0;
		if (key == 87)
			wPressed = 0;

		calculateAngleOfShape();

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * Threads that draws each frame
	 */
	private void paintEachFrame() {
		long startingTime = System.nanoTime();
		long lastTime = startingTime;
		long timePassed = 0;
		int fpsDone = 0;
		while (gameRunning == 1) {

			timePassed = timePassed + System.nanoTime() - lastTime;
			lastTime = System.nanoTime();

			repaint();
			fpsDone++;

			if (timePassed >= 1000000000) {
				// System.out.println("The fps " + fpsDone);
				startingTime = System.nanoTime();
				fpsDone = 0;
				timePassed = 0;
				lastTime = startingTime;

			}

			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Draws specified number of frames every second. Compensates for the amount
	 * of time each frame to be rendered and drawn.
	 */
	private void paintForSpecificFps() {
		long startingTime = System.nanoTime();
		long lastTime = startingTime;
		long timePassed = 0;
		int fpsDone = 0;

		while (gameRunning == 1) {

			timePassed = timePassed + System.nanoTime() - lastTime;
			lastTime = System.nanoTime();

			if (fpsDone < getWantedFps()) {
				repaint();
			}
			fpsDone++;

			if (timePassed >= 1000000000) {
				startingTime = System.nanoTime();
				fpsDone = 0;
				timePassed = 0;
				lastTime = startingTime;

			}

			try {
				int expectedDelay = 1000 / getWantedFps();
				int realDelay = expectedDelay;
				if (((timePassed / expectedDelay) + (getWantedFps() / 10)) < fpsDone) {
					int toDoFps = getWantedFps() - fpsDone;
					long delayNeeded = ((1000000000 - timePassed) / 1000000)
							/ toDoFps;
					realDelay = (int) (delayNeeded - (delayNeeded / 10));
				}

				Thread.sleep(realDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {

		super.paint(g);
		// this.setLocation(300, 0);

		Graphics2D g2d = (Graphics2D) g;

		// Map size
		double mx = furthX;
		double my = furthY;
		// System.out.println(my);

		// Size of top bar
		int bx = furthX;

		// frame's frame size
		Insets theInset = owner.getInsets();
		int topInset = theInset.top;
		int botInset = theInset.bottom;
		int leftInset = theInset.left;
		int rightInset = theInset.right;

		// This frames size
		Rectangle r = owner.getBounds();
		int py = r.height - topInset - botInset;
		int px = r.width - rightInset - leftInset;

		// Scaling
		double sx = px / mx;
		double sy = py / (my + by);

		// If sx is bigger, then tx will not be 0

		if (sx > sy) {
			sx = sy;
			double auxX = px - (mx * sy);
			tx = (int) Math.ceil(auxX / 2);
			// System.out.println(px + " " + auxX + " " + (mx*sy));
			ty = 0;
		} else {
			sy = sx;
			double auxY = py - ((my + by) * sx);
			ty = (int) Math.ceil(auxY / 2);
			tx = 0;
		}

		sf = sy;

		AffineTransform oldXForm = g2d.getTransform();

		g2d.translate(tx, ty);
		g2d.scale(sf, sf);

		drawInfoBar(g2d, bx, by);

		g2d.setTransform(oldXForm);

		g2d.translate(tx, ty + (by * sf));
		g2d.scale(sf, sf);

		// Calls the paint methods
		gameData.updateDisplay(g2d, myID);

	}

	private void drawInfoBar(Graphics2D g2d, int bx, int by) {
		// System.out.println(gameData.getKeyHolder());

		// Draws outline of the box
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, bx, by);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 42));

		// Color being used
		Color orange = new Color(0xe69138);
		Color darkBlue = new Color(0x1189ae);
		Color darkRed = new Color(0xd4002e);
		Color lightBlue = new Color(0x3decff);

		// Initiates font metrics
		FontMetrics metric = g2d.getFontMetrics();
		int heightFont = metric.getDescent() + 5;

		// Width of the box
		double a = bx;

		// Boxes size
		int smallBoxSize = (int) (a / 12);
		int centerBoxSize = (int) (2 * a / 12);

		// Draws the first part of the info bar
		int x1 = (int) (a / 3);
		g2d.setColor(Color.black);
		g2d.fillRect(x1, 0, x1, by);

		// Outlines of boxes
		int x0 = 0;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, x1, by);

		// Draws the third part
		int x2 = (int) (2 * a / 3);
		g2d.setColor(Color.black);
		g2d.fillRect(x2, 0, x1, by);

		// draws the middle section's first part
		int x1p0 = (int) (4 * a / 12);
		g2d.setColor(Color.black);
		g2d.fillRect(x1p0, 0, smallBoxSize, by);
		g2d.setColor(orange);
		g2d.drawRect(x1p0, 0, smallBoxSize, by);

		// Drawing the center Box (which has the timer)
		int x1p1 = (int) (5 * a / 12);
		g2d.setColor(orange);
		g2d.fillRect(x1p1, 0, centerBoxSize + 2, by);

		int x1p2 = (int) (7 * a / 12);

		g2d.setColor(Color.black);
		g2d.fillRect(x1p2, 0, smallBoxSize, by);

		g2d.setColor(orange);
		g2d.drawRect(x1p2, 0, smallBoxSize, by);

		int finalX, x;

		// TEAMS TANKS SCORES
		g2d.setColor(lightBlue);
		int team1 = gameData.getTeamScores()[1];
		String team1s = team1 + "";
		x = (smallBoxSize - metric.stringWidth(team1s)) / 2;
		finalX = x1 + x;
		g2d.drawString(team1s, finalX, (by / 2) + heightFont);

		g2d.setColor(darkRed);
		int team2 = gameData.getTeamScores()[0];
		String team2s = team2 + "";
		x = (smallBoxSize - metric.stringWidth(team2s)) / 2;
		finalX = x1 + x + centerBoxSize + smallBoxSize;
		g2d.drawString(team2s, finalX, (by / 2) + heightFont);

		// Draws the score
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		String timeLeft = "Time Left:";
		metric = g2d.getFontMetrics();
		heightFont = metric.getDescent();

		g2d.setColor(Color.BLACK);
		x = (x1 - metric.stringWidth(timeLeft)) / 2;
		finalX = x + x1;
		g2d.drawString(timeLeft, finalX, (by / 2) - 5 - 2);

		// Draws the score
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 34));
		metric = g2d.getFontMetrics();
		heightFont = metric.getDescent() + 15;

		if (gameData.getTeamScores()[1] > gameData.getTeamScores()[0]) {
			g2d.setColor(darkBlue);
		} else if (gameData.getTeamScores()[1] < gameData.getTeamScores()[0]) {
			g2d.setColor(darkRed);
		} else {
			g2d.setColor(Color.BLACK);
		}
		x = (x1 - metric.stringWidth(gameData.getTimer())) / 2;
		finalX = x + x1;
		g2d.drawString(gameData.getTimer(), finalX, (by / 2) + heightFont - 2);

		// System.out.println(gameData.getTimer());

		// Distance beetween box and the team labels
		int textSeparationFromBox = 10;

		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 26));
		heightFont = metric.getMaxDescent() + 5;

		String team1Text = "Team 1";
		g2d.setColor(lightBlue);
		int team1TextXCoord = x1 + 25 - metric.stringWidth(team1Text)
				- textSeparationFromBox;
		int team1TextYCoord = (by + heightFont) / 2;
		g2d.drawString(team1Text, team1TextXCoord, team1TextYCoord);

		String team2Text = "Team 2";
		g2d.setColor(darkRed);
		int team2TextXCoord = x2 + textSeparationFromBox;
		int team2TextYCoord = (by + heightFont) / 2;
		g2d.drawString(team2Text, team2TextXCoord, team2TextYCoord);

	}

	/**
	 * Controls movement, when a key is pressed it moves the tank Should be
	 * running on a loop in a new thread
	 */
	public void movementControl() {

		while (gameRunning == 1) {
			if (gameData.getTanks()[myID].getHealth() != 0) {
				if (wPressed == 1) {
					gameData.getTanks()[myID].moveUp(gameData.getTanks(),
							gameData.getObstacles());
				}
				if (aPressed == 1) {
					gameData.getTanks()[myID].moveLeft(gameData.getTanks(),
							gameData.getObstacles());
				}
				if (sPressed == 1) {
					gameData.getTanks()[myID].moveDown(gameData.getTanks(),
							gameData.getObstacles());
				}
				if (dPressed == 1) {
					gameData.getTanks()[myID].moveRight(gameData.getTanks(),
							gameData.getObstacles());
				}
			}
			int mx = 0;
			int my = 0;
			int ax = 0;
			int ay = 0;
			try {
				// Updates the tanks top part to point at mouse
				Point m = MouseInfo.getPointerInfo().getLocation();
				mx = m.x;
				my = m.y;
				Point a = getLocationOnScreen().getLocation();
				ax = a.x;
				ay = a.y;
			} catch (IllegalComponentStateException e) {

			}

			gameData.getTanks()[myID].calcAngleOutsideScreen(mx, my, ax, ay,
					tx, ty, sf, by);

			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Calculates the angle that the tank should be facing depending on the keys
	 * pressed. And sets it to the player's tanks variable.
	 */
	public void calculateAngleOfShape() {
		// Checks which angle it should be pointing at
		// Pointing towards the top right (or bottom left)
		if ((wPressed == 1 & dPressed == 1 & sPressed == 0 & aPressed == 0)
				| (sPressed == 1 & aPressed == 1 & wPressed == 0 & dPressed == 0)) {
			gameData.getTanks()[myID].setBotAngle(Math.PI / 4);
		}
		// Pointing towards the bot right (or top left)
		if ((wPressed == 1 & aPressed == 1 & sPressed == 0 & dPressed == 0)
				| (sPressed == 1 & dPressed == 1 & wPressed == 0 & aPressed == 0)) {
			gameData.getTanks()[myID].setBotAngle((Math.PI / 4) + Math.PI / 2);
		}
		// Pointing right or left
		if ((aPressed == 1 | dPressed == 1)
				& ((wPressed == 1 & sPressed == 1) | (wPressed == 0 & sPressed == 0))) {
			gameData.getTanks()[myID].setBotAngle(Math.PI / 2);
		}
		// Pointing top or down
		if ((wPressed == 1 | sPressed == 1)
				& ((aPressed == 1 & dPressed == 1) | (aPressed == 0 & dPressed == 0))) {
			gameData.getTanks()[myID].setBotAngle(0);
		}

	}

	public void updateTank(int id, int x, int y, double bAngle, double tAngle) {
		gameData.updateTank(id, x, y, bAngle, tAngle);
	}

	/**
	 * Creates a new bullet aimed at mouse position
	 */
	public void newBullet(double doublex, double doubley) {
		if (gameData.getTanks()[myID].getHealth() != 0) {
			// Gets mouse coordinates

			// System.out.println(sf);
			doublex = doublex - tx;
			if (tx != 0) {
				doubley = doubley - ty - by;
			} else {
				doubley = doubley - ty - by;

			}
			doublex = doublex / sf;
			doubley = doubley / sf;

			int x = (int) Math.round(doublex);

			// System.out.println(doublex + " " + x);

			int y = (int) Math.round(doubley);

			// Gets tanks coordinates
			Tank myTank = gameData.getTanks()[myID];
			int w2 = myTank.getWidth() / 2;
			int h2 = myTank.getHeight() / 2;

			// Creates new projectile
			Projectile newProj = new Projectile(myTank.getX() + w2,
					myTank.getY() + h2, x, y, myTank.getTeamId());
			//
			// System.out.print(myTank.getX() + "  <- tx and ax -> " + x);
			// System.out.println(" | " + myTank.getY() + "  <- ty and ay -> " +
			// y);
			// Adds new projectile to the new projectile array
			gameData.addNewProj(newProj);
		}
	}

	/**
	 * Creates a new bullet aimed at mouse position
	 */
	public void newBulletAI(double doublex, double doubley) {
		if (gameData.getTanks()[myID].getHealth() != 0) {

			// Gets tanks coordinates
			Tank myTank = gameData.getTanks()[myID];
			int w2 = myTank.getWidth() / 2;
			int h2 = myTank.getHeight() / 2;

			// Creates new projectile
			Projectile newProj = new Projectile(myTank.getX() + w2,
					myTank.getY() + h2, (int) doublex, (int) doubley,
					myTank.getTeamId());
			//
			// System.out.print(myTank.getX() + "  <- tx and ax -> " + x);
			// System.out.println(" | " + myTank.getY() + "  <- ty and ay -> " +
			// y);
			// Adds new projectile to the new projectile array
			gameData.addNewProj(newProj);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Creates a new bullet aimed at mouse position
		Point coord = this.getMousePosition();

		double doublex = coord.getX();
		double doubley = coord.getY();
		newBullet(doublex, doubley);
		sounds.playTankBulletSound();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Finds the far right coord of the map
	 * 
	 * @return
	 */
	public int findFarRight() {
		int maxX = 0;
		for (int i = 0; i < gameData.getObstacles().length; i++) {
			int thisX = gameData.getObstacles()[i].getX()
					+ gameData.getObstacles()[i].getWidth();
			if (thisX > maxX) {
				maxX = thisX;
			}
		}

		return maxX;
	}

	/**
	 * Finds the far right coord of the map
	 * 
	 * @return
	 */
	public int findFarDown() {
		int maxY = 0;
		for (int i = 0; i < gameData.getObstacles().length; i++) {
			int thisY = gameData.getObstacles()[i].getY()
					+ gameData.getObstacles()[i].getHeight();
			if (thisY > maxY) {
				maxY = thisY;
			}
		}

		return maxY;
	}

	public int getWantedFps() {
		return wantedFps;
	}

	public void setWantedFps(int wantedFps) {
		this.wantedFps = wantedFps;
	}

	/**
	 * Decides if you won the game and stops the game
	 * 
	 * @param winner
	 */
	public void decideWinner(int winner) {

		// Checks if you are the winnning team
		if (gameData.isEqualTeamID(winner)) {
			if (winner == gameData.getTanks()[myID].getTeamId()) {
				// If you are ends the game with you having won
				mm.showEndGamePanel(1);
				endGamePanel(1);

				System.out.println("Decide Winner: " + 1);
			} else {
				// Else, ends the game with you having lost.
				mm.showEndGamePanel(0);
				endGamePanel(0);

				System.out.println("Decide Winner: " + 0);
			}
		} else {
			// if game ends in a tie
			endGamePanel(-1);
			mm.showEndGamePanel(-1);
			System.out.println("DRAW...");

			System.out.println("Decide Winner: " + -1);
		}
	}
}
