package menu;

import java.awt.Color;
import java.awt.Font;
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
 * Class responsible for showing victory, defeat and draw screens.
 * 
 * @author Tendaishe Nyamapfeni
 * 
 */
public class EndGamePanel extends JPanel {

	private String victoryImgLocation = "src/resources/Victory_Animation.gif";
	private String defeatImgLocation = "src/resources/Defeat_Animation.gif";
	private String drawImgLocation = "src/resources/Draw_Animation.gif";

	// Team Text
	private String team1ImgLocation = "src/resources/Team1_text.png";
	private String team2ImgLocation = "src/resources/Team2_text.png";
	private BufferedImage imgTeam1;
	private BufferedImage imgTeam2;

	// VS Text
	private String vsImgLocation = "src/resources/VS_text.png";
	private BufferedImage imgVSText;

	// Continue Button
	private String continueImgLocation1 = "src/resources/Continue_1.png";
	private String continueImgLocation2 = "src/resources/Continue_2.png";
	private String continueImgLocation3 = "src/resources/Continue_3.png";
	private BufferedImage imgContinueButton1;
	private BufferedImage imgContinueButton2;
	private BufferedImage imgContinueButton3;

	private GridBagConstraints gbc = new GridBagConstraints();

	private int team1Score;
	private int team2Score;

	private MainMenu mm;

	public EndGamePanel(MainMenu mainMenu, int endGameState) {
		mm = mainMenu;
		// Process images //////
		try {

			// Teams - Buffered Images
			imgTeam1 = ImageIO.read(new File(team1ImgLocation));
			imgTeam2 = ImageIO.read(new File(team2ImgLocation));

			// VS text
			imgVSText = ImageIO.read(new File(vsImgLocation));

			// Continue button - Buffered images
			imgContinueButton1 = ImageIO.read(new File(continueImgLocation1));
			imgContinueButton2 = ImageIO.read(new File(continueImgLocation2));
			imgContinueButton3 = ImageIO.read(new File(continueImgLocation3));

		} catch (IOException e) {
			System.out.println("Error: Image could not found/read!");
		}

		if (endGameState == 1) {
			setupPanel(victoryImgLocation);
		} else if (endGameState == 0) {
			setupPanel(defeatImgLocation);
		} else {
			setupPanel(drawImgLocation);

		}

	}

	public void setupPanel(String animation) {
		// Get team scores from gameData
		team1Score = mm.getTeamScores()[0];
		team2Score = mm.getTeamScores()[1];

		// Colors
		Color darkBlue = new Color(0x1189ae);
		Color darkRed = new Color(0xd4002e);

		// GridbagLayout ///////
		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5); // spacing between components
		gbc.weightx = 1;
		gbc.weighty = 0;

		// Animation - Logo /////////
		JLabel lblAnimation = new JLabel(new ImageIcon(animation));
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblAnimation, gbc);

		// Image - Team 1 Text /////////
		JLabel lblTeam1 = new JLabel(new ImageIcon(imgTeam1));
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		add(lblTeam1, gbc);

		// Image - Team 2 Text /////////
		JLabel lblTeam2 = new JLabel(new ImageIcon(imgTeam2));
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		add(lblTeam2, gbc);

		// /////// Image - VS Text //////
		JLabel lblVSText = new JLabel(new ImageIcon(imgVSText));
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		// add(lblVSText, gbc);

		// Button - Continue
		JButton btnContinue = new JButton(new ImageIcon(imgContinueButton1));
		mm.setupButtons(btnContinue, imgContinueButton2, imgContinueButton3);
		btnContinue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mm.switchPanel("1");
			}
		});
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(btnContinue, gbc);

		// Label - Team 1 Score
		JLabel lblTeam1Score = new JLabel("" + team1Score);
		lblTeam1Score.setFont(new Font("Serif", Font.PLAIN, 36));
		lblTeam1Score.setForeground(darkBlue);
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lblTeam1Score, gbc);

		// Label - Team 2 Score
		JLabel lblTeam2Score = new JLabel("" + team2Score);
		lblTeam2Score.setFont(new Font("Serif", Font.PLAIN, 36));
		lblTeam2Score.setForeground(darkRed);
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(lblTeam2Score, gbc);

	}

}
