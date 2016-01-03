package pong.gui;


public class MyThread extends Thread{
	
	Window window;

	String host;

	boolean running = true;


	public MyThread(Window window){
		this.window = window;
	}
	
	public void run(){

		WaitingScreen waitingScreen = new WaitingScreen();
		window.addKeyListener(waitingScreen);
		window.add(waitingScreen);
		window.pack();

		//while no connection found
		while(running){
			waitingScreen.updateScreen();
			try{
				Thread.sleep(waitingScreen.timestep);
			}catch (InterruptedException e) {};
		}
		window.removeKeyListener(waitingScreen);
	}
	public void stopThread(){
		running = false;
	}
}
