package menu;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import client.GameClient;

/**
 * Class responsible for showing the game lobby.
 * 
 * @author Alexandre Portugal, Bella Dunvoska, Martin Mihov,Tendaishe
 *         Nyamapfeni, Samuel Hill
 * 
 */
public class LobbyPanel extends JPanel {

	// Lobby Text
	private String lobbyImgLocation = "src/resources/Lobby_text.png";
	private BufferedImage imgLobbyText;

	// VS Text
	private String vsImgLocation = "src/resources/VS_text.png";
	private BufferedImage imgVSText;

	private GridBagConstraints gbc = new GridBagConstraints();
	private GridBagConstraints gbc2 = new GridBagConstraints();
	private GridBagConstraints gbc3 = new GridBagConstraints();

	// Switch Team Button
	private String switchTeamImgLocation1 = "src/resources/Switch_team_1.png";
	private String switchTeamImgLocation2 = "src/resources/Switch_team_2.png";
	private String switchTeamImgLocation3 = "src/resources/Switch_team_3.png";
	private BufferedImage imgSwitchTeamButton1;
	private BufferedImage imgSwitchTeamButton2;
	private BufferedImage imgSwitchTeamButton3;

	// Set NickName Button
	private String nicknameImgLocation1 = "src/resources/Nickname_1.png";
	private String nicknameImgLocation2 = "src/resources/Nickname_2.png";
	private String nicknameImgLocation3 = "src/resources/Nickname_3.png";
	private BufferedImage imgNicknameButton1;
	private BufferedImage imgNicknameButton2;
	private ImageIcon icon = new ImageIcon(nicknameImgLocation3, "An Icon");
	private BufferedImage imgNicknameButton3;

	// Back Button
	private String backImgLocation1 = "src/resources/Back_1.png";
	private String backImgLocation2 = "src/resources/Back_2.png";
	private String backImgLocation3 = "src/resources/Back_3.png";
	private BufferedImage imgBackButton1;
	private BufferedImage imgBackButton2;
	private BufferedImage imgBackButton3;

	// Send Button
	private String sendImgLocation1 = "src/resources/Send_1.png";
	private String sendImgLocation2 = "src/resources/Send_2.png";
	private String sendImgLocation3 = "src/resources/Send_3.png";
	private BufferedImage imgSendButton1;
	private BufferedImage imgSendButton2;
	private BufferedImage imgSendButton3;

	// Team Text
	private String team1ImgLocation = "src/resources/Team1_text.png";
	private String team2ImgLocation = "src/resources/Team2_text.png";
	private BufferedImage imgTeam1;
	private BufferedImage imgTeam2;

	// Start Game button
	private JButton btnStartGame;
	private String startGameImgLocation1 = "src/resources/StartGame_1.png";
	private String startGameImgLocation2 = "src/resources/StartGame_2.png";
	private String startGameImgLocation3 = "src/resources/StartGame_3.png";
	private BufferedImage imgStartGameButton1;
	private BufferedImage imgStartGameButton2;
	private BufferedImage imgStartGameButton3;

	// Ready Button
	private String readyImgLocation1 = "src/resources/Ready_1.png";
	private String readyImgLocation2 = "src/resources/Ready_2.png";
	private String readyImgLocation3 = "src/resources/Ready_3.png";
	private BufferedImage imgReadyButton1;
	private BufferedImage imgReadyButton2;
	private BufferedImage imgReadyButton3;

	private MainMenu mm;

	// Chat Fields
	private JScrollPane jspChat;
	private ArrayList<String> allMessages;
	private DefaultListModel<String> listModel;
	private JList<String> lstChatBox;

	// Table Fields
	private TeamTableModel modelTeam1;
	private TeamTableModel modelTeam2;

