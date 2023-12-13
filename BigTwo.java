import java.util.*;

import javax.swing.JOptionPane;

/**
 * The BigTwo class implements the CardGame interface and is used to model a Big Two card game. It has private instance variables for storing the number of players,<br> 
 * a deck of cards, a list of players, a list of hands played on the table, an index of the current player, and a user interface. <br>
 * The BigTwo class has the following public methods- 
 * <ul>
 * 	<li>int getNumOfPlayers()</li>
 * 	<li>Deck getDeck()</li>
 * 	<li>ArrayList type-CardGamePlayer name-getPlayerList()</li>
 * 	<li>ArrayList type- Hand name-getHandsOnTable()</li>
 * 	<li>int getCurrentPlayerIdx()</li>
 *  <li>void start(Deck deck)</li>
 *  <li>void checkMove(int playerIdx, int[] cardIdx)</li>
 *  <li>boolean endOfGame()</li>
 * </ul>
 * It also has the following public static methods-
 * <ul>
 * 	<li>void main(Strings[] args)</li>
 * 	<li>Hand composeHand(CardGamePlayer player, CardList cards) </li>
 * </ul>
 * @author wasiflatifhussain
 *
 */

public class BigTwo implements CardGame {
	
	
	/**
	 * Default constructor for BigTwo class. <br>
	 * It does the following tasks-
	 * <ol>
	 * 	<li>Create 4 players and add them to the player list.</li> 
	 * 	<li>Create a BigTwoUI object for providing the user interface.</li>
	 * </ol>
	 */
	public BigTwo () {
		
		handsOnTable = new ArrayList<Hand>();   //instantiate a new empty handOnTable array to store the legal hands played on table
		playerList = new ArrayList<CardGamePlayer>();  //instantiate an arrayList to store each player and their cards
		
		CardGamePlayer p1 = new CardGamePlayer();  //declared player1
		playerList.add(p1);
		CardGamePlayer p2 = new CardGamePlayer();  //declared player2
		playerList.add(p2);
		CardGamePlayer p3 = new CardGamePlayer();  //declared player3
		playerList.add(p3);
		CardGamePlayer p4 = new CardGamePlayer();  //declared player4
		playerList.add(p4);

		this.ui = new BigTwoGUI(this);  //made the ui object to be used to play the game
		this.client = new BigTwoClient(this,this.ui);
	}
	
	private int numOfPlayers;   //private variable to store the number of players in the game
	private Deck deck;  //private variable to carry the deck of cards
	private ArrayList<CardGamePlayer> playerList; //= new ArrayList<CardGamePlayer>();  //private variable for an ArrayList that will store each CardGamePlayer and their information
																					 //such as cards,player index etc.
	private ArrayList<Hand> handsOnTable; //= new ArrayList<Hand>();    //private variable to store all the valid hands that are played on the table during each big two game
	private int currentPlayerIdx;  //private variable to store the index of the current player at any point of the game
	private BigTwoGUI ui;  //private variable to be used to declare a new UI object for each new game
	private BigTwoClient client;
	

	/**
	 * A getter method to get number of players for the private variable numOfPlayers.
	 * @return The number of players playing in the current instance of the game.
	 */
	public int getNumOfPlayers () {
		return this.numOfPlayers;
	}
	
	/**
	 * A getter method to retrieve the value from the private variable deck.
	 * @return The deck of cards being used in the current game.
	 */
	public Deck getDeck () {
		return this.deck;    
	}
	
	/**
	 * A getter method to retrieve the value from the private variable playerList.
	 * @return The list of the players of are playing in the current game. Each element is of type <i>CardGamePlayer</i>, storing the information about the players.
	 */
	public ArrayList<CardGamePlayer> getPlayerList () {
		return this.playerList;    
	}
	
	/**
	 * A getter method to retrieve the value from the private variable handsOnTable.
	 * @return An ArrayList containing all the valid hands that have been played during the current game. 
	 */
	public ArrayList<Hand> getHandsOnTable () {
		return this.handsOnTable;  
	}
	
