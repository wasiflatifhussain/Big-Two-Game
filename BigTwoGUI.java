import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


/**
 * The BigTwoGUI class implements the CardGameUI interface. It is used to build a GUI for the Big Two card game and handle all user actions. <br>
 * @author wasiflatifhussain
 *
 */
public class BigTwoGUI implements CardGameUI{   

	/**
	 * A constructor for creating a BigTwoGUI. The parameter game is a reference to a Big Two card game associates with this GUI.
	 * @param game A reference to a Big Two card game associated with the GUI.
	 */
	public BigTwoGUI(BigTwo game) {
		// TODO Auto-generated constructor stub
		

		
		this.game = game;   //declare the game instance to be used
		playerList = game.getPlayerList();  //declare the playerList to be used in this game instance 
		handsOnTable = game.getHandsOnTable();  //arrayList to store all valid hands played during the game
		
		this.go();
		
		frame = new JFrame("Big Two");  //make a new JFrame to holder the entire game gui
		frameHeight = 800;
		frameWidth = 1200;
		frame.setPreferredSize(new Dimension(frameWidth,frameHeight));  //setting preferred size of the gui
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//making the menu bar for the game
		JMenuBar menuBar = new JMenuBar();
		JMenu gameTab = new JMenu("Game");
		
		JMenuItem connectButton = new JMenuItem("Connect");  //menu button to be used to restart the game
		connectButton.addActionListener(new ConnectMenuListener());  //add a button listener to listen to the button click
		gameTab.add(connectButton);
		 
		JMenuItem quitButton = new JMenuItem("Quit");  //menu button to be used to quit the game and close the gui
		quitButton.addActionListener(new QuitMenuItemListener());  //add a button listener to listen to the button click
		gameTab.add(quitButton);
		
		
		JMenu messageTab = new JMenu("Message");  
		JMenuItem clearMsgAreaBtn = new JMenuItem("Clear Message Area");  //menu button to be used to clear messages in message area of the game panel
		clearMsgAreaBtn.addActionListener(new ClearMsgAreaBtn());  
		messageTab.add(clearMsgAreaBtn);
		
		JMenuItem clearChatAreaBtn = new JMenuItem("Clear Chat Area");  //menu button to be used to clear messages in chat area of the game panel
		clearChatAreaBtn.addActionListener(new ClearChatAreaBtn());
		messageTab.add(clearChatAreaBtn);
		
		menuBar.add(gameTab);
		menuBar.add(messageTab);
		frame.setJMenuBar(menuBar);   //added the menu bar into the jframe
		
		
		//GridLayout(rows,columns)
		
		//main jpanel holding the card panel, msgArea and chatArea
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new GridLayout(1,2));
		
		//panel holding the 4 players' cards and the cards on table
		bigTwoPanel = new BigTwoPanel();
		
