
/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a Big Two card game.<br> 
 * It overrides the compareTo() method it inherits from the Card class to reflect the ordering of cards used in a Big Two card game. 
 * @author wasiflatifhussain
 *
 */
@SuppressWarnings("serial")
public class BigTwoCard extends Card {
	/**
	 * The constructor for building a card with the specified suit and rank. 
	 * @param suit Suit is an integer between 0 and 3.
	 * @param rank Rank is an integer between 0 and 12.
	 */
	public BigTwoCard(int suit, int rank) {   //inherit the card class constructors
		super(suit,rank);  //as there is no default blank constructors for cards, we use super to call
	}
	


	//in the compareTo() method-
	// index is in the format :index-1(2 spade),0(A spade),12(K spade),11(Q spade),10(J spade),
	// 9(10-here called 0 spade),8(9 spade),7(8 spade),6(7 spade),5(6 spade),4(5 spade),
	// 3(4 spade),2(3 spade)
	
	// suit index- 0(diamond),1(clubs),2(heart),3(spades)
	
	// as the cards are not given in correct order for comparing, we will make another method
	// and use switch cases and temporary variables to find out actual position of that card 
	// implemented method-  public int switchChecker (int value)
	
	/**
	 * A method for comparing the order of this card (this.card) with the specified card. Returns a negative integer, <br>
	 * zero, or a positive integer when this card is less than, equal to, or greater than the specified card.
	 * @param card The card that should be compared with the host card.
	 * @return Returns a negative integer, zero, or a positive integer when this card is less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card) {
		
		int tempHostRank = switchChecker(this.rank);  //main determination happens in the switchChecker method
		int tempComparingRank = switchChecker(card.rank);  //main determination happens in the switchChecker method
		
		if (tempHostRank > tempComparingRank) {  //if rank of the host card(this.card) greater, means it is dominant so return 1
			return 1;
		} 
		else if (tempHostRank < tempComparingRank) {  //if rank lower means it is inferior so return -1
			return -1;
		} 
		else if (this.suit > card.suit) {  //if ranks are same, check suit. if suit is greater for host, it is dominant
			return 1;
		} 
		else if (this.suit < card.suit) {  //else submissive
			return -1;
		} 
		else {   //if both cards are same, return 0
			return 0;
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
