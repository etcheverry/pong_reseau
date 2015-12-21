package pong;

import pong.gui.Window;
import pong.gui.Pong;
import pong.gui.MyThread;

/**
 * Starting point of the Pong application
 */
public class Main  {
	public static void main(String[] args) {
		Pong pong = new Pong();
		Window window = new Window(pong);
		for (String s: args) {
            System.out.println(s);
        }
		/*the thread execute the function which connect two players
		while the main thread display the waiting screen*/
		MyThread t;
		if (args.length == 0)
			t = new MyThread(window, null);
		else
			t = new MyThread(window, args[0]);
		t.start();
		window.displayOnscreen();
	}
}