	public LobbyPanel(final MainMenu mainMenu) {
		mm = mainMenu;

		// Process images //////
		try {
			// Lobbby text
			imgLobbyText = ImageIO.read(new File(lobbyImgLocation));

			// VS text
			imgVSText = ImageIO.read(new File(vsImgLocation));

			// Switch Team Button - Buffered Image
			imgSwitchTeamButton1 = ImageIO
					.read(new File(switchTeamImgLocation1));
			imgSwitchTeamButton2 = ImageIO
					.read(new File(switchTeamImgLocation2));
			imgSwitchTeamButton3 = ImageIO
					.read(new File(switchTeamImgLocation3));

			// Set Nickname Button - Buffered Image
			imgNicknameButton1 = ImageIO.read(new File(nicknameImgLocation1));
			imgNicknameButton2 = ImageIO.read(new File(nicknameImgLocation2));
			imgNicknameButton3 = ImageIO.read(new File(nicknameImgLocation3));

			// Back Button - Buffered Image
			imgBackButton1 = ImageIO.read(new File(backImgLocation1));
			imgBackButton2 = ImageIO.read(new File(backImgLocation2));
			imgBackButton3 = ImageIO.read(new File(backImgLocation3));

			// Send Button - Buffered Image
			imgSendButton1 = ImageIO.read(new File(sendImgLocation1));
			imgSendButton2 = ImageIO.read(new File(sendImgLocation2));
			imgSendButton3 = ImageIO.read(new File(sendImgLocation3));

			// Teams - Buffered Images
			imgTeam1 = ImageIO.read(new File(team1ImgLocation));
			imgTeam2 = ImageIO.read(new File(team2ImgLocation));

			// Start Game button - Buffered images
			imgStartGameButton1 = ImageIO.read(new File(startGameImgLocation1));
			imgStartGameButton2 = ImageIO.read(new File(startGameImgLocation2));
			imgStartGameButton3 = ImageIO.read(new File(startGameImgLocation3));

			// Ready button - Buffered images
			imgReadyButton1 = ImageIO.read(new File(readyImgLocation1));
			imgReadyButton2 = ImageIO.read(new File(readyImgLocation2));
			imgReadyButton3 = ImageIO.read(new File(readyImgLocation3));

		} catch (IOException e) {
			System.out.println("Error: Image could not found/read!");
		}

		setupPanel(); // initialize panel

	}

	public void setupPanel() {
		// JPanels for layout ////
		JPanel pnlTeams = new JPanel();
		JPanel pnlChat = new JPanel();

		// GridbagLayout /////
		setLayout(new GridBagLayout());
		pnlTeams.setLayout(new GridBagLayout());
		pnlChat.setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5); // spacing between components
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc2.insets = new Insets(5, 5, 5, 5); // spacing between components
		gbc2.weightx = 1;
		gbc2.weighty = 1;
		gbc3.insets = new Insets(5, 5, 5, 5); // spacing between components
		gbc3.weightx = 1;
		gbc3.weighty = 1;

