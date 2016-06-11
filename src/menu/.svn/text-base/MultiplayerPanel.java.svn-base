package menu;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * This panel will allow the player to Create or Join a session
 * 
 * @author Tendaishe Nyamapfeni
 * 
 */
@SuppressWarnings("serial")
public class MultiplayerPanel extends JPanel {
	// Class Fields
	// /////Image Locations///////
	private String multiplayerImgLocation = "src/resources/Multiplayer_text.png";

	private String createSessionImgLocation1 = "src/resources/Create_Session_1.png";
	private String createSessionImgLocation2 = "src/resources/Create_Session_2.png";
	private String createSessionImgLocation3 = "src/resources/Create_Session_3.png";

	private String joinSessionImgLocation1 = "src/resources/Join_Session_1.png";
	private String joinSessionImgLocation2 = "src/resources/Join_Session_2.png";
	private String joinSessionImgLocation3 = "src/resources/Join_Session_3.png";

	private String backImgLocation1 = "src/resources/Back_1.png";
	private String backImgLocation2 = "src/resources/Back_2.png";
	private String backImgLocation3 = "src/resources/Back_3.png";

	// /////Layout/////////
	private GridBagConstraints gbc = new GridBagConstraints();
	// /////Buffered Images///////
	private BufferedImage imgMultiplayerText;

	private BufferedImage imgCreateSessionButton1;
	private BufferedImage imgCreateSessionButton2;
	private BufferedImage imgCreateSessionButton3;

	private BufferedImage imgJoinSessionButton1;
	private BufferedImage imgJoinSessionButton2;
	private BufferedImage imgJoinSessionButton3;

	private BufferedImage imgBackButton1;
	private BufferedImage imgBackButton2;
	private BufferedImage imgBackButton3;

	/**
	 * Constructor: All the panel elements are initialized, and configured in
	 * this constructor. It takes the MainMenu as a parameter in order to allow
	 * efficient panel switching and data flow.
	 * 
	 * @param mainMenu
	 */
	public MultiplayerPanel(final MainMenu mainMenu) {
		// GridbagLayout /////
		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 20, 5); // spacing between components
		gbc.anchor = GridBagConstraints.NORTHWEST;

		// Process images //////
		try {
			// Multiplayer text
			imgMultiplayerText = ImageIO.read(new File(multiplayerImgLocation));

			// Create Session button - Buffered images
			imgCreateSessionButton1 = ImageIO.read(new File(
					createSessionImgLocation1));
			imgCreateSessionButton2 = ImageIO.read(new File(
					createSessionImgLocation2));
			imgCreateSessionButton3 = ImageIO.read(new File(
					createSessionImgLocation3));

			// Join Session button - Buffered images
			imgJoinSessionButton1 = ImageIO.read(new File(
					joinSessionImgLocation1));
			imgJoinSessionButton2 = ImageIO.read(new File(
					joinSessionImgLocation2));
			imgJoinSessionButton3 = ImageIO.read(new File(
					joinSessionImgLocation3));

			// Back button - Buffered images
			imgBackButton1 = ImageIO.read(new File(backImgLocation1));
			imgBackButton2 = ImageIO.read(new File(backImgLocation2));
			imgBackButton3 = ImageIO.read(new File(backImgLocation3));

		} catch (IOException e) {
			System.out.println("Error: Image could not found/read!");
		}

		// Image - Multiplayer text /////////
		JLabel imgLabel = new JLabel(new ImageIcon(imgMultiplayerText));
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(imgLabel, gbc);

		// Button - Create Session /
		JButton btnCreateSession = new JButton(new ImageIcon(
				imgCreateSessionButton1));
		mainMenu.setupButtons(btnCreateSession, imgCreateSessionButton2,
				imgCreateSessionButton3); // format button
		btnCreateSession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start Server and switch panel
				mainMenu.switchPanel("4");
			}
		});
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(20, 5, 20, 5); // spacing between components
		add(btnCreateSession, gbc);

		// Button - Join Session ///////
		JButton btnJoinSession = new JButton(new ImageIcon(
				imgJoinSessionButton1));
		mainMenu.setupButtons(btnJoinSession, imgJoinSessionButton2,
				imgJoinSessionButton3); // format button
		btnJoinSession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIManager UI = new UIManager();
				UI.put("OptionPane.background", Color.WHITE);
				UI.put("Panel.background", Color.WHITE);
				UI.put("OptionPane.messageForeground", new Color(0xCF7B23));
				//new Color(0xe69138)
				String hostIP = null;
				hostIP = JOptionPane.showInputDialog("Enter ServerIP/HostIP");
				if (hostIP != null) { // Check if user input is null
					JOptionPane.showMessageDialog(null,
							"The server will now try to join:   '" + hostIP
									+ "'.\n" + "Click OK to continue");
					mainMenu.startClient(hostIP); // Create Client
				} else {
					JOptionPane.showMessageDialog(null,
							"Please input a valid IP Address.");
				}
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(btnJoinSession, gbc);

		// Button - Back ///////
		JButton btnBack = new JButton(new ImageIcon(imgBackButton1));
		mainMenu.setupButtons(btnBack, imgBackButton2, imgBackButton3);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainMenu.switchPanel("1"); // switch panel
			}
		});
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(btnBack, gbc);

	}

}