		//add the player cards panel (bigTwoPanel) to the master panel of the game
		masterPanel.add(bigTwoPanel);

		
		//panel that will hold both the msgArea and the chatArea text areas
		JPanel gameMessage = new JPanel();
//		gameMessage.setLayout(new BoxLayout(gameMessage,BoxLayout.Y_AXIS)); //using box layout for this panel
		gameMessage.setLayout(new GridLayout(2,1)); //using box layout for this panel
		
		
		msgArea = new JTextArea();
		//System.out.println("burvvvvv= " + frame.getWidth());
		msgArea.setFont(new Font("Serif", Font.PLAIN, 16));
		msgArea.setLineWrap(true);
		msgArea.setWrapStyleWord(true);
		msgArea.setEditable(false); // set chatArea non-editable
		msgArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//adding a scroller to the msgArea to allow for scrolling
		JScrollPane scroll= new JScrollPane(msgArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		gameMessage.add(scroll);  //adding the message area to the panel that will hold both text areas
		
		printMsg(" Player Move Messages: \n");

		//text area that will display all chat messages of players
		chatArea = new JTextArea();
		chatArea.setFont(new Font("Serif", Font.PLAIN, 16));
		chatArea.setEditable(false);
		chatArea.setBorder(BorderFactory.createLineBorder(Color.black));
		chatArea.append(" Player's Chat Messages: \n");
		JScrollPane scroll2= new JScrollPane(chatArea);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		gameMessage.add(scroll2); //adding the message area to the panel that will hold both text areas
		
		//adding the panel holding both text areas to the masterPanel
		masterPanel.add(gameMessage);
		
		JPanel footer = new JPanel();  //main footer panel- this will hold the Pass and Play buttons and chatInput area
		footer.setLayout(new GridLayout(1,2));
		
		JPanel footerLeft = new JPanel();  //this will hold both the pass and play buttons
		footerLeft.setLayout(new BoxLayout(footerLeft,BoxLayout.X_AXIS));
		playButton = new JButton("Play");  //play button
		playButton.setPreferredSize(new Dimension(100,30));  
		playButton.addActionListener(new PlayButtonListener());  //add button listener to listen when the button is clicked
		
		footerLeft.add(playButton);  
		
		passButton = new JButton("Pass"); //pass button
		passButton.setPreferredSize(new Dimension(100,30));
		passButton.addActionListener(new PassButtonListener());  //add button listener to listen when the button is clicked
	
		footerLeft.add(passButton);
		
		//added the buttons to the footer panel
		footer.add(footerLeft);
		
		JPanel footerRight = new JPanel();  
		footerRight.setLayout(new BoxLayout(footerRight,BoxLayout.X_AXIS));
		JLabel footLabel = new JLabel("Message: ");  //adding label for the message chat input region
		
		footerRight.add(footLabel);  //adding the label to the right side of the footer region
		
		chatInput = new JTextField();  //setting up chat input region to take in chat inputs from users
		chatInput.setPreferredSize(new Dimension(400,30));
		chatInput.addActionListener(new MessageSendListener());  //adding a send listener so takes in the message inputs when the user presses enter on their keyboard
		
		footerRight.add(chatInput);  //adding the message area to the right bottom corner of the gui
		
		footer.add(footerRight);
		
		frame.pack();
		frame.add(masterPanel);
		frame.add(footer,BorderLayout.SOUTH);
		
		frame.setVisible(true);
		
//		System.out.println("frame height and widthhhhhhh" + frame.getHeight() + " " + frame.getWidth());
		
		
		

	}
	

	private final static int MAX_CARD_NUM = 13;   //maximum number of cards
	private ArrayList<CardGamePlayer> playerList;  //array list to store the information of the players and their cards
	private ArrayList<Hand> handsOnTable;  //array list to store the hands played on the table (all valid hands only)
	
	private BigTwo game;  //store the game instance for this iteration
	private boolean[] selected = new boolean[MAX_CARD_NUM];  //store boolean indication of whether a card has been selected by the user or not
	private int activePlayer = -1;  //holds the number indicating the current active player playing the game
	private JFrame frame;  //the main JFrame for the game
	private JPanel bigTwoPanel;  //the panel holding all cards and avatars
	private JButton playButton;  //button to play a hand of cards
	private JButton passButton;  //button for passing
	private JTextArea msgArea;  //holds all the messages of the hands played during game and player turns
	private JTextArea chatArea;  //player chat messages are displayed here
	private JTextField chatInput;  //players input their chat messages here
	private final static String newline = "\n"; 
	
