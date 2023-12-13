/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards. It has a private instance variable for storing the player <br>
 * who plays this hand. It also has methods for getting the player of this hand, checking if it is a valid hand, getting the type of this hand, getting <br>
 * the top card of this hand, and checking if it beats a specified hand.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public abstract class Hand extends CardList {
	
	/**
	 * Constructor to form a hand of cards for any player.
	 * @param player The player who wants to form a hand of card.
	 * @param cards The list of cards to be used to form the hand of cards.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i = 0; i < cards.size(); ++i) {  //adding all the cards to the CardList called cards
			this.addCard(cards.getCard(i));
		}
	}
	
	private final CardGamePlayer player; //private variable for the player who played this hand 
	
	/**
	 * Method for retrieving the player of this hand.
	 * @return The player who played this hand of cards.
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}


	/**
	 * A method for retrieving the top card from the hand that is played.
	 * @return The top card from the hand based on the conditions of the type of hand.
	 */
	public Card getTopCard() {
		if (!this.isEmpty()) {
			Card topCard = this.getCard(0);  //declare first card as top card
			for (int i = 1; i < this.size(); ++i) {  //iterate through the cards list
				int tempHostRank = switchChecker(topCard.rank);
				int tempComparingRank = switchChecker(this.getCard(i).rank);
				
				if (tempComparingRank > tempHostRank) {  //if the rank of the current card is greater than top card, make current card as the top card
					topCard = this.getCard(i);
				}
				else if (topCard.suit < this.getCard(i).suit) {  //if the rank is same for top card and current card, check suits for more dominant suit 
					topCard = this.getCard(i);
				}
			}
			return topCard;
		}
		else {
			return null;  
		}
	}
	
	
	/**
	 * A method for checking if this hand (let's call it host hand) beats a specified hand (let's call it visiting hand).
	 * @param hand The specified hand to be compared against.
	 * @return Boolean value stating true if the host hand beats visiting hand; false otherwise.
	 */
	public boolean beats(Hand hand) {
		if (hand == null || !hand.isValid() || !this.isValid() || this.size() != hand.size()) {  //check if the hand is empty, or if either of the hands is invalid or not; 
																								 //also check to confirm if both hands have same number of cards
																								 //if they are not than return false directly
			return false;
		}
		else {
			//this uses the cardTypeToInteger and converts each card type into an integer based on strengths
			//more dominant type should beat less dominant type so return true in that case immediately
			if (cardTypeToInteger(this.getType()) > cardTypeToInteger(hand.getType())) {
				return true;
			}
			else if (cardTypeToInteger(this.getType()) < cardTypeToInteger(hand.getType())) {
				return false;
			}
			//if both hands are same type, call the compare to function and compare their top cards to see which is more dominant
			else {
				if (this.getTopCard().compareTo(hand.getTopCard()) > 0) {
					return true;
				}
				else {
					return false;
				}
			}

		}
	}
	
	//if the card is 2 of any suit, that card has index of 1 initially. but this is
	//the most powerful card so we define its value as 12 index
	//we define A of any suit as index 11
	//we define K as index 10
	//we define Q as index 9
	//we define J as index 8 and so on
	//we define 4 as index 1
	//we define 3(card 3) as index 0 (smallest)
	
	/**
	 * A method to be used to rearrange and determine the actual rank of the card according to its strength.
	 * @param value The given rank of the card (which was given in ascending order of numbers- where A is 0 and K is 12.
	 * @return The actual rank of the card (where 2 is indexed 12, A is indexed 11, K is indexed 10 upto 3 being indexed as 0).
	 */
	public int switchChecker (int value) {
		switch (value) {
		case 1:  //for 2 of any suit
			value = 12;  
			break;
		case 0:  //for A of any suit
			value = 11;
			break;
		case 12:  //for K of any suit
			value = 10;
			break;
		case 11:  //for Q of any suit
			value = 9;
			break;
		case 10:  //for J of any suit
			value = 8;
			break;
		case 9:  //for 10 of any suit
			value = 7;
			break;
		case 8:  //for 9 of any suit
			value = 6;
			break;
		case 7:  //for 8 of any suit
			value = 5;
			break;
		case 6:  //for 7 of any suit
			value = 4;
			break;
		case 5:  //for 6 of any suit
			value = 3;
			break;
		case 4:  //for 5 of any suit
			value = 2;
			break;
		case 3:  //for 4 of any suit
			value = 1;
			break;
		case 2:  //for 3 of any suit
			value = 0;
			break;
		}
		return value;
	}
	
	/**
	 * Returns the card type as an integer based on the strength of the type. A more dominant type has a higher integer value than a less dominant type
	 * @param value A string determining the name of the type of card
	 * @return An integer value determining the strength of the type of hand
	 */
	public int cardTypeToInteger (String value) {
		int convertedResult = 0;
		switch (value) {
		case "Single":
			convertedResult = 0;  //weakest hand
			break;
		case "Pair":
			convertedResult = 1;  //beats single
			break;
		case "Triple":
			convertedResult = 2;  //beats pair and triple
			break;
		case "Straight":
			convertedResult = 3;  //beats all hands with lower integers
			break;
		case "Flush":
			convertedResult = 4;  //beats all hands with lower integers
			break;
		case "FullHouse":
			convertedResult = 5;  //beats all hands with lower integers
			break;
		case "Quad":
			convertedResult = 6;  //beats all hands with lower integers
			break;
		case "StraightFlush":
			convertedResult = 7;  //beats all hands with lower integers
			break;
		}
		return convertedResult;
	}
	
	/**
	 * Abstract method for checking if the given hand is a valid hand or not.
	 * To be implemented in the subclasses.
	 * @return A boolean value returning true if the hand is valid; and false otherwise.
	 */
	public abstract boolean isValid();
	
	/**
	 * Abstract method to return the name of the type of hand.
	 * @return Returns hand type name is string format.
	 */
	public abstract String getType();
	
	
}
