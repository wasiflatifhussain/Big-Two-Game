/**
 * This hand consists of only one single card. The only card in a single is referred to as the top card of this single. <br>
 * A single with a higher rank beats a single with a lower rank. For singles with the same rank, the one with a higher suit <br>
 * beats the one with a lower suit.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class Single extends Hand {
	
	/**
	 * The constructor to form a single hand.
	 * @param player The player who played this hand.
	 * @param cards The list of cards present in this hand.
	 */
	public Single (CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * Checks if the hand is actually a valid single or not.
	 * @return True if it is a valid single; false otherwise. 
	 */
	public boolean isValid() {
		if (this.size() != 1) {  //length must be 1 for it to be a valid single
			return false;
		}
		return true;
	}
	
	/**
	 * A method to return the type name of the hand as a string
	 * @return String stating the name of this hand type
	 */
	public String getType() {
		return "Single";
	}
	
}
