import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The BigTwoClient class implements the NetworkGame interface. It is used to model a Big Two game client that is responsible for establishing<br>
 * a connection and communicating with the Big Two game server.
 * @author wasiflatifhussain
 *
 */
public class BigTwoClient implements NetworkGame{   
	
	/**
	 * A constructor for creating a Big Two client. The first parameter is a reference to a BigTwo object associated with this client and the second <br>
	 * parameter is a reference to a BigTwoGUI object associated the BigTwo object. 
	 * @param game The BigTwo game for this iteration
	 * @param gui The BigTwoGUI for this iteration
	 */
	BigTwoClient(BigTwo game, BigTwoGUI gui) {
		this.game = game;
		this.gui = gui;	
	}
	
	private BigTwo game;    //big two game
	private BigTwoGUI gui;  //big two gui
	private Socket sock;  //socket connection for this client to connect to the server
	private ObjectOutputStream oos;  //object output stream to send data out to the server
	private int playerID;  //stores the ID of this client
	private String playerName;  //stores the name of the player
	private String serverIP;  //stores the IP for this player
	private int serverPort;  //stores the TCP port for this player
	private boolean connectionValue;  //stores the connectionstatus for this player
	private ObjectInputStream ois;  //object input stream to receive object inputs for the player
	private Runnable job;  //the job to be passed into the server handler for running the server handler
	private Thread receiverThread;  //the thread to handle the server client relation

	
	/**
	 * Sets the connection value to be used to true or false to be used for managing the server.
	 * @param value A boolean value to be used to set the connection status for client-server connection handling
	 */
	public void setConnectionValue(boolean value) {
		this.connectionValue = value;
	}
	
	/**
	 * Gets the connection value to be used to check if the client is connect to the server or not.
	 * @return A boolean value about the connection status of the client to server.
	 */
	public boolean getConnectionValue() {
		return this.connectionValue;
	}
	
	/**
	 * Getter for getting the player ID for this client amongst the other clients in the server.
	 */
	public int getPlayerID () {
		return this.playerID;
	}
	
	/**
	 * Setter to set the player ID for this client amongst the other clients in the server.
	 * This method should be called from the parseMessage() method <br>
	 * when a message of the type PLAYER_LIST is received from the game server.
	 */
	public void setPlayerID (int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * Gets the name of this player in the game.
	 */
	public String getPlayerName () {
		return this.playerName;
	}
	
	/**
	 * Sets the name of this player in the game.
	 */
	public void setPlayerName (String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * Gets the Server IP Address for this client to connect to the server.
	 */
	public String getServerIP () {
		return this.serverIP;
	}
	
	/**
	 * Sets the Server IP Address for this client to connect to the server.
	 */
	public void setServerIP (String serverIP) {
		this.serverIP = serverIP;
	}
	
	/**
	 * Gets the Server TCP port to connect client to server.
	 */
	public int getServerPort () {
		return this.serverPort;
	}
	
	/**
	 * Sets the Server TCP port to connect the client to server.
	 */
	public void setServerPort (int serverPort) {
		this.serverPort = serverPort;
	}
	
	
	/**
	 * A method for making a socket connection with the game server. Upon successful connection, you should (i) create an ObjectOutputStream for sending <br>
	 * messages to the game server; (ii) create a new thread for receiving messages from the game server.
	 */
	public void connect () {
		try {

			sock = new Socket(getServerIP(),getServerPort());  //initiate the socket connection
			oos = new ObjectOutputStream(sock.getOutputStream());  //oos to send out output streams to server
			
			job = new ServerHandler();  //declare a new Server Handler object
			
			//Thread receiverThread = new Thread(job);    //new ServerHandler() inside bracket maybe//maybe has to have something inside the bracket on the right
			
			receiverThread = new Thread(job);  //initiate the thread that this server runs on
			receiverThread.start();  //start the stread
			
		}
		catch (Exception ex) {
			connectionValue = false;
			ex.printStackTrace();
		}
	}
	
	/**
	 * A method for parsing the messages received from the game server. This method should be called from the thread responsible for receiving messages from <br>
	 * the game server. Based on the message type, different actions will be carried out (please refer to the general behavior of the client described in the <br>
	 * previous section).
	 */
	public synchronized void parseMessage (GameMessage message) {  
		CardGameMessage msg = (CardGameMessage) message;  //type casting the message to be processed accordingly.
		
		switch (msg.getType()) {   //switch cases to store all the possible messages for the clients
		case CardGameMessage.PLAYER_LIST:   
			
			setPlayerID(message.getPlayerID());  //setting the player ID as the local player ID
			
			String[] playerNames = (String[]) message.getData();  //stores the names of the players to be viewed on players game panel
			 
			for (int i = 0; i < playerNames.length; ++i) { 
				game.getPlayerList().get(i).setName(playerNames[i]);  //setting names of the players for this clients gui screen
			}
//			setPlayerName((String)message.getData());
			
			connectionValue = true;
			
			sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1,getPlayerName()));  //send a join message to allow the player to join the game server
			break;
			
		
		case CardGameMessage.JOIN:
			game.getPlayerList().get(message.getPlayerID()).setName((String)message.getData());    //sets name of the player inside the playerList
			gui.repaint();  //so to print the player names
			if (message.getPlayerID() == playerID) {
				sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));  //if this is the current player who joined, send a ready message
																				  //to indicate that the player is ready now after joining

			}
			gui.printMsg(" " + game.getPlayerList().get(message.getPlayerID()).getName() + " joined the game.\n");  //print message on messsage area about player's ready status
			break;
			
			
		case CardGameMessage.FULL:
			gui.printMsg(" Server is full. Cannot join the game. \n Please wait for a player to disconnect.\n");  //sends a server message saying the client cannot join
			gui.disable();  //disable this client's GUI to prevent them from interacting with the game gui
			break;
			
		
		case CardGameMessage.QUIT:
			gui.printMsg(" " + game.getPlayerList().get(message.getPlayerID()).getName() + " has left the game.\n"); //display info about client that left the game
			gui.printMsg(" Please wait for other player(s) to join for the game to restart.\n"); 
			game.getPlayerList().get(message.getPlayerID()).setName(null);
			message.setData(null);
			//HOW DO WE STOP THE GAME?
			sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
			
