package pong;

import pong.gui.Window;
import pong.gui.Pong;
import pong.gui.MyThread;
import pong.gui.Network;
import java.awt.Point;
/**
 * Starting point of the Pong application
 */
public class Main  {
	public static void main(String[] args) {
		Network network = new Network();
		Pong pong = new Pong(network);
		Window window = new Window(pong);

		/*thread is used to have the animation of the waiting screen
		while we are waiting for a connection in the main thread*/
		MyThread t;
		t = new MyThread(window);
		t.start();
		
		//waiting for or looking for players
		if (args.length == 0)
			network.waitForNPlayers(1);
		else
			network.connect(args[0], 7777);
			
		t.stopThread();
		try{
			t.join();
		}catch(InterruptedException e){}
		window.displayOnscreen();
	}
}