	/**
	 * A getter method to retrieve the value from the private variable currentPlayerIdx.
	 * @return The current player's index during any position in the game.
	 */
	public int getCurrentPlayerIdx () {
		return this.currentPlayerIdx;
	}
	
	/**
	 * Stores the index of the player whp played the last valid hand during a big two game.
	 */
	public int lastPlayerIdx;   //self declared public to store the index  of the player who played the last valid hand during a game 
	
	/**
	 * A method for starting/restarting the game with a given shuffled deck of cards. It does the following-
	 * <ol>
	 * 		<li>Remove all the cards from the players as well as from the table; </li>
	 * 		<li>Distribute the cards to the players; </li>
	 * 		<li>Identify the player who holds the Three of Diamonds;</li> 
	 * 		<li>Set both the currentPlayerIdx of the BigTwo object and the activePlayer of the BigTwoUI object to the index of the player who holds the Three of Diamonds; 
	 * 		<li>Call the repaint() method of the BigTwoUI object to show the cards on the table; </li>
	 * 		<li>Call the promptActivePlayer() method of the BigTwoUI object to prompt user to select cards and make his/her move.</li>
	 * </ol>
	 * @param deck This is the deck of shuffled cards that will be distributed among the players and used in the game.
	 */
	public void start (Deck deck) {
		
		if (handsOnTable.size() != 0) {
			int length = handsOnTable.size();
			for (int i = 0; i < length; ++i) {
				handsOnTable.remove(0);
			}
//			for (Hand hand: handsOnTable) {   //remove any cards that may be on the table
//				handsOnTable.remove(hand);
//			}
		}
		
		for (int i = 0; i < playerList.size(); ++i) {   //giving 13 cards to each player
			playerList.get(i).removeAllCards();  //remove player cards and clear their hand
			int limitStart = 0;
			int limitEnd = 0;
			if (i == 0) {
				limitStart = 0;  //player 1 gets cards idx 0 to idx 12 (from the shuffled deck)
				limitEnd = 13;
			}
			else if (i == 1) {
				limitStart = 13;  //player 2 gets cards idx 13 to idx 25 (from the shuffled deck)
				limitEnd = 26;
			}
			else if (i == 2) {
				limitStart = 26;  //player 3 gets cards idx 26 to idx 38 (from the shuffled deck) 
				limitEnd = 39;
			}
			else if (i == 3) {
				limitStart = 39;  //player 4 gets cards idx 39 to idx 51 (from the shuffled deck)
				limitEnd = 52;
			}
			for (int j = limitStart; j < limitEnd; ++j) {
				playerList.get(i).addCard(deck.getCard(j));  //adding 12 cards to each player
				if (deck.getCard(j).suit == 0) {
					if (deck.getCard(j).rank == 2) {
						currentPlayerIdx = i;   //finding the player with the 3 of diamonds
						lastPlayerIdx = currentPlayerIdx;  //making this player the last player for the first round of the game (check the method- checkMove for details
						ui.setActivePlayer(i);   //setting the player with the 3 of diamond as the active player
					}
				}
			}
			playerList.get(i).sortCardsInHand();  //sorting cards in each players hand according to card strength(rank-wise)
		}
		
		firstMove = true;  //make first move true always when we run start game (especially for the restart function of the game)
		
		ui.printMsg(" " + playerList.get(currentPlayerIdx).getName() + "'s Turn:\n");
		//ui.go();    //to make the ui form
		ui.repaint();  //repaint the ui for a new print of the game layout
		ui.promptActivePlayer();  //prompt the current player to select/choose a hand of cards
		
		
		
	}
	
