/**
 * This hand consists of five cards, with two having the same rank and three having another same rank. The card in the triplet with<br>
 * the highest suit in a full house is referred to as the top card of this full house. A full house always beats any straights and flushes. <br>
 * A full house having a top card with a higher rank beats a full house having a top card with a lower rank.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class FullHouse extends Hand{
	
	/**
	 * The constructor for making the Full House hand of cards.
	 * @param player The player who played this hand
	 * @param cards The cards played by the player
	 */
	public FullHouse (CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 * Checks if the hand is actually a valid full house or not.
	 * @return True if it is a valid full house; false otherwise.
	 */
	public boolean isValid() {
		if (this.size() != 5) {  //if number of cards is not 5, it cannot be fullhouse so return false
			return false;
		}
		else if (this.size() == 5) {   //if number of cards is 5, try this clause
			int firstRank = this.getCard(0).rank;
			int firstRankCount = 1;  //as first element is counted already, so rankCount starts from 1
			int secondRank = this.getCard(0).rank;
			for (int i = 1; i < 5; ++i) {  //check which rank has 3 cards from it
				if (this.getCard(i).rank == firstRank) {  
					firstRankCount += 1;
				}
				else if (this.getCard(i).rank != firstRank) {  //find both the first rank count and the second rank inside one loop
					secondRank = this.getCard(i).rank;
				}
			}
			
			if (firstRankCount != 2 && firstRankCount != 3) {   //firstRank count must be 2 or 3 to be full house
				return false;
			}
			else {
				int secondRankCount = 0;
				for (int j = 0; j < 5; ++j) {
					if (this.getCard(j).rank == secondRank) {   //count how many cards with second rank
						secondRankCount += 1;
					}
					else if (this.getCard(j).rank != firstRank) {  //if card rank not first rank or second rank then this is not a full house
						return false;
					}
				}
				if (secondRankCount != 2 && secondRankCount != 3) {  //secondRank count must be 2 or 3 to be full house
					return false;
				}
				if (firstRankCount + secondRankCount != 5) {  //if both add up to 5, then it is a full house
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
		return "FullHouse";
	}
	
	/**
	 * A method for getting the top card from the hand if the hand is a valid full house.
	 * Checks through all the cards and finds the card with the highest suit from the <i>triplet</i>
	 * set in the hand. That is the top card of this hand.
	 */
	public Card getTopCard() {
		int firstRank = this.getCard(0).rank;
		int firstRankCount = 1;  //as first element is counted also, so start counter from 1
		int secondRank = this.getCard(0).rank; 
		//this iterates through the cards in hand and finds the cards that have the same rank
		//as the rank of the firstRank card. increments counter by 1 to see how many firstRank
		//cards exist
		for (int i = 1; i < 5; ++i) {
			if (this.getCard(i).rank == firstRank) {
				firstRankCount += 1;
			}
			//also looks for the second possible rank in the card hand as the only other rank present must be the second rank
			else if (this.getCard(i).rank != firstRank) {  //find both the first rank count and the second rank
				secondRank = this.getCard(i).rank;
			}
		}

		int secondRankCount = 0;  //count how many cards of the second rank exists in this hand
		for (int j = 0; j < 5; ++j) {
			if (this.getCard(j).rank == secondRank) {
				secondRankCount += 1;
			}
		}
		
		int mainRank = 0;  //this will see which rank has more of its cards as that is the triplet so use that to find the top card
		if (secondRankCount > firstRankCount) {
			mainRank = secondRank;
		}
		else {
			mainRank = firstRank;
		}
				
		//change mainRank to fit the power-index scale (rank-wise comparison)
		mainRank = switchChecker(mainRank);
		Card topCard = this.getCard(0);  //set first card as the topCard to check the full hand
		for (int i = 1; i < this.size(); ++i) {
			if ((switchChecker(this.getCard(i).rank) == mainRank)) {  
				topCard = this.getCard(i);   //get the first card with our desired rank for later comparison
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