	private Image[][] cardImages = new Image[4][13];  //an array to hold the images of all the cards in the game
	private String[][] cardImageNames = new String[4][13];  //holds the names of the cards
	private Image[] playerAvatars = new Image[4];  //holds the images of all the avatars in the game
	private Image backOfCards = new ImageIcon("cards/b.gif").getImage();  //holds the image of the back of the cards
	private int frameHeight;
	private int frameWidth;
	private String[] playerNames = {"Player 0","Player 1","Player 2","Player 3"};  //holds all players names (for printing purposes)
	private boolean mouseClickDetect = true;  //used to check if the panel is enabled or disabled and whether mouse click should be detected or not
	private boolean gameStatus;
	
	
	/**
	 * A method for setting the index of the active player (i.e., the player having control of the GUI).
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= playerList.size()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer; 
		}
		
		
	}
	
	/**
	 * Resets the array of selected cards to all false so no cards are selected.
	 */
	private void resetSelected() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
	}
	
	/**
	 * Returns an array of indices of the cards selected through the GUI.
	 * 
	 * @return an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
	private int[] getSelected() {
		int count = 0;
		
		for (int i = 0; i < selected.length; ++i) {
			if (selected[i] == true) {
				count += 1;
				
			}
		}
		int[] cardsSelected = new int[count];
		int index = 0;
		//find how many cards are selected and select those and put in an array to pass it for selected
		if (count > 0) {
			for (int i = 0; i < selected.length; ++i) {
				if (selected[i] == true) {
					cardsSelected[index] = i;
					index += 1;
				}
			}
		}
		return cardsSelected;
	}
	
	
	/**
	 * Repaints the GUI to refresh the JFrame and show the changes(if any).
	 */
	public void repaint() {
		bigTwoPanel.removeAll();
		bigTwoPanel.repaint();
	}
	
	/**
	 * Prints in-game messages in the game message area on the top-right of the GUI interface.
	 */
	public void printMsg(String msg) {
		msgArea.append(msg);
	}
	
	/**
	 * Can be called to clear all the messages in the message area of the GUI on the top-right.
	 */
	public void clearMsgArea() {
		msgArea.setText(null);
		msgArea.append(" Player Move Messages: \n");
	}
	
	/**
	 * Clears all the chat messages sent by the players in the bottom right region of the JFrame.
	 */
	public void clearChatArea() {
		chatArea.setText(null);
		chatArea.append(" Player's Chat Messages: \n");
	}
	
	/**
	 * A method for resetting the GUI. You should (i) reset the list of selected cards; (ii) clear the message area; and (iii) enable user interactions.
	 */
	public void reset() {
		resetSelected();
		clearMsgArea();
		clearChatArea();
		repaint();
		enable();
	}
	
	/**
	 * To make the chat enabled and disabled by calling from the BigTwoClient.
	 */
	public void enableChat() {
		chatInput.setEnabled(true);
	}
	
	/**
	 * A method for enabling user interactions with the GUI. You should (i) enable the "Play" button and "Pass" button (i.e., making them clickable); <br>
	 * (ii) enable the chat input; and (iii) enable the BigTwoPanel for selection of cards through mouse clicks.
	 */
	public void enable() {
		bigTwoPanel.setEnabled(true);
		mouseClickDetect = true;
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		chatInput.setEnabled(true);
	}
	
	/**
	 * a method for disabling user interactions with the GUI. You should (i) disable the "Play" button and "Pass" button (i.e., making them not clickable); <br>
	 * (ii) disable the chat input; and (ii) disable the BigTwoPanel for selection of cards through mouse clicks.
	 */
	public void disable() {
		bigTwoPanel.setEnabled(false);
		mouseClickDetect = false;
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		chatInput.setEnabled(false);
	}
	
	/**
	 * A method for prompting the active player to select cards and make his/her move. A message should be displayed in the message area showing it is the active player’s turn.
	 */
	public void promptActivePlayer() {
		
//		System.out.print("active player = " + activePlayer + ": ");
//		playerList.get(activePlayer).getCardsInHand().print();
//		
//		System.out.print("player0 = ");
//		playerList.get(0).getCardsInHand().print();
//		System.out.print("player1 = ");
//		playerList.get(1).getCardsInHand().print();
//		System.out.print("player2 = ");
//		playerList.get(2).getCardsInHand().print();
//		System.out.print("player3 = ");
//		playerList.get(3).getCardsInHand().print();
//		System.out.println("rank of card = " + playerList.get(activePlayer).getCardsInHand().getCard(0).rank);
			
		resetSelected();
		bigTwoPanel.repaint();
		
	}
	
	/**
	 * A method for storing all the card images in an image array that will be used draw the cards on the game panel.<br>
	 * Also store all the player avatar images in another array.
	 */
	public void go() {
		
		
//		msgArea.append(" " + playerNames[activePlayer] + "'s turn: \n");  //print current player name
//		printMsg(" Player Move Messages: \n");

		
		//storing all card names
		String[] rankManager = {"a","2","3","4","5","6","7","8","9","t","j","q","k"};
		String[] suitManager = {"d","c","h","s"};
		

		//gets all the card images and stores in a 2d array
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 13; ++j) {
				
				cardImages[i][j] = new ImageIcon("cards/" + rankManager[j] + suitManager[i] + ".gif").getImage();
				cardImageNames[i][j] = "cards/" + rankManager[j] + suitManager[i] + ".gif";

			}
		}
		
		
		//gets all player avatars and stores in this array
		playerAvatars[0] = new ImageIcon("avatars/player0.png").getImage();
		playerAvatars[1] = new ImageIcon("avatars/player1.png").getImage();
		playerAvatars[2] = new ImageIcon("avatars/player2.png").getImage();
		playerAvatars[3] = new ImageIcon("avatars/player3.png").getImage();
		
