/**
 * This hand consists of five cards with consecutive ranks. For the sake of simplicity, 2 and A can only form a straight with K but not <br>
 * with 3. The card with the highest rank in a straight is referred to as the top card of this straight. A straight having a top card with a <br>
 * higher rank beats a straight having a top card with a lower rank. For straights having top cards with the same rank, the one having a top card <br>
 * with a higher suit beats the one having a top card with a lower suit.
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class Straight extends Hand{
	
	/**
	 * The constructor being used to form the straight hand for this iteration.
	 * @param player The player who played the cards.
	 * @param cards The cards played by the player for this iteration.
	 */
	public Straight (CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	
	/**
	 * Checks if the hand is actually a valid straight or not.
	 * @return True if it is a valid straight; false otherwise.      
	 */
	public boolean isValid() {
		if (this.size() != 5) {  //if the number of cards is not 5, it must not be a straight so return false
			return false;
		}
		else if (this.size() == 5) {
			//tempArrayay to hold the ranks by comparable indexes
			int[] tempArray = new int[5];
			for (int i = 0; i < 5; ++i) {   //make new tempArrayay and take the checkable index values for ranks
				tempArray[i] = switchChecker(this.getCard(i).rank);  //note that the index values for ranks are now rank wise according to their strengths
			}
			
			//sorting array in ascending order; to check if the 5 cards are consecutive ranks or not
	        for (int k = 0; k < tempArray.length; ++k) {  
	        	int temp = 0;
	            for (int j = k+1; j < tempArray.length; ++j) {     
	               if(tempArray[k] > tempArray[j]) {    
	                   temp = tempArray[k];      //swapping to check and sort array
	                   tempArray[k] = tempArray[j];    
	                   tempArray[j] = temp;    
	               }     
	            }     
	        }
	        
			//check if ranks are all consecutive
			for (int m = 0; m < 5-1; ++m) {
				if (tempArray[m] != tempArray[m+1]-1) {
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
		return "Straight";
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

