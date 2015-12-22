package pong.gui;

import java.io.*;
import java.net.*;

public class Player{

	PrintWriter writer;
	BufferedReader reader;
	Socket soc;

	public Player(){
		
	}

	public void setSocket(Socket s){
		this.soc = s;
		try{
			this.reader = new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(this.soc.getOutputStream()));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void write(String s){
		this.writer.println(s);
		this.writer.flush();
	}

	public String read(){
		String s = new String("");
		try{
			s = this.reader.readLine();
		}
		catch(IOException e){
			e.printStackTrace();
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
		}
	}
}