			gui.setgameStatus(false);
			gui.disable();   //disable GUI to disable players from playing the game until a new player joins again
			gui.enableChat();
			break;
			
		case CardGameMessage.READY:
			gui.printMsg(" " + game.getPlayerList().get(message.getPlayerID()).getName() + " is ready!\n");  //sends ready message to server and player
			break;
			
		case CardGameMessage.START:
			gui.clearMsgArea();  //clear message area for the next game
			gui.printMsg(" All players are ready. Game Begins!\n");
			gui.setgameStatus(true);
			gui.enable();
			game.start((BigTwoDeck)message.getData());  //start the game with the deck of cards provided by server/game
			break;
			
		case CardGameMessage.MOVE:
			game.checkMove(message.getPlayerID(), (int[])message.getData());  //simply call check move to check validity of this game move
			break;
			
		case CardGameMessage.MSG:
			String data = (String)message.getData();  
			System.out.println(data);
			String data2 = "You are already connected to the server!";
			if (data.contains(data2)) {  //if both message same means that player already connected to the server as per this program code; then it displays this message to the msgArea
				if (message.getPlayerID() == game.getLocalPlayerIdx()) {
					gui.printMsg(" " + data2 + "\n");
				}
			}
			else {
				gui.chatAreaWriter((String)message.getData());  //else prints normally to chat area
			}
			break;
		}
		
		

		
			
			
	}
	
	/**
	 * A method for sending the specified message to the game server. This method should be called whenever the client wants to communicate with the game <br>
	 * server or other clients.
	 */
	public synchronized void sendMessage (GameMessage message) {
		try {
			oos.writeObject(message);  //sends message to the server
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * An inner class that implements the Runnable interface. You should implement the run() method from the Runnable interface and create a thread with an <br>
	 * instance of this class as its job in the connect() method from the NetworkGame interface for receiving messages from the game server. Upon receiving a <br>
	 * message, the parseMessage() method from the NetworkGame interface should be called to parse the messages accordingly.
	 * @author wasiflatifhussain
	 *
	 */
	class ServerHandler implements Runnable {   //maybe should be private?
		
		@SuppressWarnings("deprecation")
		public void run() {
			CardGameMessage message;
			
			try {
				ois = new ObjectInputStream(sock.getInputStream());
					while ((message = (CardGameMessage) ois.readObject()) != null) {  //if user gets a message of type FULL means server is full
						if (message.getType() == 2) {
							sock.close();  //close socket and oos to stop client sending messages to the server
							oos.close();
							parseMessage(message);  //send the FULL message so player knows server full
							receiverThread.stop();  //stop the thread to stop user interactions
							
						}
						else {
							parseMessage(message); //else just normally parse this message
						}
						
					}

			}catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Send a message to the server just for checking if the user is still connected to the server or not. This is used when the client clicks "Connect" from the Menu Pane.<br>
	 * If the user is already connected to the server, the message goes through and displays to the user that they are already connected. Else it reconnects the user by <br>
	 * calling the connect method.
	 * @param words String of words saying that client already connect connected to the server
	 * @return Boolean value indicating true if user already connected, and false if otherwise.
	 */
	public synchronized boolean sendFakeMessage(String words) {
		CardGameMessage msg = new CardGameMessage(CardGameMessage.MSG, -1, words);
		boolean check = true;
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			check = false;
//			e.printStackTrace();  //not printing this here as we know this error will occur when server down or users full
								  //main target here is to make check false and return it
		}
		return check;
	}
}
