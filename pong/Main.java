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

		MyThread t;
		t = new MyThread(window);
		t.start();
		
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
