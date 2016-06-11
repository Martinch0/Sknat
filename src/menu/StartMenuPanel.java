package menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel will allow the user to navigate to the multiplayer panel or exit
 * the game. This is basically the front of the game.
 * 
 * @author Tendaishe Nyamapfeni
 * 
 */
@SuppressWarnings("serial")
public class StartMenuPanel extends JPanel {
	// Class Fields
	// //////Image Locations///////
	private String logoImgLocation = "src/resources/Logo.png";

	private String singleplayerImgLocation1 = "src/resources/Singleplayer_1.png";
	private String singleplayerImgLocation2 = "src/resources/Singleplayer_2.png";
	private String singleplayerImgLocation3 = "src/resources/Singleplayer_3.png";

	private String multiplayerImgLocation1 = "src/resources/Multiplayer_1.png";
	private String multiplayerImgLocation2 = "src/resources/Multiplayer_2.png";
	private String multiplayerImgLocation3 = "src/resources/Multiplayer_3.png";

	private String exitImgLocation1 = "src/resources/Exit_1.png";
	private String exitImgLocation2 = "src/resources/Exit_2.png";
	private String exitImgLocation3 = "src/resources/Exit_3.png";
	// ///////Layout//////
	private GridBagConstraints gbc = new GridBagConstraints();
	// ///////Buffered Images/////
	private BufferedImage imgLogo;

	private BufferedImage imgSingleplayerButton1;
	private BufferedImage imgSingleplayerButton2;
	private BufferedImage imgSingleplayerButton3;


	private BufferedImage imgMultiplayerButton1;
	private BufferedImage imgMultiplayerButton2;
	private BufferedImage imgMultiplayerButton3;

	private BufferedImage imgExitButton1;
	private BufferedImage imgExitButton2;
	private BufferedImage imgExitButton3;

	private MainMenu mm;

	/**
	 * Constructor: All the panel elements are initialized, and configured in
	 * this constructor. It takes the MainMenu as a parameter in order to allow
	 * efficient panel switching and data flow.
	 * 
	 * @param mainMenu
	 */
	public StartMenuPanel(final MainMenu mainMenu) {
		mm = mainMenu;
		// GridbagLayout ///////
		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5); // spacing between components

		// Process images /////
		try {
			// Logo - Buffered Image/
			imgLogo = ImageIO.read(new File(logoImgLocation));

			// Singleplayer Button - Buffered Image/
			imgSingleplayerButton1 = ImageIO.read(new File(
					singleplayerImgLocation1));
			imgSingleplayerButton2 = ImageIO.read(new File(
					singleplayerImgLocation2));
			imgSingleplayerButton3 = ImageIO.read(new File(
					singleplayerImgLocation3));

			// Multiplayer Button - Buffered Image/
			imgMultiplayerButton1 = ImageIO.read(new File(
					multiplayerImgLocation1));
			imgMultiplayerButton2 = ImageIO.read(new File(
					multiplayerImgLocation2));
			imgMultiplayerButton3 = ImageIO.read(new File(
					multiplayerImgLocation3));

			// Exit Button - Buffered Image/
			imgExitButton1 = ImageIO.read(new File(exitImgLocation1));
			imgExitButton2 = ImageIO.read(new File(exitImgLocation2));
			imgExitButton3 = ImageIO.read(new File(exitImgLocation3));

		} catch (IOException e) {
			System.out.println("Error: Image could not found/read!");
		}

		// Image - Logo /
		JLabel lblLogo = new JLabel(new ImageIcon(imgLogo));
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblLogo, gbc);

		// Button - Singleplayer/
		JButton btnSingleplayer = new JButton(new ImageIcon(
				imgSingleplayerButton1));
		mainMenu.setupButtons(btnSingleplayer, imgSingleplayerButton2,
				imgSingleplayerButton3);
		btnSingleplayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mm.startHosting(2, "AI1vs1");
				mm.createAI(2, 1);
				mm.switchPanel("3");
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(btnSingleplayer, gbc);

		
		// Button - Multiplayer //////////
		JButton btnMultiplayer = new JButton(new ImageIcon(
				imgMultiplayerButton1));
		mainMenu.setupButtons(btnMultiplayer, imgMultiplayerButton2,
				imgMultiplayerButton3);
		btnMultiplayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainMenu.switchPanel("2");// switch panel
			}
		});
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(btnMultiplayer, gbc);

		// Button - Exit /
		JButton btnExit = new JButton(new ImageIcon(imgExitButton1));
		mainMenu.setupButtons(btnExit, imgExitButton2, imgExitButton3);
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Exit Game
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(btnExit, gbc);
	}
}
