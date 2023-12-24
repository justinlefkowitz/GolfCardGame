
public class GolfGame {
	
	
	Engine game;
	GUI gui;
	
	public GolfGame() {
		game = new Engine();
		gui = new GUI(game);
	}
	
	public static void main(String[] args) {
		GolfGame golf = new GolfGame();
		
	}
	

}
