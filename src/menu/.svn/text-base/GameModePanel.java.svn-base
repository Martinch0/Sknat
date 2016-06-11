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
 * Class responsible for showing the screen that lists all game modes for
 * multiplayer.
 * 
 * @author Tendaishe Nyamapfeni
 * 
 */
public class GameModePanel extends JPanel {

	// Mode text
	private String modeImgLocation = "src/resources/Mode_text.png";
	private BufferedImage imgModeText;

	// 1v1 Button
	private String v1ImgLocation1 = "src/resources/1v1_1.png";
	private String v1ImgLocation2 = "src/resources/1v1_2.png";
	private String v1ImgLocation3 = "src/resources/1v1_3.png";
	private BufferedImage img1v1Button1;
	private BufferedImage img1v1Button2;
	private BufferedImage img1v1Button3;

	// 2v2 Button
	private String v2ImgLocation1 = "src/resources/2v2_1.png";
	private String v2ImgLocation2 = "src/resources/2v2_2.png";
	private String v2ImgLocation3 = "src/resources/2v2_3.png";
	private BufferedImage img2v2Button1;
	private BufferedImage img2v2Button2;
	private BufferedImage img2v2Button3;

	// 2vAI Button
	private String vAIImgLocation1 = "src/resources/2vAI_1.png";
	private String vAIImgLocation2 = "src/resources/2vAI_2.png";
	private String vAIImgLocation3 = "src/resources/2vAI_3.png";
	private BufferedImage img2vAIButton1;
	private BufferedImage img2vAIButton2;
	private BufferedImage img2vAIButton3;

	// Back Button
	private String backImgLocation1 = "src/resources/Back_1.png";
	private String backImgLocation2 = "src/resources/Back_2.png";
	private String backImgLocation3 = "src/resources/Back_3.png";
	private BufferedImage imgBackButton1;
	private BufferedImage imgBackButton2;
	private BufferedImage imgBackButton3;

	private GridBagConstraints gbc = new GridBagConstraints();
	private MainMenu mm;

	public GameModePanel(final MainMenu mainMenu) {
		mm = mainMenu;

		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5); // spacing between components

		// Process images //////
		try {
			// Mode text
			imgModeText = ImageIO.read(new File(modeImgLocation));

			// Back button - Buffered images
			imgBackButton1 = ImageIO.read(new File(backImgLocation1));
			imgBackButton2 = ImageIO.read(new File(backImgLocation2));
			imgBackButton3 = ImageIO.read(new File(backImgLocation3));

			// 1v1 button - Buffered images
			img1v1Button1 = ImageIO.read(new File(v1ImgLocation1));
			img1v1Button2 = ImageIO.read(new File(v1ImgLocation2));
			img1v1Button3 = ImageIO.read(new File(v1ImgLocation3));

			// 2v2 button - Buffered images
			img2v2Button1 = ImageIO.read(new File(v2ImgLocation1));
			img2v2Button2 = ImageIO.read(new File(v2ImgLocation2));
			img2v2Button3 = ImageIO.read(new File(v2ImgLocation3));

			// 2vAI button - Buffered images
			img2vAIButton1 = ImageIO.read(new File(vAIImgLocation1));
			img2vAIButton2 = ImageIO.read(new File(vAIImgLocation2));
			img2vAIButton3 = ImageIO.read(new File(vAIImgLocation3));

		} catch (IOException e) {
			System.out.println("Error: Image could not found/read!");
		}

		// Image - Mode text /////////
		JLabel lblMode = new JLabel(new ImageIcon(imgModeText));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(lblMode, gbc);

		// button - 1v1 ////
		JButton btn1v1 = new JButton(new ImageIcon(img1v1Button1));
		mainMenu.setupButtons(btn1v1, img1v1Button2, img1v1Button3); // format
																		// button
		btn1v1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start Server and switch panel
				mainMenu.startHosting(2, "1vs1");
				mainMenu.switchPanel("3");
			}
		});
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(20, 5, 20, 5); // spacing between components
		add(btn1v1, gbc);

		// button - 2v2 ////
		JButton btn2v2 = new JButton(new ImageIcon(img2v2Button1));
		mainMenu.setupButtons(btn2v2, img2v2Button2, img2v2Button3); // format
																		// button
		btn2v2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start Server and switch panel
				mainMenu.startHosting(4, "2vs2"); // parameter = number of
													// players
				mainMenu.switchPanel("3");
			}
		});
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(20, 5, 20, 5); // spacing between components
		add(btn2v2, gbc);

		// Button - Co-op////////////////
		JButton btnCoOP = new JButton(new ImageIcon(img2vAIButton1));
		mainMenu.setupButtons(btnCoOP, img2vAIButton2, img2vAIButton3);
		btnCoOP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mm.startHosting(4, "coop");
				mm.createAI(4, 1);
				mm.createAI(4, 1);

				mm.switchPanel("3");
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(btnCoOP, gbc);

		// Button - Back ///////
		JButton btnBack = new JButton(new ImageIcon(imgBackButton1));
		mm.setupButtons(btnBack, imgBackButton2, imgBackButton3);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainMenu.switchPanel("2"); // switch panel
			}
		});
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(btnBack, gbc);

	}

}
