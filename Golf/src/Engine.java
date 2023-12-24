import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Engine {

	private ArrayList<Player> players = new ArrayList<Player>();
	private CardDeck cards = new CardDeck();
	private Card topCard;
	private Card picked;
	
	private Scanner in;
	private String answer;
	
	
	private int gameState;
	private Player activePlayer;
	private int swing;
	
	private int rounds;

	
	/*
	0 = initialized
	1 = first choice
	2 = second choice
	3 = replace
	4 = end of round
	
	*/
	
	public Engine() {
		gameState = 0;
		
		in = new Scanner(System.in);
		
		players.add(new Player("Justin"));
		//players.add(new Player("Simon"));
		//players.add(new Player("Lily"));
		players.add(new Player("Ellie"));
		
		swing = 1;
		
		startGame();
		display();
	
	}

	
	public void startGame() {
		for (int i = 0; i < 4; i++) {
			for (int p = 0; p < players.size(); p++) {
				players.get(p).hand.receive(getCards().draw());
			}
			nextCard();
		}
		
		gameState = 1;
		activePlayer = players.get(0);
		
	}
	
	public void display() {
		for (Player p: players) {
			System.out.println(p.getName());
			p.hand.printHand();
			System.out.println();
		}
		System.out.println("\nThe top card is " + topCard);
		
	}
	

	public void displayScore() {
		for (Player p: players) {
			System.out.println(p.getName() + " has a score of: " + p.hand.score());
		}
	}
	
	public void nextCard() {
		topCard = getCards().draw();
		//topCard.flip();
	}
	
	
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	

	
	public CardDeck getCards() {
		return cards;
	}

	
	public Card getTop() {
		return topCard;
	}


	
	public int getGameState() {
		return gameState;
	}
	
	
	public void setGameState(int i) {
		gameState = i;
	}


	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public int playerCount() {
		return players.size();
	}
	
	public int playerID(Player p) {
		return players.indexOf(p);
	}
	
	public int getSwing() {
		return swing;
	}
	
	public void endTurn(Card c) {
		
		activePlayer.hand.play(c);
		
		
		gameState = 1;
		int next = (playerID(activePlayer) + 1) % players.size();
		
		if (next == 0) {
			swing++;
		}
		
		if (swing < 5) {
		
			activePlayer = players.get(next);
			//display();
		
		} else {
			
			gameState = 4;
			displayScore();
		}
	}
	
	public void cardReplace(Player p, Card c) {
		
		p.hand.replace(c, topCard);
		topCard = c;	
	}
	



	
	
}
