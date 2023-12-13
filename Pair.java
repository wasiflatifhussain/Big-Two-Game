/**
 * This hand consists of only one single card. The only card in a single is referred to as the top card of this single. A single with a higher <br>
 * rank beats a single with a lower rank. For singles with the same rank, the one with a higher suit beats the one with a lower suit.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class Pair extends Hand{
	
	/**
	 * The constructor for making the Pair hand of cards.
	 * @param player The player who played this hand
	 * @param cards The cards played by the player
	 */
	public Pair (CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 * Checks if the hand is actually a valid pair or not.
	 * @return True if it is a valid pair; false otherwise. 
	 */
	public boolean isValid() {
		if (this.size() != 2) {  //if number of cards played is not 2, then it cannot be a pair
			return false;
		}
		else if (this.size() == 2) {  //if no. is 2, then check their ranks as their ranks must be same to be a pair
			if (this.getCard(0).rank != this.getCard(1).rank) {
				return false;
			}
			if (this.getCard(0).suit == this.getCard(1).suit) {  //if suits are same then also not a pair
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A method to return the type name of the hand as a string
	 * @return String stating the name of this hand type 
	 */
	public String getType() {
		return "Pair";
	}
}
