import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener, MouseListener {

	private Dimension size;

	private Engine game;
	private ImagePanel background;
	private JPanel gameBoard;
	
	private JPanel bottom;
	private JPanel top;
	private JPanel left;
	private JPanel right;
	
	private JPanel displayPanel;
	private JPanel deckPanel;
	
	//hash map to contain index of playerpanel to gameBoard as a key to player id
	private HashMap<Integer, Integer> position;

	
	
	public GUI(Engine e) {
		game = e;
		
		size = new Dimension(750,750);
		this.setTitle("Golf");
		this.setSize(size);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//set background
		try {
			background = new ImagePanel(ImageIO.read(getClass().getResource("./images/background.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		this.setContentPane(background);
		this.setLayout(new BorderLayout(0,0));
	
		
		
		initialize();
		setGameBoard();
		
		
		this.setVisible(true);
	}
	
	
	private void setGameBoard() {
		

		//creates gameBoard
		gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(3,3));
		//gameBoard.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		gameBoard.setOpaque(false);
		gameBoard.setSize(new Dimension(500,500));
		
		
		//sets spacers
		JPanel tl = createSpacerPanel();
		JPanel br = createSpacerPanel();
		JPanel tr = createSpacerPanel();
		
		
		//set player panels
		assignPanels();
		
		deckPanel = createDeckPanel();
		displayPanel = createStatePanel();
		updateDisplay();
		
		gameBoard.add(tl);
		gameBoard.add(top);
		gameBoard.add(tr);
		gameBoard.add(left);
		gameBoard.add(deckPanel);
		gameBoard.add(right);
		gameBoard.add(displayPanel);
		gameBoard.add(bottom);
		gameBoard.add(br);
		

		this.add(gameBoard);
		
	}


	private void initialize() {
		
		position = new HashMap<>();
		
		switch (game.playerCount()) {
		
			
			case 2: 
				position.put(0, 7);
				position.put(1, 1);
				break;
			case 3:
				position.put(0, 7);
				position.put(1, 3);
				position.put(2, 5);
				break;
			case 4:
				position.put(0, 7);
				position.put(1, 3);
				position.put(2, 1);
				position.put(3, 5);
				break;		
			default: System.out.println("Error bro");
				break;
			
		}
	}
	
	private void assignPanels() {
		switch (game.playerCount()) {
		
			case 2: 
				bottom = createPlayerPanel(game.getPlayer(0));
				top = createPlayerPanel(game.getPlayer(1));
				left = createPlayerPanel();
				right = createPlayerPanel();
				break;
				
			case 3:
				bottom = createPlayerPanel(game.getPlayer(0));
				left = createPlayerPanel(game.getPlayer(1));
				right = createPlayerPanel(game.getPlayer(2));
				top = createPlayerPanel();
				break;
				
			case 4:
				bottom = createPlayerPanel(game.getPlayer(0));
				left = createPlayerPanel(game.getPlayer(1));
				top = createPlayerPanel(game.getPlayer(2));
				right = createPlayerPanel(game.getPlayer(3));
				break;	
				
			default: System.out.println("Error bro");
				break;
			
		}
	}



	public void mousePressed(MouseEvent e) {
		
		
		if (game.getGameState() == 1) {
			firstChoice(e);
		} 
		
		if (game.getGameState() == 2) {
			secondChoice(e);	
		}
		
		if (game.getGameState() == 3) {
			replace(e);
		}
		
		
			
		
		updateDisplay();
		validate();
		repaint();

	}
	
	public void firstChoice(MouseEvent e) {
		if (e.getComponent() instanceof CardDeck) {
			//update game engine information
			game.nextCard();
			game.setGameState(2);
			
			//update GUI accordingly
			flipDeck();
		
		}
		
		
		if (e.getComponent() instanceof Card) {
			
			
			Card c = (Card) e.getComponent();	
			JPanel panel = (JPanel) c.getParent();
			
			//if pile
			if (panel.equals(deckPanel)) {
				
				game.setGameState(3);
				
				
			//if direct card flip
			} else if(panel.equals(gameBoard.getComponent(position.get(game.playerID(game.getActivePlayer())))) && c.getState() != 1) {
				
				c.flip();
				game.endTurn(c);				
				
			}
		}

	}

	public void secondChoice(MouseEvent e) {
		
		if (e.getComponent() instanceof Card) {
			
			Card c = (Card) e.getComponent();	
			JPanel panel = (JPanel) c.getParent();
			
		
			//if pile
			if (panel.equals(deckPanel)) {
				
				game.setGameState(3);
				
				
			//if direct card flip
			} else if(panel.equals(gameBoard.getComponent(position.get(game.playerID(game.getActivePlayer())))) && c.getState() != 1) {
			
				c.flip();
				game.endTurn(c);				
				
			}
			
			
		}
		
	}
	
	public void replace(MouseEvent e) {
		if (e.getComponent() instanceof Card) {
			
			Card c = (Card) e.getComponent();	
			JPanel panel = (JPanel) c.getParent();
			

			if(panel.equals(gameBoard.getComponent(position.get(game.playerID(game.getActivePlayer())))) && c.getState() != 1) {
			
				Card pile = (Card) deckPanel.getComponent(1);
				int index = cardIndex(panel, c);
				
				game.cardReplace(game.getActivePlayer(), c);
				
				deckPanel.remove(1);
				deckPanel.add(c, 1);
				
				panel.add(pile, index);
				panel.remove(c);
				
				
				c.flip();
				game.endTurn(pile);				
				
			}
			
			
		}
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
	}

	public void mouseClicked(MouseEvent e) {

	}
	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	public JPanel createPlayerPanel() {
		JPanel panel = new JPanel();
		//panel.setBackground(Color.green);
		panel.setOpaque(false);
		
		return panel;
		
	}

	public JPanel createPlayerPanel(Player p) {
		
		if (p == null) {
			return createPlayerPanel();
		}
		
		
		JPanel panel = new JPanel();
		
		//panel.setBackground(Color.green);
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(2,2));
		
		
		for (Card c: p.hand.getHand()) {
			c.addMouseListener(this);
			c.setAlignmentX(CENTER_ALIGNMENT);
			c.setAlignmentY(CENTER_ALIGNMENT);
			panel.add(c);
		}		
		
		return panel;
		
	}
	
	public JPanel createDeckPanel() {
		JPanel panel = new JPanel();
		
		
		panel.setLayout(new GridLayout(1,2));
		panel.setOpaque(false);
		
		game.getCards().addMouseListener(this);
		panel.add(game.getCards());
		
		game.getTop().addMouseListener(this);
		game.getTop().flip();
		panel.add(game.getTop());
		
		
		
		return panel;
	}

	public JPanel createSpacerPanel() {
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);		
		return panel;
	}
		
	public JPanel createSpacerPanel(int w, int h) {

		JPanel panel = new JPanel();
		panel.setSize(new Dimension(w,h));
		panel.setOpaque(false);
		return panel;
	}
		
	public JPanel createStatePanel() {
		JPanel panel = new JPanel();
		
		panel.setOpaque(false);
		
		JLabel state = new JLabel();
		JLabel round = new JLabel();
		JLabel player = new JLabel();
		
		panel.add(state);
		panel.add(round);
		panel.add(player);
		
		return panel;
	}
	
	public void updateDisplay() {
		JLabel state = (JLabel) displayPanel.getComponent(0);
		state.setText("Game State: " + game.getGameState());
		
		JLabel round = (JLabel) displayPanel.getComponent(1);
		round.setText("Swing: " + game.getSwing());
		
		JLabel player = (JLabel) displayPanel.getComponent(2);
		player.setText("Turn: " + game.getActivePlayer().getName());
	}
	
	public void flipDeck() {
		deckPanel.remove(1);
		game.getTop().addMouseListener(this);
		game.getTop().flip();
		deckPanel.add(game.getTop());
	}

	private int cardIndex(JPanel playerPanel, Card c) {
		
		return Arrays.asList(playerPanel.getComponents()).indexOf(c);
		
	}
	
	

	
	
}
