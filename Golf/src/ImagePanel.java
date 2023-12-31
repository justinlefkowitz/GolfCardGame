import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	private Image image;
	
	
	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	
	public ImagePanel(Image img) {
		image = img;
		
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setSize(size);
		this.setLayout(null);
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	

}
