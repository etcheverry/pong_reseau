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

	private final WaitingScreen waitingScreen;

	private boolean waiting = true;

	/**
	 * Constructor
	 */
	public Window(Pong pong) {
		this.waitingScreen = new WaitingScreen();
		this.addKeyListener(waitingScreen);
		this.pong = pong;
	}

	/**
	 * Displays the Window using the defined margins, and call the
	 * {@link Pong#animate()} method of the {@link Pong} every 100ms
	 */
	public void displayOnscreen() {
		add(waitingScreen);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		//while no connection found
		while(waiting){
			waitingScreen.updateScreen();
			try{
				Thread.sleep(waitingScreen.timestep);
			}catch (InterruptedException e) {};
		}

		//prepare the window for the new panel
		this.removeKeyListener(waitingScreen);
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

	/*###########################################
	  # A COMPLETER : 							#
	  # attribut les sockets des 2 joueurs      #
	  # met fin à la boucle de l'écran d'attente#
	  ########################################### 
	*/
	public void connection(String host){
		if(host == null){
			try{
				ServerSocket ecoute = new ServerSocket(7777);
				Socket client = ecoute.accept();
				client.close();
				ecoute.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			try{
				Socket client = new Socket(host, 7777);
				client.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}

		/*try{
			Thread.sleep(2000);
		}catch (InterruptedException e) {};*/
		waiting = false;
	}
}