	/**
	 * A method for making a move by a player with the specified index using the cards specified by the list of indices. This method should be called <br>
	 * from the BigTwoUI after the active player has selected cards to make his/her move.
	 * @param playerIdx The index of the current player who played a selection of cards
	 * @param cardIdx An array containing the index of the cards from the player's hand which they want to play for this turn
	 */
	public void makeMove (int playerIdx, int[] cardIdx) {
		//this.checkMove(playerIdx, cardIdx);
		CardGameMessage message = new CardGameMessage(CardGameMessage.MOVE,playerIdx,cardIdx);
		client.sendMessage(message);
	}

	
	private Hand lastHand = null;  //self-declared private variable to store the last valid hand played 
	private boolean firstMove = true;  //self-declared private variable to store information about whether this move is the first move of the game or not
	
	
	/**
	 * A method for checking a move made by a player. This method should be called from the makeMove() method.
	 * @param playerIdx The index of the current player who played a selection of cards
	 * @param cardIdx An array containing the index of the cards from the player's hand which they want to play for this turn
	 */
	public void checkMove (int playerIdx, int[] cardIdx) {
		boolean legalMove = false;  //to declare whether a hand is legal or not
		
		//at first we will check if this is the first move of the game or not
		//the first move of the game must contain a valid hand having 3 of Diamonds in it
		if (firstMove == true) {
			if (cardIdx == null) {  //the first move must not be an empty/pass by the first player so we have to prevent that
				System.out.println(" Not a legal move!!!");
				ui.printMsg(" Not a legal move!!!\n");
				legalMove = false;
			}
			else {   //if the player does give a set of cards for first move, we have to check if the selected cards contains 3 of Diamonds or not
				CardList cards0 = new CardList();
				CardList cards1 = new CardList();
				cards1 = playerList.get(playerIdx).getCardsInHand();  //gets all of player's cards in their hand into this cardList to check against cardIdx[]
				for (int i = 0; i < cardIdx.length; ++i) {
					cards0.addCard(cards1.getCard(cardIdx[i]));  //checks cardIdx[] for the indexes of the cards from the cards1 list and puts those cards into the cards0 list
																 //cards0 list contains all the cards played as the hand by the player during this turn
				}
				if (cards0.contains(new BigTwoCard(0,2)) == true) {   //checking if 3 of Diamonds exists in the hand or not; true means the cards exists
					Hand played = composeHand(playerList.get(playerIdx),cards0);  //calls composeHand method to see if a valid hand of any type can be composed
																				  //with this hand of cards or not
					if (played == null) {  //if played == null means that no valid hand was able to be formed from the cards the player selected
										   //so ask the player to enter value again
						System.out.println(" Not a legal move!!!");
						ui.printMsg(" Not a legal move!!!\n");
						legalMove = false;
					}
					else {  //played != null means that a valid hand was able to be formed from the cards given
						lastPlayerIdx = playerIdx;  //store the index of this player as the lastPlayerIdx to be used in the next iteration for comparison against next hand of cards
						lastHand = played;  //store the hand of this player as the lastPlayerIdx to be used in the next iteration for comparison against next hand of cards
						
						handsOnTable.add(lastHand);    //adding the hand to table
						
						//printing the valid hand type and the hand on the terminal/console
						System.out.print("{");
						System.out.print(played.getType());
						System.out.print("} ");
						
//						Hand lastHandOnTable = (handsOnTable.isEmpty()) ? null : handsOnTable.get(handsOnTable.size() - 1);
						String cards = " [";
						for (int i = 0; i < played.size(); i++) {
							cards += played.getCard(i); 
							cards += "] ";
							if ( i < played.size() - 1) {
								cards += " [";
							}
						}
						ui.printMsg(" {" + played.getType() + "} ");
						ui.printMsg(cards + "\n");
						
//						ui.printMsg("{" + played.getType() + "}");
						
						played.print();
						
						playerList.get(playerIdx).removeCards(cards0);  //remove the cards the player played from their hand as they already put them on the table
						if (currentPlayerIdx == 3) {  //max index is 3 so reset index to 0
							currentPlayerIdx = 0;
						}
						else if (currentPlayerIdx < 3) {  //increment currentPlayerIdx by 1 to get the nextPlayer index for next iteration
							currentPlayerIdx += 1;
						}
						legalMove = true;  //as move was legal, make legalMove = true (to be used below for ui.repaint and prompting new input from next player
						firstMove = false;  //as first move successfully played, make firstMove false for this run of the game 
					} 
				}
				else {  //if 3 of Diamonds not present, the this is illegal move and player has to enter value again
					System.out.println(" Not a legal move!!!");
					ui.printMsg(" Not a legal move!!!\n");
					legalMove = false;
				}
				
				
			}
		}
		
		else if (cardIdx == null) {   //cardIdx == null means the player attempted to pass for his/her turn
			if (playerIdx != lastPlayerIdx) {  //if the currentPlayer index not equal to lastPlayerIdx means they are allowed to pass to accept pass
				System.out.println("{Pass}");
				ui.printMsg(" {Pass}\n");
				if (currentPlayerIdx == 3) {  //increment to the next player as currentPlayer
					currentPlayerIdx = 0;
				}
				else if (currentPlayerIdx < 3) {
					currentPlayerIdx += 1;
				}
				legalMove = true;  //as move is legal, declare it true
			}
			else {  //if current player's index and last player's index are same, they are not allowed to pass but must play any valid hand they want
				System.out.println(" Not a legal move!!!");
				ui.printMsg(" Not a legal move!!!\n");
				legalMove = false;
			}
		}
		
		//if a player tries to play a hand which has more number of cards than the current hand on the table, this is an 
		//illegal hand so legalMove should be make false and ask for a new userinput again
		//also this case is only true if the lastPlayerIdx != playerIdx; if the two index are same, this means all the other players
		//passed so now the player can play any valid hand so this is not applicable here
		else if (cardIdx != null && cardIdx.length != lastHand.size() && lastPlayerIdx != playerIdx) {
			ui.printMsg(" Not a legal move!!!\n");
			System.out.println(" Not a legal move!!!");
			legalMove = false;
		}
		
		else if (cardIdx != null) {
			if (lastPlayerIdx == playerIdx) {  //if current player and last player index same, as the can play any valid hand they want, so 
											   //we set the lastHand as null to take in their new hand as the new landhand (check the codes that follow)
				lastHand = null;
			}
			CardList cards0 = new CardList();
			CardList cards1 = new CardList();
			cards1 = playerList.get(playerIdx).getCardsInHand();  //gets all of player's cards in their hand into this cardList to check against cardIdx[]
			for (int i = 0; i < cardIdx.length; ++i) {
				cards0.addCard(cards1.getCard(cardIdx[i]));      //checks cardIdx[] for the indexes of the cards from the cards1 list and puts those cards into the cards0 list
				 												//cards0 list contains all the cards played as the hand by the player during this turn
			}
			Hand played = composeHand(playerList.get(playerIdx),cards0);     //calls composeHand method to see if a valid hand of any type can be composed
			  																//with this hand of cards or not
				
			if (played == null) {	  //if played == null means that no valid hand was able to be formed from the cards the player selected
				   					 //so ask the player to enter value again
				System.out.println(" Not a legal move!!!");
				ui.printMsg(" Not a legal move!!!\n");
				legalMove = false;
				lastHand = handsOnTable.get(handsOnTable.size() - 1);  //there could be a case where all players passed and then when the first player played 
																	   //a hand, that hand was invalid. in that case, reset lastHand to the lastHand on table
																	   //as it had previously been set to null
			}
			else {    //played != null means that a valid hand was able to be formed from the cards given
				if (played.beats(lastHand) == true || lastHand == null) {   //check if the current hand is able to beat last hand or not  (when another player tries to beat the hand on the table
																			//lastHand = null when the lastPlayer and currentPlayer index are same so now player can play any hand they want
					lastPlayerIdx = playerIdx;
					lastHand = played;
					
					handsOnTable.add(lastHand);  //add the hand to the table list
					
//					System.out.print("{");
//					System.out.print(played.getType());
//					System.out.print("} ");
//					ui.printMsg("{" + played.getType() + "}");
					
					played.print();
					
//					Hand lastHandOnTable = (handsOnTable.isEmpty()) ? null : handsOnTable.get(handsOnTable.size() - 1);
					String cards = " [";
					for (int i = 0; i < played.size(); i++) {
						cards += played.getCard(i); 
						cards += "] ";
						if ( i < played.size() - 1) {
							cards += " [";
						}
					}
					ui.printMsg(" {" + played.getType() + "} ");
					ui.printMsg(cards + "\n");
					
					
					playerList.get(playerIdx).removeCards(cards0);
					if (currentPlayerIdx == 3) {
						currentPlayerIdx = 0;
					}
					else if (currentPlayerIdx < 3) {
						currentPlayerIdx += 1;
					}
					legalMove = true;
				}
				else if (played.beats(lastHand) == false) {   //if not able to beat the last hand, this is illegal move so ask for move input again
					ui.printMsg(" Not a legal move!!!\n");
					legalMove = false;
				}

			}
		}
		
		if (legalMove == true) {   //if move is legal, first check if the game ends after this move or not.
								   //if ends, then print the results and end game
								   //else ask the next player for card input
			if (endOfGame() == true) {   //game ends so print game ending information
				ui.printMsg("\n");
				ui.printMsg(" Game Ends!\n");
				String toPrint = "";  //this will store the data that will be viewed on the pop up window upon game ending!
				for (int i = 0; i < playerList.size(); ++i) {
					if (playerList.get(i).getNumOfCards() == 0) {
						if (i == client.getPlayerID()) {
							toPrint += " You win the game!\n";
							ui.printMsg(" You win the game!\n");
						}
						else {
							toPrint += " " + playerList.get(i).getName() + " wins the game. \n";
							ui.printMsg(" " + playerList.get(i).getName() + " wins the game. \n");
						}

					}
					else {
						if (i == client.getPlayerID()) {
							toPrint += " You have " + playerList.get(i).getNumOfCards() + " cards left\n";
							ui.printMsg(" You have " + playerList.get(i).getNumOfCards() + " cards left\n");
						}
						else {
							toPrint += " " + playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards left. \n";
							ui.printMsg(" " + playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards left. \n");
						}
					}
				}
				ui.printMsg(" Press Restart from the Game tab to enable the game panel and start a new game.\n");
				ui.printMsg(" Happy Gaming!\n");
				ui.repaint();
				ui.showEndingMessage(toPrint);
			
			}
			else {  //game must continue so ask next player for input
//				System.out.println();
				ui.printMsg(" " + playerList.get(currentPlayerIdx).getName() + "'s Turn: \n");
				ui.setActivePlayer(currentPlayerIdx);
				ui.repaint();
				ui.promptActivePlayer();
			}

		}
		else if (legalMove == false) {  //for illegal move, ask the same player for input agains
//			ui.printMsg(" Player " + currentPlayerIdx + "'s turn: \n");
			ui.printMsg(" " + playerList.get(currentPlayerIdx).getName() + "'s Turn: \n");
			ui.promptActivePlayer();
		}
	}
	
	/**
	 * A method for returning a valid hand from the specified list of cards of the player. Returns null if no valid hand can be composed from the specified list of cards.
	 * @param player Which carries information about the player to whom the cards belong.
	 * @param cards A list containing the cards with which a hand composition should be attempted.
	 * @return A composed hand of the possible type that can be made for this selection of cards.
	 */
	public static Hand composeHand (CardGamePlayer player, CardList cards) {
		Hand h0 = new Single(player,cards);  //check if a single can be made
		if (h0.isValid() == true) {
			return h0;
		}
		Hand h1 = new Pair(player,cards);  //check if a pair can be made
		if (h1.isValid() == true) {
			return h1;
		}
		Hand h2 = new Triple(player,cards);  //check if a triple can be made
		if (h2.isValid() == true) {
			return h2;
		}
		Hand h7 = new StraightFlush(player,cards);  //check if a straightflush can be made   //check straightflush before straight or flush!
		if (h7.isValid() == true) {
			return h7;
		}
		Hand h3 = new Straight(player,cards);  //check if a straight can be made
		if (h3.isValid() == true) {
			return h3;
		}
		Hand h4 = new Flush(player,cards);  //check if a flush can be made
		if (h4.isValid() == true) {
			return h4;
		}
		Hand h5 = new FullHouse(player,cards);  //check if a full house can be made
		if (h5.isValid() == true) {
			return h5;
		}
		Hand h6 = new Quad(player,cards);  //check if a quad can be made
		if (h6.isValid() == true) {
			return h6;
		}
		return null;  //if not hand can be made, return null
	}
	
	/**
	 * Check if game should end or not by checking if any player has an empty hand or not.
	 */
	public boolean endOfGame () {
		for (int i = 0; i < playerList.size(); ++i) {
			if (playerList.get(i).getNumOfCards() == 0) {
				return true;  //if any player has an empty hand, return true
			}
		}
		return false;  //else return false
	}
	
	/**
	 * Used to send chat messages by the GUI to the client and server if needed.
	 * @param message A string that carries the chat message to be send.
	 */
	public void sendChatMessage (String message) {
		CardGameMessage msg = new CardGameMessage(CardGameMessage.MSG,-1,message);
		client.sendMessage(msg);
	}
	
	
	/**
	 * Make the client ready by setting an IP Address and TCP Port and also taking in a valid name for the user.
	 * Invalid names will result in the client window shutting down.
	 * @param args Any user inputs that may be necessary.
	 */
	public void getClientReady(String[] args) {
		client.setServerIP("127.0.0.1");
		client.setServerPort(2396);
		
		String name = JOptionPane.showInputDialog(null,"Name: ");
		if (name == null) {
			System.exit(0);
		}
		else if (name.equals("")) {
			System.exit(0);
		}
		else {
			client.setPlayerName(name);
			client.connect();
		}

	}
	
	/**
	 * Used to get the ID of this client amongst all the other clients currently connected to the player.
	 * @return An index value between 0 to 3 indicating the player ID.
	 */
	public int getLocalPlayerIdx() {
		return client.getPlayerID();
	}
	
	/**
	 * Used to enable GUI upon the call by the client
	 */
	public void enableGUI() {
		ui.enable();
	}
	
	/**
	 * Used to disable the the GUI upon the call by the client.
	 */
	public void disableGUI() {
		ui.disable();
	}
	
	
	/**
	 * Used to set a QUIT message when the player wants to quit the game.
	 */
	public void sendQuitMessageToClient() {
		client.sendMessage(new CardGameMessage(CardGameMessage.QUIT,client.getPlayerID(),-1));
	}
	
	/**
	 * Used to send a READY message to the server and hence to the clients to start a game.
	 * It is mainly called by the BigTwoGUI whenever a old game ends and it wants to start a new game.
	 */
	public void startNewGame() {
		client.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
	}
	
	
	/**
	 * A method used to test if the client is already connected or the server or not.
	 * It sends an ObjectOutputStream to the server to check if it is received by anyone on the other end or not.
	 * @param words A stream of strings to test if the server is active.
	 * @return Returns true if the server is active and false otherwise.
	 */
	public boolean sendTesterMessage(String words) {
		return client.sendFakeMessage(words);
	}
	
	
	/**
	 * Used to connect the client to the server whenever the client gets disconnected.
	 * Mainly called by the BigTwoGUI whenever it wants to connect the client to the server.
	 */
	public void connectUser() {
		client.connect();
	}
	
	
	/**
	 * A method for starting a Big Two card game. It does the following-
	 * <ol>
	 * 		<li> Create a Big Two card game,</li>
	 * 		<li> Create and shuffle a deck of cards, and </li>
	 * 		<li> Start the game with the deck of cards.</li>
	 * </ol>
	 * @param args Not used in this instance
	 */
	public static void main (String[] args) {
		BigTwo game = new BigTwo();   //new game instance for big two game
		
		//BigTwoDeck deck = new BigTwoDeck();   //a big two card game deck to be used in the game
		
		game.getClientReady(args);  //setting the IP Address and TCP Port for the client

	}
	
	
		
}
