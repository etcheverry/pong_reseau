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

	private Player pl;

	/**
	 * Constructor
	 */
	public Window(Pong pong) {
		this.waitingScreen = new WaitingScreen();
		this.addKeyListener(waitingScreen);
		this.pong = pong;
		pl = new Player();
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
		pong.setPlayer(pl);
		while(true) {
			pong.animate();
			try {
				Thread.sleep(pong.timestep);
			} catch (InterruptedException e) {};
		}
	}

	public void connection(String host){
		if(host == null){
			try{
				ServerSocket ecoute = new ServerSocket(7777);
				pl.setSocket(ecoute.accept());
				System.out.println(pl.read());
				pl.setNumber(1);
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			try{
				pl.setSocket(new Socket(host, 7777));
				pl.write("salut");
				pl.setNumber(2);
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		
		waiting = false;
	}
}