		// Image - Lobby Text //////
		JLabel lblLobbyText = new JLabel(new ImageIcon(imgLobbyText));
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblLobbyText, gbc);

		// ////////////// Chat Panel////////////////////////////////
		// Text Area - Chatbox
		listModel = new DefaultListModel<String>();
		lstChatBox = new JList<String>(listModel);
		lstChatBox.setForeground(new Color(0xe69138));
		lstChatBox.setBackground(Color.BLACK);
		lstChatBox.setSelectionForeground(new Color(0xe69138));
		lstChatBox.setSelectionBackground(Color.BLACK);
		listModel.addElement("---- Chat with other Players ----");
		jspChat = new JScrollPane(lstChatBox);
		// Add a listener to the scroll bar that will automatically update
		// scrolling when elements are added
		jspChat.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(AdjustmentEvent e) {
						Adjustable sb = e.getAdjustable();
						sb.setValue(sb.getMaximum());
					}
				});
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 2;
		gbc2.gridheight = 1;
		gbc2.fill = GridBagConstraints.BOTH;
		pnlChat.add(jspChat, gbc2);

		// ////// Text Field - Message ///////////////
		final JTextField txtMessage = new JTextField("...Enter Message...");
		txtMessage.setBackground(Color.BLACK);
		txtMessage.setForeground(new Color(0xe69138));
		txtMessage.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				txtMessage.selectAll();
			}
		});
		gbc2.gridwidth = 1;
		gbc2.gridheight = 1;
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.weighty = 0;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		pnlChat.add(txtMessage, gbc2);

		// ///////// Button - Send /////////
		JButton btnSend = new JButton(new ImageIcon(imgSendButton1));
		mm.setupButtons(btnSend, imgSendButton2, imgSendButton3);
		mm.getFrame().getRootPane().setDefaultButton(btnSend);
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = txtMessage.getText();
				if (!message.isEmpty()) {
					mm.sendChatMessage(message);
					txtMessage.setText("");
				}
			}
		});
		gbc2.gridwidth = 1;
		gbc2.gridheight = 1;
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		gbc2.weightx = 0;
		gbc2.fill = 0;
		gbc2.anchor = GridBagConstraints.EAST;
		pnlChat.add(btnSend, gbc2);

		// Image - Team 1 Text /////////
		JLabel lblTeam1 = new JLabel(new ImageIcon(imgTeam1));
		gbc3.gridwidth = 3;
		gbc3.gridheight = 1;
		gbc3.gridx = 0;
		gbc3.gridy = 1;
		gbc3.anchor = GridBagConstraints.CENTER;
		pnlTeams.add(lblTeam1, gbc3);

		// Image - Team 2 Text /////////
		JLabel lblTeam2 = new JLabel(new ImageIcon(imgTeam2));
		gbc3.gridwidth = 3;
		gbc3.gridheight = 1;
		gbc3.gridx = 0;
		gbc3.gridy = 3;
		gbc3.anchor = GridBagConstraints.CENTER;
		pnlTeams.add(lblTeam2, gbc3);

		// //////////// Button - Switch team ///////////////
		JButton btnSwitchTeam = new JButton(new ImageIcon(imgSwitchTeamButton1));
		btnSwitchTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// This check ensures that team limits have been instantiated
				// before team changes occur
				if (GameClient.NUMBER_OF_PLAYERS != 0) {
					mm.updateTeam();
				}
			}
		});
		mm.setupButtons(btnSwitchTeam, imgSwitchTeamButton2,
				imgSwitchTeamButton3);
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		gbc3.gridx = 0;
		gbc3.gridy = 2;
		gbc3.anchor = GridBagConstraints.CENTER;
		pnlTeams.add(btnSwitchTeam, gbc3);

		// /////// Image - VS Text //////
		JLabel lblVSText = new JLabel(new ImageIcon(imgVSText));
		gbc3.anchor = GridBagConstraints.CENTER;
		gbc3.gridx = 1;
		gbc3.gridy = 2;
		pnlTeams.add(lblVSText, gbc3);

		// /////////////// Button - Set Nickname /////////
		JButton btnRename = new JButton(new ImageIcon(imgNicknameButton1));
		mm.setupButtons(btnRename, imgNicknameButton2, imgNicknameButton3);
		btnRename.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nickname = (String) JOptionPane.showInputDialog(null,
						"Enter Nickame", "Sknat", JOptionPane.QUESTION_MESSAGE,
						icon, null, "currentNickname");
				if (nickname == null || nickname == "") {
					JOptionPane.showMessageDialog(null,
							"Enter a valid nickname");

				} else {
					mm.changeName(nickname);
				}
			}
		});
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		gbc3.gridx = 2;
		gbc3.gridy = 2;
		gbc3.anchor = GridBagConstraints.CENTER;
		pnlTeams.add(btnRename, gbc3);
		// ////////// Table - Team 1 /////////////////////////////////
		JTable tblTeam1 = new JTable();
		modelTeam1 = new TeamTableModel(0);
		tblTeam1.setModel(modelTeam1);

		TableColumnModel columnModel = tblTeam1.getColumnModel();
		for (int i = 0; i < modelTeam1.getColumnCount() - 1; i++) {
			columnModel.getColumn(i).setCellRenderer(new TeamTableRenderer());
		}
		columnModel.getColumn(2).setMaxWidth(200);
		tblTeam1.setPreferredScrollableViewportSize(new Dimension(300, 64));
		JScrollPane jspTeam1 = new JScrollPane(tblTeam1);
		gbc3.gridwidth = 3;
		gbc3.gridheight = 1;
		gbc3.gridx = 0;
		gbc3.gridy = 0;
		gbc3.anchor = GridBagConstraints.CENTER;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		pnlTeams.add(jspTeam1, gbc3);
		// /////////// Table - Team 2 /////////////////////////////////
		JTable tblTeam2 = new JTable();
		modelTeam2 = new TeamTableModel(1);
		tblTeam2.setModel(modelTeam2);
		TableColumnModel columnModel2 = tblTeam2.getColumnModel();
		for (int i = 0; i < modelTeam2.getColumnCount() - 1; i++) {
			columnModel2.getColumn(i).setCellRenderer(new TeamTableRenderer());
		}
		columnModel2.getColumn(2).setMaxWidth(200);
		tblTeam2.setPreferredScrollableViewportSize(new Dimension(300, 64));
		tblTeam2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JScrollPane jspTeam2 = new JScrollPane(tblTeam2);
		gbc3.gridwidth = 3;
		gbc3.gridheight = 1;
		gbc3.gridx = 0;
		gbc3.gridy = 4;
		gbc3.anchor = GridBagConstraints.CENTER;
		pnlTeams.add(jspTeam2, gbc3);

		// ////// Button - Back /////////
		JButton btnBack = new JButton(new ImageIcon(imgBackButton1));
		mm.setupButtons(btnBack, imgBackButton2, imgBackButton3);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Select which panel to go back to
				if (mm.getGameType().equals("AI1vs1")) {
					mm.switchPanel("1"); // switch panel (Back to Start Menu
											// Panel)
				} else {
					mm.switchPanel("2"); // switch panel (Back to Multiplayer
											// Panel)
				}
				// Stop Server Communications
				if (mm.getServer() != null) {
					mm.getServer().stopServer();
					mm.setServer(null);
				}
			}
		});
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		add(btnBack, gbc);

		// ///////// Button - Start Game /////////
		btnStartGame = new JButton(new ImageIcon(imgStartGameButton1));
		btnStartGame.setEnabled(false);
		mm.setupButtons(btnStartGame, imgStartGameButton2, imgStartGameButton3);
		btnStartGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mm.getPlayerID() == 0) {
					mm.sendStartState();
					// mm.switchPanel("4"); // switch panel
					// mm.startGamePanel(); // start movement threads
				} else {
					// Tell the main menu that the ready button has been clicked
					mm.updateReady(mm.getPlayerID());
					btnStartGame.setEnabled(false);
				}
			}
		});
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		add(btnStartGame, gbc);

		// //////// JPanel - Chat ////////
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(pnlChat, gbc);

		// //// JPanel - Teams ////////
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(pnlTeams, gbc);
	}

	/**
	 * This method changes the start game button to a ready button
	 */

	public void renameBtnStartGame() {
		btnStartGame.setIcon(new ImageIcon(imgReadyButton1));
		mm.setupButtons(btnStartGame, imgReadyButton2, imgReadyButton3);
		btnStartGame.setEnabled(true);
	}

	/**
	 * This method checks whether players are ready.
	 */
	public void checkReady() {
		if (mm.getPlayerID() == 0) {
			new Thread(new Runnable() { // new thread to check if players have
						// connected
						public void run() {
							btnStartGame.setEnabled(false);
							while (mm.isPlayersReady() == false) {
								// do nothing
								// System.out.println("DO nothing");
							}
							btnStartGame.setEnabled(true);
							repaint();
						}
					}).start();
		}
	}

	/**
	 * @return the listModel
	 */
	public DefaultListModel getListModel() {
		return listModel;
	}

	/**
	 * A method to filter the addresses stored on the network interface and
	 * return the appropriate address
	 * 
	 * @return
	 */
	private String getLocalNetworkIP() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface currentNetwork = networkInterfaces
						.nextElement();
				System.out.println("Name of current Network: "
						+ currentNetwork.getName());
				// remove interfaces that are dead, purely localHost, remove
				// non-physical networks.

				System.out
						.println("All networks: " + currentNetwork.toString());
				// In essence this just prints the IP from the wlan0 network
				// interface
				if (currentNetwork.isUp() && !currentNetwork.isLoopback()
						&& !currentNetwork.isVirtual()
						&& currentNetwork.getName().compareTo("wlan0") == 0) {

					System.out.println("All networks: "
							+ currentNetwork.toString());
					if (currentNetwork.isUp() && !currentNetwork.isLoopback()
							&& !currentNetwork.isVirtual()) {

						// Take IP's from current interface... that we are
						// authenitcated to get
						Enumeration<InetAddress> ipAddresses = currentNetwork
								.getInetAddresses();
						while (ipAddresses.hasMoreElements()) {
							InetAddress currentAddress = ipAddresses
									.nextElement();
							System.out.println("current Address:"
									+ currentAddress.toString());
							if (!currentAddress.isLoopbackAddress()
									&& currentAddress instanceof Inet4Address) {
								return currentAddress.toString();
							}
						}
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param teamNumber
	 * @return TeamTableModel
	 */
	public TeamTableModel getTableModel(int teamNumber) {
		switch (teamNumber) {
		case 0:
			return modelTeam1;
		case 1:
			return modelTeam2;
		}
		return null;
	}

}
