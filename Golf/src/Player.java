
public class Player {
	
	private String name;
	public Hand hand;
	private int score;
	
	
	public Player(String s) {
		name = s;
		hand = new Hand();
		score = 0;
	}

	public String getName() {
		return name;
	}
	
	public void addScore(int s) {	
		score += s;
	}

}
