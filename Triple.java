/**
 * This hand consists of three cards with the same rank. The card with the highest suit in a triple is referred to as the top card <br>
 * of this triple. A triple with a higher rank beats a triple with a lower rank.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class Triple extends Hand{
	
	/**
	 * The constructor to make a triple from the cards selected by the player.
	 * @param player The player who played the list of cards.
	 * @param cards The cards played by the player for this iteration.
	 */
	public Triple (CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 * Checks if the hand is actually a valid triple or not.
	 * @return True if it is a valid triple; false otherwise.
	 */
	public boolean isValid() {
		if (this.size() != 3) {
			return false;
		}
		else if (this.size() == 3) {   //if number of cards is not three, then it is not a triple
			if (this.getCard(0).rank != this.getCard(1).rank) {
				return false;
			}
			else if (this.getCard(0).rank != this.getCard(2).rank) {  //check to confirm if ranks of all 3 cards are same or not
				return false;
			}
			else if (this.getCard(1).rank != this.getCard(2).rank) {
				return false;
			}
			if ((this.getCard(0).suit == this.getCard(1).suit) && (this.getCard(0).suit == this.getCard(2).suit) && (this.getCard(1).suit == this.getCard(2).suit)) {
				return false;  //extra case for in case all three cards are same(error check)
			}
		}
		return true;
	}
	
	/**
	 * A method to return the type name of the hand as a string
	 * @return String stating the name of this hand type
	 */
	public String getType() {
		return "Triple";
	}
	

}

