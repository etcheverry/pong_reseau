package pong.gui;
import java.awt.Point;
import java.awt.Dimension;


public class Racket extends PongItem{
	private int saved_speed;

	private boolean up = false;
	private boolean down = false;

	public Racket(Dimension area, int player_number){
		super("image/racket.png", 0, area.width/2, 0, 0, area);
		int x;
		if(player_number == 1)
			setX(0);
		else if(player_number == 2)
			setX(area.width - this.getWidth());
		saved_speed = 4;
	}

	public void animate(){
		setNextPos(this.getSpeed().x + this.getPosition().x, this.getSpeed().y + this.getPosition().y );

		if(up)
			this.setSpeedY(-saved_speed);
		else if(down)
			this.setSpeedY(saved_speed);
		else
			this.setSpeed(0, 0);
		
		

		/* Update racket position */
		if (this.getNextPos().y < 0){
			this.setNextPos(getNextPos().x, 0);
		}
		if (this.getNextPos().y > this.getArea().height - this.getHeight()){
			this.setNextPos(getNextPos().x, this.getArea().height - this.getHeight());
		}

		super.animate();
	}

	public void down(){
		down = true;
		if(up)
			up = false;
	}

	public void up(){
		up = true;
		if(down)
			down = false;
	}

	public void nup(){
		up = false;
	}

	public void ndown(){
		down = false;
	}

	public void setSavedSpeed(int new_speed){
		this.saved_speed = new_speed;
	}
}