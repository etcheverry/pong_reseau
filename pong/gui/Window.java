package pong.gui;

import javax.swing.JFrame;

/**
 * A Window is a Java frame containing an Pong
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Pong component to be displayed
	 */
	private final Pong pong;

	private final WaitingScreen connectionScreen;

	/**
	 * Constructor
	 */
	public Window(Pong pong) {
		this.connectionScreen = new WaitingScreen();
		this.addKeyListener(connectionScreen);
		this.pong = pong;
	}

	/**
	 * Displays the Window using the defined margins, and call the
	 * {@link Pong#animate()} method of the {@link Pong} every 100ms
	 */
	public void displayOnscreen() {
		add(connectionScreen);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		boolean connectionWaiting = true;
		while(connectionWaiting){
			connectionScreen.updateScreen();
			try{
				Thread.sleep(connectionScreen.timestep);
			}catch (InterruptedException e) {};
			connectionWaiting = connectionScreen.IsStillWaiting();
		}

		this.removeKeyListener(connectionScreen);
		this.addKeyListener(pong);
		this.add(pong);
		pack();
		while(true) {
			pong.animate();
			try {
				Thread.sleep(pong.timestep);
			} catch (InterruptedException e) {};
		}
	}
}
