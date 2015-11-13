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

	private Racket racket;
	private Racket racket2;

	public Pong() {
		this.ball = new Ball(AREA_SIZE);
		this.racket = new Racket(AREA_SIZE, 1);
		this.racket2 = new Racket(AREA_SIZE, 2);
		this.setPreferredSize(AREA_SIZE);
		this.addKeyListener(this);
	}

	/**
         * Proceeds to the movement of the ball and updates the screen
	 */
	public void animate() {
		ball.animate(racket);
		racket.animate();
		racket2.animate();
		/* And update output */
		updateScreen();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				racket.up();
				racket2.up();
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				racket.down();
				racket2.down();
				break;
			default:
				System.out.println("got press "+e);
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				racket.nup();
				racket2.nup();
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				racket.ndown();
				racket2.ndown();
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
		racket.draw(graphicContext);
		racket2.draw(graphicContext);
		this.repaint();
	}
}
