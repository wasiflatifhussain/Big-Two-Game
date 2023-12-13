/**
 * This hand consists of five cards with the same suit. The card with the highest rank in a flush is referred to as the top card of this flush. <br>
 * A flush always beats any straights. A flush with a higher suit beats a flush with a lower suit. For flushes with the same suit, the one having a <br>
 * top card with a higher rank beats the one having a top card with a lower rank.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class Flush extends Hand{
	/**
	 * The constructor for making the Flush hand of cards.
	 * @param player The player who played this hand
	 * @param cards The cards played by the player
	 */
	public Flush (CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 * Checks if the hand is actually a valid flush or not.
	 * @return True if it is a valid flush; false otherwise.
	 */
	public boolean isValid() {
		if (this.size() != 5) {   //if length is not 5, then it cannot be a flush so return false
			return false;
		}
		else if (this.size() == 5) {  //if length is 5, check the suits of each cards as in a flush all cards must have same suit
			for (int i = 1; i < 5; ++i) {
				if (this.getCard(0).suit != this.getCard(i).suit) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * A method to return the type name of the hand as a string
	 * @return String stating the name of this hand type
	 */
	public String getType() {
		return "Flush";
	}

	
	/**
	 * Used to check if the host hand/card beats the previous hand/card or not for Flush
	 * The main beats has been overridden here to due to the special beat conditions assigned for flush.
	 * @param hand Is the previous hand on the table/the visiting hand of cards to compare the host/current hand against.
	 * @return A boolean value stating true if the host beats the visiting card and false otherwise.
	 */
	public boolean beats(Hand hand) {
		if (hand == null || !hand.isValid() || !this.isValid() || this.size() != hand.size()) {
//			System.out.println("first if failed what?");
			return false;
		}
		else {
			//checking if the type of the hand is more dominant or not. if host hand type is more dominant,
			//it should immediately return true as host has beaten the visiting hand
			if (cardTypeToInteger(this.getType()) > cardTypeToInteger(hand.getType())) {
				return true;
			}
			else if (cardTypeToInteger(this.getType()) < cardTypeToInteger(hand.getType())) {

				return false;
			}
			
			//in flushes, if the suit of the host card is bigger than the visiting card, then
			//this host card is more dominant and beat the visiting card so return true
			else if (this.getTopCard().suit > hand.getTopCard().suit) {
				return true;
			}
			else if (this.getTopCard().suit < hand.getTopCard().suit) {
				return false;
			}
			//if host and visiting card both have the same suit, compare their top card ranks.
			//card with higher rank belongs to the more dominant hand
			else {
				int tempHostRank = switchChecker(this.getTopCard().rank);  //main determination happens in the switchChecker method
				int tempComparingRank = switchChecker(hand.getTopCard().rank);  //main determination happens in the switchChecker method
				if (tempHostRank > tempComparingRank) {  //if host rank higher, return true
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
	
}

