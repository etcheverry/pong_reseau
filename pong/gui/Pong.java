package pong.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * An Pong is a Java graphical container that extends the JPanel class in
 * order to display graphical elements.
 */
public class Pong extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Constant (c.f. final) common to all Pong instances (c.f. static)
	 * defining the background color of the Pong
	 */

	private static Color backgroundColor = new Color(0, 255, 255);


	/**
	 * Pong area
	 */
	private static final Dimension AREA_SIZE = new Dimension(800,600);
	/**
	 * Time step of the simulation (in ms)
	 */
	public static final int timestep = 10;
	/**
	 * Speed of racket (in pixels per second)
	 */
	public static final int RACKET_SPEED = 4;

	/**
	 * Pixel data buffer for the Pong rendering
	 */
	private Image buffer = null;
	/**
	 * Graphic component context derived from buffer Image
	 */
	private Graphics graphicContext = null;

	/**
	 * Ball to be displayed
	 */
	private Ball ball;



	/**
	 * One Racket to be displayed
	 */
	private final Image racket;
	/**
	 * Width of the racket in pixels
	 */
	private int racket_width;
	/**
	 * Height of the racket in pixels
	 */
	private int racket_height;
	/**
	 * Speed of racket, in pixels per timestamp
	 */
	private int racket_speed;
	/**
	 * Position of racket
	 */
	private Point racket_position = new Point(0, 0);

	public Pong() {
		this.ball = new Ball(AREA_SIZE);
		ImageIcon icon;
		this.racket = Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource("image/racket.png"));
		icon = new ImageIcon(racket);
		this.racket_width = icon.getIconWidth();
		this.racket_height = icon.getIconHeight();

		this.setPreferredSize(AREA_SIZE);
		this.addKeyListener(this);
	}

	/**
         * Proceeds to the movement of the ball and updates the screen
	 */
	public void animate() {
		ball.animate();
		/* Update racket position */
		racket_position.y += racket_speed;
		if (racket_position.y < 0)
			racket_position.y = 0;
		if (racket_position.y > AREA_SIZE.width - racket_height/2)
			racket_position.y = AREA_SIZE.height - racket_height/2;

		/* And update output */
		updateScreen();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				racket_speed = -RACKET_SPEED;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				racket_speed = RACKET_SPEED;
				break;
			default:
				System.out.println("got press "+e);
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				racket_speed = 0;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				racket_speed = 0;
				break;
			default:
				System.out.println("got release "+e);
		}
	}
	public void keyTyped(KeyEvent e) { }

	/*
	 * (non-Javadoc) This method is called by the AWT Engine to paint what
	 * appears in the screen. The AWT engine calls the paint method every time
	 * the operative system reports that the canvas has to be painted. When the
	 * window is created for the first time paint is called. The paint method is
	 * also called if we minimize and after we maximize the window and if we
	 * change the size of the window with the mouse.
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Draw each Pong item based on new positions
	 */
	public void updateScreen() {
		if (buffer == null) {
			/* First time we get called with all windows initialized */
			buffer = createImage(AREA_SIZE.width, AREA_SIZE.height);
			if (buffer == null)
				throw new RuntimeException("Could not instanciate graphics");
			else
				graphicContext = buffer.getGraphics();
		}
		
		/* Fill the area with blue */
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, AREA_SIZE.width, AREA_SIZE.height);
		
		/* Draw items */
		ball.draw(graphicContext);
		graphicContext.drawImage(racket, racket_position.x, racket_position.y, racket_width, racket_height, null);

		this.repaint();
	}
}