//		System.out.println(Arrays.deepToString(cardImageNames));
		
		
	}
	
	/**
	 * Enters this message into the chat area of the GUI.
	 * @param msg The string containing the information to be displayed in chat area.
	 */
	public void chatAreaWriter(String msg) {
		chatArea.append(" " + msg);
	}
	
	/**
	 * An inner class to listen to whether the user presses enter to input any text into the chat area or not.
	 * @author wasiflatifhussain
	 *
	 */
	class MessageSendListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String text = chatInput.getText();
			if (text.length() != 0) {  //only if the user enters something in the text area, then we send the data into chat area
				game.sendChatMessage(text + "\n");
//				chatArea.append(" " + playerNames[activePlayer]+": " + text + newline);
				chatInput.setText("");
			}

		}
	}
	
	

	
	/**
	 * An inner class that extends the JPanel class and implements the MouseListener interface. Overrides the paintComponent() method inherited from the JPanel <br>
	 * class to draw the card game table. Implements the mouseReleased() method from the MouseListener interface to handle mouse click events.
	 * @author wasiflatifhussain
	 *
	 */
	@SuppressWarnings("serial")
	class BigTwoPanel extends JPanel implements MouseListener{
		public BigTwoPanel() {
			setBackground(new Color(101, 199, 101));  //setting color of the bigTwoPanel
			setLayout(new GridLayout(5,1));
			this.addMouseListener(this);  //adding mouse listener to the bigTwoPanel to detect where the user clicks	
			}

		/**
		 * Overrides the paint component to draw the images onto the panel at specific positions and basically draws the entire card panel
		 */
		public void paintComponent(Graphics g) {  
			super.paintComponent(g);  
			if (game.getLocalPlayerIdx() == activePlayer && gameStatus == true) {
				game.enableGUI();
				chatInput.setEnabled(true);
			}
			else {
				game.disableGUI();
				chatInput.setEnabled(true);
			}
			Graphics2D g2d = (Graphics2D) g; //makes a graphics2D object to draw images 
			
			int eachPanelHeight = this.getHeight()/5;  //gets the panel height for computation of card positions and sizes
			int eachPanelWidth = this.getWidth();  //gets the panel weight for computation of card positions and sizes
//			System.out.println("sqrsqr = " + eachPanelHeight);
//			System.out.println("sqrsqr = " + eachPanelWidth);
			
			//the player names will shift this many positions based on panel size(along y-axis)
			double[] playerNamePositions = {0.12*eachPanelHeight,((0.12*eachPanelHeight)+eachPanelHeight),((0.12*eachPanelHeight)+eachPanelHeight+eachPanelHeight),((0.12*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight),((0.12*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight+eachPanelHeight)};
//			System.out.println(Arrays.toString(playerNamePositions));
			
			//for avatars of players and for the cards positions when the cards are not selected (computed against the full panel size;
			//this determines the y-coordinate of the cards
			double[] playerAvatarPositions = {0.17361111*eachPanelHeight,((0.17361111*eachPanelHeight)+eachPanelHeight),((0.17361111*eachPanelHeight)+eachPanelHeight+eachPanelHeight),((0.17361111*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight), ((0.17361111*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight+eachPanelHeight)};
			//array with raised position coordinates
			//this determines the y-coordinate of the cards when they are selected or pressed
			double[] raisedPositions = {0.07*eachPanelHeight,((0.07*eachPanelHeight)+eachPanelHeight),((0.07*eachPanelHeight)+eachPanelHeight+eachPanelHeight),((0.07*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight)};
			
			//for the images of cards, this is the xCoordinate multipliers
			double[] xCoordinate = {0.20, 0.25, 0.30, 0.35, 0.40, 0.45, 0.50, 0.55, 0.60, 0.65, 0.70, 0.75, 0.80};  //contains all x coordinate multipliers
			//double[] xCoordinate = {0.20, 0.2, 0.28, 0.32, 0.36, 0.40, 0.44, 0.48, 0.52, 0.56, 0.60, 0.64, 0.68};  //contains all x coordinate multipliers
			
			//this loop uses computations against the panel height and weight to determine exact positions where the cards should be placed on the panel
			for (int i = 0; i < 4; ++i) {
				if (i == game.getLocalPlayerIdx()) {
					g2d.setColor(Color.red);
					g2d.drawString("You", (int)(0.0166666667 * eachPanelWidth), (int)(playerNamePositions[i]));   //name string
					g2d.setColor(Color.black);
					g2d.drawImage(playerAvatars[i], (int)((0.025) * eachPanelWidth), (int)(playerAvatarPositions[i]), (int)(0.13 * eachPanelWidth), (int)(0.70 * eachPanelHeight), this);
			
					for (int j = 0; j < playerList.get(i).getNumOfCards(); ++j) {
					
						if (selected[j] == false) {  //if the card has not been selected use this parameter to display the cards
							g2d.drawImage(cardImages[playerList.get(i).getCardsInHand().getCard(j).suit][playerList.get(i).getCardsInHand().getCard(j).rank],
									(int)(xCoordinate[j] * eachPanelWidth), (int)(playerAvatarPositions[i]), (int)(0.13 * eachPanelWidth), (int)(0.70 * eachPanelHeight), this);
						}
						else if (selected[j] == true) {  //if the card has been selected already by player, use this parameter for display (raised)
							g2d.drawImage(cardImages[playerList.get(i).getCardsInHand().getCard(j).suit][playerList.get(i).getCardsInHand().getCard(j).rank],
									(int)(xCoordinate[j] * eachPanelWidth), (int)(raisedPositions[i]), (int)(0.13 * eachPanelWidth), (int)(0.70 * eachPanelHeight), this);
						}

					}

				}
				else {  //if the player is not the active player, the playr's cards should not be shown but the back of cards should be shown instead

					if (playerList.get(i).getName()!=null) {  //if name available, it will print the name of the player
						g2d.drawString(playerList.get(i).getName(), (int)(0.0166666667 * eachPanelWidth), (int)(playerNamePositions[i]));   //name string
					}
					else {  //if player has not joined yet, this name shows
						g2d.drawString(playerNames[i], (int)(0.0166666667 * eachPanelWidth), (int)(playerNamePositions[i]));   //name string
					}

					//System.out.println("yoooo = " + playerList.get(i).getName());
					
					g2d.drawImage(playerAvatars[i], (int)((0.025) * eachPanelWidth), (int)(playerAvatarPositions[i]), (int)(0.13 * eachPanelWidth), (int)(0.70 * eachPanelHeight), this);
					for (int j = 0; j < playerList.get(i).getNumOfCards(); ++j) {
//						g2d.drawImage(cardImages[playerList.get(i).getCardsInHand().getCard(j).suit][playerList.get(i).getCardsInHand().getCard(j).rank],
//								(int)(xCoordinate[j] * eachPanelWidth), (int)(playerAvatarPositions[i]), (int)(0.13 * eachPanelWidth), (int)(0.70 * eachPanelHeight), this);
						g2d.drawImage(backOfCards,
								(int)(xCoordinate[j] * eachPanelWidth), (int)(playerAvatarPositions[i]), (int)(0.13 * eachPanelWidth), (int)(0.70 * eachPanelHeight), this);
					}
				}

			}
			//draws lines to separate player cards (for beauty purposes)
			g2d.drawLine(0, eachPanelHeight, eachPanelWidth, eachPanelHeight);
			g2d.drawLine(0, eachPanelHeight*2, eachPanelWidth, eachPanelHeight*2);
			g2d.drawLine(0, eachPanelHeight*3, eachPanelWidth, eachPanelHeight*3);
			g2d.drawLine(0, eachPanelHeight*4, eachPanelWidth, eachPanelHeight*4);
			
			
			//printing cards on the table
			Hand lastHandOnTable = (handsOnTable.isEmpty()) ? null : handsOnTable.get(handsOnTable.size() - 1);  //try to get the last hand played on the table
			double[] tableCoordinate = {0.20, 0.35, 0.50, 0.65, 0.80};
			if (lastHandOnTable != null) {  //if a last hand exists then display it and the name of the player who played it
				g2d.drawString("Hand played by: " + playerList.get(game.lastPlayerIdx).getName(), (int)(0.0166666667 * eachPanelWidth), (int)(playerNamePositions[4]));   //name string
				for (int k = 0; k < lastHandOnTable.size(); ++k) {
					g2d.drawImage(cardImages[lastHandOnTable.getCard(k).suit][lastHandOnTable.getCard(k).rank],
							(int)(tableCoordinate[k] * eachPanelWidth), (int)(playerAvatarPositions[4]), (int)(0.13 * eachPanelWidth), 
							(int)(0.70 * eachPanelHeight), this);
				}
			}
			else {  //else just display an empty table
				g2d.drawString("Table", (int)(0.0166666667 * eachPanelWidth), (int)(playerNamePositions[4]));   //name string
			}

		}


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		/**
		 * Checks for mouse released instances to see if mouse clicked or not.
		 * If clicked, this checks which position has the user clicked and perform computations
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (mouseClickDetect == true) {
//				System.out.println("Who is the active player bro? =  " + activePlayer);
				
				//this variable checks to make sure that during each mouse click, ONLY ONE CARD is selected and repaints UI accordingly
				//this avoids miss clicks and prevents from multiple cards being selected during each mouse clicks
				boolean moveMade = false;
				
				
				int xtouched = e.getX();  //gets the x-coordinates of the point where the user mouse clicked
				int ytouched = e.getY();  //gets the y-coordinates of the point where the user mouse clicked
				
//				System.out.println("x possss = " + xtouched);
//				System.out.println("y possss = " + ytouched);
				
				int eachPanelHeight = this.getHeight()/5;  //compute panel height and width for checking if any card selected
				int eachPanelWidth = this.getWidth();
				
//				System.out.println("moused height = " + eachPanelHeight);
//				System.out.println("moused width = " + eachPanelWidth);
				
				
				//holds all the y-coordinate points for each player's set of cards when card is NOT selected
				double[] playerAvatarPositions = {0.17361111*eachPanelHeight,((0.17361111*eachPanelHeight)+eachPanelHeight),((0.17361111*eachPanelHeight)+eachPanelHeight+eachPanelHeight),((0.17361111*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight)};
				//holds all the y-coordinate points for each player's set of cards when card IS selected
				double[] raisedPositions = {0.07*eachPanelHeight,((0.07*eachPanelHeight)+eachPanelHeight),((0.07*eachPanelHeight)+eachPanelHeight+eachPanelHeight),((0.07*eachPanelHeight)+eachPanelHeight+eachPanelHeight+eachPanelHeight)};
				//x-coordinate values for checking
				double[] xCoordinate = {0.20, 0.25, 0.30, 0.35, 0.40, 0.45, 0.50, 0.55, 0.60, 0.65, 0.70, 0.75, 0.80};

				//when a card has been selected, there is a different between the elevated and non-elevated position y-coordinates
				//this is computed and stored here
				double elevationGap = playerAvatarPositions[0]-raisedPositions[0];
				
				//gets the number of cards in the hand of the current active player
				int lengthOfLoop = playerList.get(activePlayer).getNumOfCards();
				
				//always checks the last card in the players hand first as that card if fully facing the player while other cards are overlapped 
				if (selected[playerList.get(activePlayer).getNumOfCards()-1] == false) {
					
					//checks the area where this card sits and sees if the x and y coordinates of the selected point falls in this area or not
					if ((xtouched >= (int)(xCoordinate[playerList.get(activePlayer).getNumOfCards()-1] * eachPanelWidth)) && (xtouched <= (int)((xCoordinate[playerList.get(activePlayer).getNumOfCards()-1] * eachPanelWidth) + (0.13 * eachPanelWidth))) 
							&& (ytouched >= (int)playerAvatarPositions[activePlayer]) && (ytouched <= (int)(playerAvatarPositions[activePlayer] + 
									(0.70 * eachPanelHeight)))) {
						selected[playerList.get(activePlayer).getNumOfCards()-1] = true;
						repaint();
						moveMade = true;  //if last card has been selected make this true to prevent any more cards from being selected (miss-click handling)
						
					}
						
				}
				else if (selected[playerList.get(activePlayer).getNumOfCards()-1] == true) {
					//checks the area where this card sits and sees if the x and y coordinates of the selected point falls in this area or not
					if ((xtouched >= (int)(xCoordinate[playerList.get(activePlayer).getNumOfCards()-1] * eachPanelWidth)) && (xtouched <= (int)((xCoordinate[playerList.get(activePlayer).getNumOfCards()-1] * eachPanelWidth) + (0.13 * eachPanelWidth))) 
							&& (ytouched >= (int)raisedPositions[activePlayer]) && (ytouched <= (int)(raisedPositions[activePlayer] + (0.70 * 
									eachPanelHeight)))) {
						selected[playerList.get(activePlayer).getNumOfCards()-1] = false;
						repaint();
						moveMade = true;  //if last card has been selected make this true to prevent any more cards from being selected (miss-click handling)
						
						
					}
				}
				//if last card has not been selected, runs a loop and checks all other cards
				for (int i = lengthOfLoop - 2; i >= 0; --i) {
					//if the card has not been selected yet, and move has not been made yet, this if clause is checked
					if (selected[i] == false && moveMade == false) {
						if (selected[i+1] == false) {  //if the card beside the inspected card is not selected/not in a raised position, this coordinates are checked
							if ((xtouched >= (int)(xCoordinate[i] * eachPanelWidth)) && (xtouched <= (int)(xCoordinate[i+1] * eachPanelWidth)) && 
									(ytouched >= (int)playerAvatarPositions[activePlayer]) && (ytouched <= (int)(playerAvatarPositions[activePlayer] + 
											(0.70 * eachPanelHeight)))) {
//								System.out.println("value of i in l1= " + i);
								selected[i] = true;
								repaint();
								moveMade = true;   //if last card has been selected make this true to prevent any more cards from being selected (miss-click handling)
								
							}
						}
						//if the card beside the inspected card is selected/in an elevated position, then these coordinates are checked
						//as now a new region is exposed of the inspected card which was previously hidden by un-elevated neighboring card
						else if (selected[i+1] == true) {
							if ((xtouched >= (int)(xCoordinate[i] * eachPanelWidth)) && (xtouched <= (int)(xCoordinate[i+1] * eachPanelWidth)) && 
									(ytouched >= (int)playerAvatarPositions[activePlayer]) && (ytouched <= (int)(playerAvatarPositions[activePlayer] + 
											(0.70 * eachPanelHeight)))) {
//								System.out.println("value of i in l2= " + i);
								selected[i] = true;
								repaint();
								moveMade = true;   //if last card has been selected make this true to prevent any more cards from being selected (miss-click handling)
								
							}
							else if ((xtouched >= (int)(xCoordinate[i] * eachPanelWidth)) && (xtouched <= (int)((xCoordinate[i] * eachPanelWidth) + (0.13 * eachPanelWidth))) && 
									(ytouched >= (int)((playerAvatarPositions[activePlayer] + (0.70 * eachPanelHeight))-elevationGap)) && 
									(ytouched <= (int)(playerAvatarPositions[activePlayer] + 
											(0.70 * eachPanelHeight)))) {

//								System.out.println("value of i in l3= " + i);
								selected[i] = true;
								repaint();
								moveMade = true;   //if last card has been selected make this true to prevent any more cards from being selected (miss-click handling)
							}
						}

						
					}
					
					//if the inspected card is already selected/elevated, then this if clause runs
					else if (selected[i] == true && moveMade == false) {
						if (selected[i+1] == true) {  //if the neighbor card is also selected/elevated, this set of x cooridinates are checked
							if ((xtouched >= (int)(xCoordinate[i] * eachPanelWidth)) && (xtouched <= (int)(xCoordinate[i+1] * eachPanelWidth)) && 
									(ytouched >= (int)raisedPositions[activePlayer]) && (ytouched <= (int)(raisedPositions[activePlayer] + 
											(0.70 * eachPanelHeight)))) {
//								System.out.println("value of i in l4= " + i);
								selected[i] = false;
								repaint();
								moveMade = true;
							}
						}
						else if (selected[i+1] == false) {  //if neighbor card is not elevated, this is used as now there is an extra region at the top of the current card that is also exposed
							if ((xtouched >= (int)(xCoordinate[i] * eachPanelWidth)) && (xtouched <= (int)(xCoordinate[i+1] * eachPanelWidth)) && 
									(ytouched >= (int)raisedPositions[activePlayer]) && (ytouched <= (int)(raisedPositions[activePlayer] + 
											(0.70 * eachPanelHeight)))) {
//								System.out.println("value of i in l5= " + i);
								selected[i] = false;
								repaint();
								moveMade = true;
							}
							else if ((xtouched >= (int)(xCoordinate[i] * eachPanelWidth)) && (xtouched <= (int)((xCoordinate[i] * eachPanelWidth) + (0.13 * eachPanelWidth))) && 
									(ytouched >= (int)raisedPositions[activePlayer]) && (ytouched <= 
											(int)raisedPositions[activePlayer] + elevationGap)) {
//								System.out.println("value of i in l6= " + i);
								selected[i] = false;
								repaint();
								moveMade = true;
								
							}
							
						}

					}
				}
			}
			
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		

	}
	
	
	/**
	 * An inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click <br>
	 * events for the "Play" button. When the "Play" button is clicked, you should call the makeMove() method of your BigTwo object to make a move.
	 * @author wasiflatifhussain
	 *
	 */
	class PlayButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			int[] cardsToSend = getSelected();
			if (cardsToSend.length == 0) {
				printMsg(" No cards have been selected! \n");
				printMsg(" Player " + activePlayer + "'s turn: \n");
				promptActivePlayer();
			}
			else {
				game.makeMove(activePlayer,cardsToSend);
			}

			
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click <br>
	 * events for the "Pass" button. When the "Pass" button is clicked, you should call the makeMove() method of your BigTwo object to make a move.
	 * @author wasiflatifhussain
	 *
	 */
	class PassButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			//send a null value everytime player clicks pass
			int[] cardsToSend = null;
			game.makeMove(activePlayer, cardsToSend);
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click <br>
	 * events for the "Restart" menu item. When the "Restart" menu item is selected, you should (i) create a new BigTwoDeck object and call its shuffle() method; and (ii) call <br>
	 * the start() method of your BigTwo object with the BigTwoDeck object as an argument.
	 * @author wasiflatifhussain
	 *
	 */
	class ConnectMenuListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			
			if (game.sendTesterMessage("You are already connected to the server!")) {  //send a message to the server to test if the player is connected
																					   //to the server or not. if not connected, it returns false and true otherwise
				System.out.println("Working!");
			}
			else { //if it returns false means that the client is disconnected, so connect the client 
				game.connectUser();
				//printMsg(" You are already connected to the server!\n");
			}
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click <br>
	 * events for the "Quit" menu item. When the "Quit" menu item is selected, it should terminate your application.
	 * @author wasiflatifhussain
	 *
	 */
	class QuitMenuItemListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
//			game.sendQuitMessageToClient();
			System.exit(0);
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click <br>
	 * events for the "Clear Message Area" menu item. When the "Clear Message Area" menu item is selected, it should clear all messages in the messages area.
	 * @author wasiflatifhussain
	 *
	 */
	class ClearMsgAreaBtn implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			clearMsgArea();
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click <br>
	 * events for the "Clear Chat Area" menu item. When the "Clear Chat Area" menu item is selected, it should clear all messages in the messages area.
	 * @author wasiflatifhussain
	 *
	 */
	class ClearChatAreaBtn implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			clearChatArea();
		}
	}
	
	/**
	 * Set the game status to true or false as called by the client.
	 * @param status A boolean value that stores true or false.
	 */
	public void setgameStatus(boolean status) {
		this.gameStatus = status;
	}
	
	/**
	 * Getter for the game status boolean variable.
	 * @return A boolean value indicating either true or false.
	 */
	public boolean getgameStatus() {
		return this.gameStatus;
	}
	
	/**
	 * Makes the JOptionPane to display the game ending message.
	 * Displays a different pane for the winner and for the losers.
	 * @param toPrint A String holding the information about the winners and losers.
	 */
	public void showEndingMessage(String toPrint) {
		game.disableGUI();  //disable game gui to stop client interactions until they click "OK"
		setgameStatus(false);
		if (game.lastPlayerIdx == game.getLocalPlayerIdx()) {
			JOptionPane.showMessageDialog(null, toPrint, "You Win!", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, toPrint, "You Lose!", JOptionPane.INFORMATION_MESSAGE);
		}
		clearMsgArea();
		game.startNewGame();

		
		//maybe shud set the game status to false
		//also work on the "ok" part
	}
}