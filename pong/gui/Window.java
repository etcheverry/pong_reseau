package pong.gui;

import javax.swing.JFrame;
import java.net.*;
import java.io.*;

/**
 * A Window is a Java frame containing an Pong
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Pong component to be displayed
	 */
	private final Pong pong;

	

	/**
	 * Constructor
	 */
	public Window(Pong pong) {
		this.pong = pong;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Displays the Window using the defined margins, and call the
	 * {@link Pong#animate()} method of the {@link Pong} every 100ms
	 */

	public void displayOnscreen() {
		//prepare the window for the new panel
		this.setContentPane(pong);
		this.addKeyListener(pong);
		pack();

		pong.initItems();
		while(true) {
			pong.animate();
			try {
				Thread.sleep(pong.timestep);
			} catch (InterruptedException e) {};
		}
	}
}
