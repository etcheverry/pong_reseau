package pong.gui;

import java.io.*;
import java.net.*;

public class Player{

	PrintWriter writer;
	BufferedReader reader;
	Socket soc;
	int id; //used to recognize the racket of the other player in the PongItem array

	public Player(){
	}

	public void setID(int id){
		this.id = id;
	}

	public int getID(){
		return id;
	}

	//finding an host player
	public void connection(String host, int port){
		try{
			this.setSocket(new Socket(host, port));
		}
		catch(IOException e){
			closeConnection();
			System.exit(0);
		}
	}

	//save the socket in the class and open communication stream between 2 players
	public void setSocket(Socket s){
		this.soc = s;
		try{
			this.reader = new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(this.soc.getOutputStream()));
		}
		catch(IOException e){
			closeConnection();
			System.exit(0);
		}
	}

	public void write(String s){
		this.writer.println(s);
		this.writer.flush();
	}

	//read a line from the other player
	public String read(){
		String s = new String("");
		try{
			s = this.reader.readLine();
		}
		catch(IOException e){
			closeConnection();
			System.exit(0);
		}
		return s;
	}

	public void closeConnection(){
		try{
			this.soc.close();
			this.reader.close();
			this.writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
}