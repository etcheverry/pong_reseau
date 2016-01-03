package pong.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class PongItem {

	private Image image;

	private int width;

	private int height;

	private Point speed;

	private Point position;

	private Point nextpos;

	private Dimension area;

	public PongItem(){
		
	}

	public PongItem(String imagePath, int position_x, int position_y, int speed_x, int speed_y, Dimension area){
		ImageIcon icon;
		this.image = Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource(imagePath));
		icon = new ImageIcon(this.image);
		this.width = icon.getIconWidth();
		this.height = icon.getIconHeight();
		this.position = new Point (position_x, position_y);
		this.speed = new Point (speed_x, speed_y);
		this.area = (Dimension)area.clone();
		this.nextpos = new Point(position_x + speed_x, position_y + speed_y);
	}

	public PongItem(String imagePath, Dimension area){
		this(imagePath, 0, 0, 0, 0, area);
	}

	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}

	public Point getSpeed(){
		return (Point)this.speed.clone();
	}

	public Point getPosition(){
		return (Point)this.position.clone();
	}

	public Dimension getArea(){
		return (Dimension) this.area.clone();
	}

	public void setSpeed(Point speed){
		this.speed = (Point) speed.clone();
	}

	public void setSpeed(int x, int y){
		this.speed.x = x;
		this.speed.y = y;
	}

	public void setSpeedX(int x){
		this.speed.x = x;
	}

	public void setSpeedY(int y){
		this.speed.y = y;
	}

	public void setPosition(int x, int y){
		this.position.x = x;
		this.position.y = y;
	}

	public void setX(int x){
		this.position.x = x;
	}

	public void setY(int y){
		this.position.y = y;
	}

	// update the current position with the next (must be only after collision tests)
	public void animate(){
		this.setPosition(nextpos.x, nextpos.y);
	}

	public Point getNextPos(){
		return nextpos;
	}

	public void setNextPos(int x, int y){
		this.nextpos = new Point(x,y);
	}

	//set the next position according to the current speed
	public void updateNextPos(){
		setNextPos(this.getSpeed().x + this.getPosition().x, this.getSpeed().y + this.getPosition().y);
	}

	public void draw(Graphics graphicContext){
		graphicContext.drawImage(this.image, this.position.x, this.position.y, this.width, this.height, null);
	}
}