import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CardDeck extends JPanel{
	
	private ArrayList<Card> deck;

	
	public CardDeck() {
		deck = new ArrayList<Card>();
		for (int n = 1; n < 14; n++) {
			for (Card.Suit s: Card.Suit.values()) {
				deck.add(new Card(n,s));
			}
		}
		
		this.setSize(new Dimension(5,10));
		this.setMaximumSize(new Dimension(5,10));
	}
	
	
	
	public Card draw() {
		if (deck.size() == 0) {
			return null; //Come back here to throw a reshuffle deck algo
		}
		
		return deck.remove((int) (Math.random() * deck.size()));
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(getClass().getResource("./images/cardback.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		float cardRatio = img.getHeight() / img.getWidth();
		float panelRatio = this.getHeight() / this.getWidth();
		
		
		if (cardRatio < panelRatio + 1) {
			g.drawImage(img, 0, (int) ((this.getHeight() - (this.getWidth() * cardRatio)) / 2), this.getWidth(), (int)(this.getWidth() * cardRatio), null);
		} else {
			g.drawImage(img, (int)(this.getWidth() - (this.getHeight() / cardRatio))/2, 0, (int) (this.getHeight() / cardRatio), this.getHeight(), null);
		};
		
	}
	
	
	

	
}


