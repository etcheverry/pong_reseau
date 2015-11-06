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
	private static final Color backgroundColor = new Color(0xFF, 0x40, 0);

	/**
	 * Width of pong area
	 */
	private static final int SIZE_PONG_X = 800;
	/**
	 * Height of pong area
	 */
	private static final int SIZE_PONG_Y = 600;
	/**
	 * Time step of the simulation (in ms)
	 */
	public static final int timestep = 10;
	/**
	 * Speed of ball (in pixels per second)
	 */
	public static final int BALL_SPEED = 2;
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
	private final Image ball;
	/**
	 * Width of ball in pixels
	 */
	private int ball_width;
	/**
	 * Height of ball in pixels
	 */
	private int ball_height;
	/**
	 * Position of ball
	 */
	private Point ball_position = new Point(0, 0);
	/**
	 * Speed of ball, in pixels per timestep
	 */
	private Point ball_speed = new Point(BALL_SPEED, BALL_SPEED);

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
		ImageIcon icon;

		this.ball = Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource("image/ball.png"));
		icon = new ImageIcon(ball);
		this.ball_width = icon.getIconWidth();
		this.ball_height = icon.getIconHeight();

		this.racket = Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource("image/racket.png"));
		icon = new ImageIcon(racket);
		this.racket_width = icon.getIconWidth();
		this.racket_height = icon.getIconHeight();

		this.setPreferredSize(new Dimension(SIZE_PONG_X, SIZE_PONG_Y));
		this.addKeyListener(this);
	}

	/**
         * Proceeds to the movement of the ball and updates the screen
	 */
	public void animate() {
		/* Update ball position */
		ball_position.translate(ball_speed.x, ball_speed.y);
		if (ball_position.x < 0)
		{
			ball_position.x = 0;
			ball_speed.x = -ball_speed.x;
		}
		if (ball_position.y < 0)
		{
			ball_position.y = 0;
			ball_speed.y = -ball_speed.y;
		}
		if (ball_position.x > SIZE_PONG_X - ball_width)
		{
			ball_position.x = SIZE_PONG_X - ball_width;
			ball_speed.x = -ball_speed.x;
		}
		if (ball_position.y > SIZE_PONG_Y - ball_height)
		{
			ball_position.y = SIZE_PONG_Y - ball_height;
			ball_speed.y = -ball_speed.y;
		}

		/* Update racket position */
		racket_position.y += racket_speed;
		if (racket_position.y < 0)
			racket_position.y = 0;
		if (racket_position.y > SIZE_PONG_Y - racket_height/2)
			racket_position.y = SIZE_PONG_Y - racket_height/2;

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
			buffer = createImage(SIZE_PONG_X, SIZE_PONG_Y);
			if (buffer == null)
				throw new RuntimeException("Could not instanciate graphics");
			else
				graphicContext = buffer.getGraphics();
		}
		/* Fill the area with blue */
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, SIZE_PONG_X, SIZE_PONG_Y);

		/* Draw items */
		graphicContext.drawImage(ball, ball_position.x, ball_position.y, ball_width, ball_height, null);
		graphicContext.drawImage(racket, racket_position.x, racket_position.y, racket_width, racket_height, null);

		this.repaint();
	}
}
