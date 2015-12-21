package pong.gui;

import pong.gui.Window;

public class MyThread extends Thread{
	
	Window window;

	String host;

	public MyThread(Window window, String host){
		this.window = window;
		this.host = host;
	}
	
	public void run(){
		window.connection(host);
	}
}
