/**
 * This hand consists of five cards, with four having the same rank. The card in the quadruplet with the highest suit in a quad <br>
 * is referred to as the top card of this quad. A quad always beats any straights, flushes, and full houses. A quad having a top card <br>
 * with a higher rank beats a quad having a top card with a lower rank.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class Quad extends Hand{
	
	/**
	 * The constructor to be used for the formation of a quad hand.
	 * @param player The player who played set of the cards.
	 * @param cards The list of cards to be used in quad formation.
	 */ 
	public Quad (CardGamePlayer player, CardList cards) {
		super(player,cards);  //calling the super constructor
	}
	
	/**
	 * Checks if the hand is actually a valid Quad or not.
	 * @return True if it is a valid quad; false otherwise.
	 */
	public boolean isValid() {
		if (this.size() != 5) {  //if length is not 5, it must not be a quad; return false.
			return false;
		}
		else if (this.size() == 5) {  //if length is 5, check
			int firstRank = this.getCard(0).rank;  //gets first card as first rank
			int firstRankCount = 1;  //as first element is counted also
			int secondRank = this.getCard(0).rank;
			for (int i = 1; i < 5; ++i) {
				if (this.getCard(i).rank == firstRank) {
					firstRankCount += 1;
				}
				else if (this.getCard(i).rank != firstRank) {  //find both the first rank count and the second rank
					secondRank = this.getCard(i).rank;  //also checks for the second rank present in the hand
				}
			}
			
			if (firstRankCount != 4 && firstRankCount != 1) {   //firstRank count must be 1 or 4 to be quad
				return false;
			}
			else {
				int secondRankCount = 0;
				for (int j = 0; j < 5; ++j) {
					if (this.getCard(j).rank == secondRank) {
						secondRankCount += 1;
					}
					else if (this.getCard(j).rank != firstRank) {  //if card rank not first rank or second rank then this is not a quad
						return false;
					}
				}
				if (secondRankCount != 1 && secondRankCount != 4) {  //secondRank count must be 1 or 4 to be quad
					return false;
				}
				if (firstRankCount + secondRankCount != 5) {  //both must add upto to a combined value of 5
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
		return "Quad";
	}
	
	/**
	 * A method for getting the top card from the hand if the hand is a valid quad.
	 * Checks through all the cards and finds the card with the highest suit from the <i>quadruplet</i>
	 * set in the hand. That is the top card of this hand. 
	 */
	public Card getTopCard() {
		int firstRank = this.getCard(0).rank;  //get the first rank for the quad
		int firstRankCount = 1;  //as first element is counted also, start count at 1
		int secondRank = this.getCard(0).rank; 
		for (int i = 1; i < 5; ++i) {  //counting number of cards with first rank
			if (this.getCard(i).rank == firstRank) {
				firstRankCount += 1;
			}
			else if (this.getCard(i).rank != firstRank) {  //find both the first rank count and the second rank itself
				secondRank = this.getCard(i).rank;
			}
		}

		int secondRankCount = 0;  //finding the number of second rank
		for (int j = 0; j < 5; ++j) {
			if (this.getCard(j).rank == secondRank) {
				secondRankCount += 1;
			}
		}
		
		int mainRank = 0;
		if (secondRankCount > firstRankCount) {   //the rank count with higher value must be the quad
			mainRank = secondRank;
		}
		else {
			mainRank = firstRank;
		}
				
		//change mainRank to fit the power-index scale (rank-based system)
		mainRank = switchChecker(mainRank);
		Card topCard = this.getCard(0);
		for (int i = 1; i < this.size(); ++i) {
			if ((switchChecker(this.getCard(i).rank) == mainRank)) {
				topCard = this.getCard(i);   //get the first card with our desired rank (quad rank) 
				break;
			}
		}
		//find our desired rank with the highest suit
		for (int i = 1; i < this.size(); ++i) {
			if (this.getCard(i).suit > topCard.suit) {
				topCard = this.getCard(i);
			}
		}
		return topCard;
	}

}

