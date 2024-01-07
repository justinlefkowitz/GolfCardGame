import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Hand {
	
	
	private ArrayList<Card> hand;
	//contains whether cards are shown or not,
			//0 = face down
			//1 = face up
			//2 = transparent preview?
	
	
	
	public Hand() {
		hand = new ArrayList<>();
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public void receive(Card c) {
		hand.add(c);
	}
	
	public void peek(Card c) {
		hand.get(hand.lastIndexOf(c)).setState(2);
	}
	
	public void peek(int i) {
		hand.get(i).setState(2);
	}
	
	public void play(Card c) {
		hand.get(hand.lastIndexOf(c)).flip();
	}
		

	public void replace(Card a, Card b) {
		/*
		Card card = null;
		for (Card c: keySet()) {
			if (a.equals(c)) {
				card = c;
			}
		}*/
		hand.remove(a);
		hand.add(b);
	}

	public void printHand() {
		for (Card c: hand) {
			System.out.println(c + " " + c.getState());
		}
	}

	public int score() {
		int total = 0;
		int[] scores = new int[13];
		
		for (int i = 0; i < 13; i++) {		
			scores[i] = 0;	
		}
		
		
		for (Card c: hand) {
			scores[(c.getNum() % 13)]++;
		}
		
		for (int i = 0; i < 13; i++) {		
			if (scores[i] == 1) {
				total += i;
			} else if (scores[i] == 3) {
				total -= i;
			} else if (scores[i] == 4) {
				System.out.println("How on earth did you manage to win????");
				total = Integer.MIN_VALUE;
			}
		}
		
		
		return total;
	}
	
	
	
}
