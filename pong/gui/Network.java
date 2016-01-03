package pong.gui;
import java.net.*;
import java.io.*;
import java.awt.Point;

public class Network{
	private Player [] players;
	private int id;

	public Network(){

	}

	public void waitForNPlayers(int n){
		try{
			id = 0;
			ServerSocket ecoute = new ServerSocket(7777);
			players = new Player[n];
			for (int i = 0; i < n ; i++)
			{
				players[i] = new Player();
				players[i].setSocket(ecoute.accept());
				players[i].setID(i+1);
				players[i].write(Integer.toString(n));
				players[i].write(Integer.toString(i+1));
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void connect(String host, int port){
		try{
			Player playerHost = new Player();
			playerHost.setID(0);
			playerHost.setSocket(new Socket (host, port));
			int n = Integer.parseInt(playerHost.read());
			id = Integer.parseInt(playerHost.read());
			players = new Player[n];
			players[0] = playerHost;
			for (int i = 1 ; i < n ; i++)
			{
				//TODO Multiplayer >2 players
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Player[] getPlayers(){
		return players;
	}

	public int getID(){
		return id;
	}

	public void sendPosition(Point position, int id){
		for(int i = 0 ; i < this.getPlayers().length ; i++){
			String msg = "pos " + Integer.toString(id) + " " + Integer.toString(position.x) + " " + Integer.toString(position.y);
			this.getPlayers()[i].write(msg);
		}
	}

	public void sendSpeed(Point speed, int id){
		for(int i = 0 ; i < this.getPlayers().length ; i++){
			String msg = "speed " + Integer.toString(id) + " " + Integer.toString(speed.x) + " " + Integer.toString(speed.y);
			this.getPlayers()[i].write(msg);
		}
	}

	public void sendScore(int [] score){
		for(int i = 0 ; i < this.getPlayers().length ; i++){
			String msg = "score " + Integer.toString(score[0]) + " " + Integer.toString(score[1]);
			this.getPlayers()[i].write(msg);
		}
	}

	public String[] receive(int player){
		String msg = this.getPlayers()[player].read();
		return msg.split(" ");
	}
}