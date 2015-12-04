package pong.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import javax.swing.JPanel;

public class WaitingScreen extends JPanel implements KeyListener {

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
	public static final int timestep = 1000;

	/**
	 * Pixel data buffer for the Pong rendering
	 */
	private Image buffer = null;
	/**
	 * Graphic component context derived from buffer Image
	 */
	private Graphics graphicContext = null;

	/*true while no connection found*/
	private boolean stillWaiting = true;

	private Image ballImage;
	private Image backGround;

	private ImageIcon ballIcon;
	private ImageIcon backGroundIcon;

	private int ballNb = 1;

	public WaitingScreen() {
		this.setPreferredSize(AREA_SIZE);
		this.addKeyListener(this);

		this.ballImage = Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource("image/sharlball.png"));
		this.ballIcon = new ImageIcon(this.ballImage);

		this.backGround = Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource("image/waitingScreen.png"));
		this.backGroundIcon = new ImageIcon(this.backGround);
	}

	public boolean IsStillWaiting() {
		return stillWaiting;
	}

	/*##############################
	  #####     TEMPORAIRE     #####
	  ##############################*/
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
			case KeyEvent.VK_ENTER :
				stillWaiting = false;
				break;
			default:
				System.out.println("got press "+e);
				break;
		}
	}

	public void keyReleased(KeyEvent e) { }

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

		graphicContext.drawImage(this.backGround, 0, 0, this.backGroundIcon.getIconWidth(), this.backGroundIcon.getIconHeight(), null);

		for(int i=0; i < this.ballNb; i++)
			graphicContext.drawImage(this.ballImage, (i*50) + 275, 270, this.ballIcon.getIconWidth(), this.ballIcon.getIconHeight(), null);
		if(ballNb == 5)
			ballNb = 0;
		else
			ballNb ++;

		this.repaint();
	}

}